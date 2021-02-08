package io.openshift.client.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
// import org.assertj.core.internal.cglib.beans.BeanMap;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import io.openshift.client.ClientAccessException;
import io.openshift.client.ClientException;
import io.openshift.model.domain.AuthToken;
import io.openshift.model.dto.ApiObjectDTO;
import io.openshift.model.dto.AuthTokenDTO;
import io.openshift.model.dto.api.ApiGroupDTO;
import io.openshift.model.dto.api.ApiListDTO;
import io.openshift.model.dto.api.ApiVersionDTO;
import io.openshift.model.dto.api.resource.QuantityValueDTO;
import io.openshift.model.dto.api.util.IntOrStringDTO;
import io.openshift.model.dto.deploymentconfig.DeploymentConfigListDTO;
import io.openshift.model.dto.imagestream.ImageStreamListDTO;
import io.openshift.model.dto.imagestream.ImageStreamTagDTO;
import io.openshift.model.dto.imagestream.ImageStreamTagListDTO;
import io.openshift.model.dto.project.ProjectListDTO;
import io.openshift.model.dto.replicationcontroller.ReplicationControllerListDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor()
@Builder
@Slf4j
public class Client {
	@Value("#{environment.OPENSHIFT_USERNAME}")
    private final String OPENSHIFT_USERNAME;
	
	@Value("#{environment.OPENSHIFT_PASSWORD}")
    private final String OPENSHIFT_PASSWORD;

    private final ModelMapper modelMapper = new ModelMapper();

    private final String baseUrl;
    private final ApiContext context = new ApiContext();
    private final Gson gson = createGson();

    @Data
    public static class ApiContext {
        private Map<String, String> versions;
        private AuthToken authToken;
    }

    interface ApiWithContext {
        String toPath();
    }

    interface ContextEnabled {
        ApiWithContext withContext(ApiContext context);
    }

    interface ContextEnabledWithId extends ContextEnabled {
        ContextEnabled withId(String id);
    }

    public enum Apis implements ContextEnabled {
        PROJECTS("project.openshift.io", "projects"),
        IMAGE_STREAMS("image.openshift.io", "imagestreams"),
        IMAGE_STREAM_TAGS("image.openshift.io", "imagestreamtags"),
        REPLICATION_CONTROLLERS(null, "replicationcontrollers"),
        DEPLOYMENT_CONFIGS("apps.openshift.io", "deploymentconfigs");


        private final String group;
        private final String path;

        Apis(final String group, final String path) {
            this.group = group;
            this.path = path;
        }

        private String createPath(ApiContext context, String nameSpace, String id) {
            String prefix;

            if (null != group) {
                assertThat(context).isNotNull();
                assertThat(context.getVersions()).isNotNull();
                final String groupVersion = context.getVersions().get(group);
                assertThat(groupVersion).isNotBlank();
                prefix = "/apis/" + group + "/" + groupVersion;
            } else {
                prefix = "/api/v1";
            }

            /**
             * hint: when using HttpClient directly we need to URLencode parameters -> id.replaceAll(":", "%3A"));
             * while RestTemplate does URL encoding on its own
             */
            return prefix + (null != nameSpace ? "/namespaces/" + nameSpace : "") + "/" + path + (null == id ? "" : '/' + id);
        }

        @Override
        public ApiWithContext withContext(ApiContext context) {
        	return () -> createPath(context, null, null);
        }

        public ContextEnabledWithId withNameSpace(final String nameSpace) {
            return new ContextEnabledWithId() {
                @Override
                public ApiWithContext withContext(final ApiContext context) {
                	return () -> createPath(context, nameSpace, null);
                }

                @Override
                public ContextEnabled withId(final String id) {
                	return context -> { return () -> createPath(context, nameSpace, id); };
                }
            };
        }

    }

    // TODO: locking
    // TODO: obtain token and versions in parallel as versions do not need authentication
    private void updateContext() throws ClientException {
        // TODO: caching
        if (null == context.getAuthToken()) {
            context.setVersions(getVersions());
        }
        // obtain a new token, if we have none or if existing is about to expire in next minute
        if (null == context.getAuthToken() || context.getAuthToken().getValidTo().getTime() < System.currentTimeMillis() - 60*1000L) {
            context.setAuthToken(getAuthToken());
        }
    }

    // TODO: use mapper
    private Map<String, String> getVersions() throws ClientException {
        ApiListDTO apis = apiRequest("/apis", ApiListDTO.class);
        Map<String, String> versions = Maps.newHashMapWithExpectedSize(apis.getGroups().size());
        // assumption #1 each group/versions/[groupVersion == group.name + '/' + version]
        for (ApiGroupDTO group : apis.getGroups()) {
            for (ApiVersionDTO version : group.getVersions()) {
                assertThat(group.getName() + '/' + version.getVersion()).isEqualTo(version.getGroupVersion());
            }
            assertThat(group.getName() + '/' + group.getPreferredVersion().getVersion()).isEqualTo(group.getPreferredVersion().getGroupVersion());
            versions.put(group.getName(), group.getPreferredVersion().getVersion());
        }
        return versions;
    }

    private AuthToken getAuthToken() throws ClientException {
        try (CloseableHttpClient httpClient = HttpClients.createMinimal()) {
        	
        	// TODO: move to properties
            final String path = "/oauth/authorize?client_id=openshift-challenging-client&response_type=token";

            HttpGet request = new HttpGet(baseUrl + path);

            String auth = OPENSHIFT_USERNAME + ":" + OPENSHIFT_PASSWORD;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + new String(encodedAuth, StandardCharsets.US_ASCII);
            request.setHeader(HttpHeaders.HOST, new URI(baseUrl).getHost());
            request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

            log.debug("request: {}", request);
            log.debug("headers: {}",
                    Arrays.stream(request.getAllHeaders()).map(header -> String.format("%s: %s", header.getName(), header.getValue())).collect(Collectors.joining("\n"))
            );

            HttpResponse response;
            final long start = System.currentTimeMillis();
            response = httpClient.execute(request);
            final long end = System.currentTimeMillis();

            int responseStatus = response.getStatusLine().getStatusCode();
            log.info("responseStatus: {} ({}), took {} ms", responseStatus, baseUrl + path, (end - start));

            assertThat(responseStatus).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);

            log.debug("headers: {}",
                    Arrays.stream(response.getAllHeaders()).map(header -> String.format("%s: %s", header.getName(), header.getValue())).collect(Collectors.joining("\n"))
            );
            Header locationHeader = response.getFirstHeader(HttpHeaders.LOCATION);
            assertThat(locationHeader).isNotNull();
            List<NameValuePair> locationParams = new URIBuilder(locationHeader.getValue().replace('#', '?')).getQueryParams();

            AuthTokenDTO tokenDTO = new AuthTokenDTO();
            BeanMap beanMap = BeanMap.create(tokenDTO);

            locationParams.forEach(x -> {
                String attrName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, x.getName());
                beanMap.put(attrName, x.getValue());
            });

            // TODO: add basic validation
            return modelMapper.map(tokenDTO, AuthToken.class);

        } catch (IOException | URISyntaxException e) {
            throw new ClientException(e);
        }
    }

    // TODO: caching - each data source should has its own lifespan, e.g. projects are not changing that often like pods state
    public ProjectListDTO getProjects() throws ClientException {
        updateContext();
        return apiRequest(Apis.PROJECTS, ProjectListDTO.class, context);
    }

    public ImageStreamListDTO getImageStreamsForNameSpace(final String nameSpace) throws ClientException {
        updateContext();
        return apiRequest(Apis.IMAGE_STREAMS.withNameSpace(nameSpace), ImageStreamListDTO.class, context);
    }

    public ImageStreamTagListDTO getImageStreamTagsForNameSpace(final String nameSpace) throws ClientException {
        updateContext();
        return apiRequest(Apis.IMAGE_STREAM_TAGS.withNameSpace(nameSpace), ImageStreamTagListDTO.class, context);
    }

    public ImageStreamTagDTO getImageStreamTagsForNameSpaceAndName(final String nameSpace, final String name) throws ClientException {
        updateContext();
        return apiRequest(Apis.IMAGE_STREAM_TAGS.withNameSpace(nameSpace).withId(name), ImageStreamTagDTO.class, context);
    }

    public ReplicationControllerListDTO getReplicationControllersForNameSpace(final String nameSpace) throws ClientException {
        updateContext();
        return apiRequest(Apis.REPLICATION_CONTROLLERS.withNameSpace(nameSpace), ReplicationControllerListDTO.class, context);
    }

    public DeploymentConfigListDTO getDeploymentConfigsForNameSpace(final String nameSpace) throws ClientException {
        updateContext();
        return apiRequest(Apis.DEPLOYMENT_CONFIGS.withNameSpace(nameSpace), DeploymentConfigListDTO.class, context);
    }

    private <T extends ApiObjectDTO> T apiRequest(final String path, Class<T> clazz) throws ClientException {
        return apiRequestWithRestTemplate(path, clazz, null);
    }

    private <T extends ApiObjectDTO> T apiRequest(ContextEnabled api, Class<T> clazz, ApiContext context) throws ClientException {
        final String path = api.withContext(context).toPath();
        return apiRequestWithRestTemplate(path, clazz, context.getAuthToken());
    }


    private Gson createGson() {
        /* JsonDeserializer<IntOrStringDTO> deserializerIntOrStringDTO = new JsonDeserializer<IntOrStringDTO>() {

            @Override
            public IntOrStringDTO deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
                String str = jsonElement.getAsString();
                return IntOrStringDTO.builder().value(str).build();
            }
        }; */
    	final JsonDeserializer<IntOrStringDTO> deserializerIntOrStringDTO = (jsonElement, type, jsonDeserializationContext) -> 
    		IntOrStringDTO.builder().value(jsonElement.getAsString()).build();

    	/*
        JsonDeserializer<QuantityValueDTO> deserializerQuantityValueDTO = new JsonDeserializer<QuantityValueDTO>() {

            @Override
            public QuantityValueDTO deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
                String str = jsonElement.getAsString();
                return QuantityValueDTO.builder().value(str).build();
            }
        }; */
    	final JsonDeserializer<QuantityValueDTO> deserializerQuantityValueDTO = (jsonElement, type, jsonDeserializationContext) ->
    		QuantityValueDTO.builder().value(jsonElement.getAsString()).build();

        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(IntOrStringDTO.class, deserializerIntOrStringDTO)
                .registerTypeAdapter(QuantityValueDTO.class, deserializerQuantityValueDTO)
                .create();
    }


    private <T extends ApiObjectDTO> T apiRequestWithRestTemplate(final String path, Class<T> clazz, AuthToken authToken) throws ClientException {
        try {
            GsonHttpMessageConverter messageConverter = new GsonHttpMessageConverter();
            messageConverter.setGson(this.gson);
            RestTemplate template = new RestTemplate(ImmutableList.of(messageConverter));

            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            if (null != authToken && null != authToken.getAccessToken() && null != authToken.getTokenType()) {
            	final String accessToken = authToken.getAccessToken().trim();
            	final String tokenType = authToken.getTokenType().trim();
            	if (accessToken.length() > 0 && tokenType.length() > 0) {
                    headers.add(HttpHeaders.AUTHORIZATION, tokenType + ' ' + accessToken);
                }
            }
            org.springframework.http.HttpEntity<String> request = new org.springframework.http.HttpEntity<>("headers", headers);
            final long start = System.currentTimeMillis();
            ResponseEntity<T> response = template.exchange(baseUrl + path, HttpMethod.GET, request, clazz);
            final long end = System.currentTimeMillis();

            org.springframework.http.HttpStatus responseStatus = response.getStatusCode();
            log.info("responseStatus: {} ({}), took {} ms", responseStatus.value(), baseUrl + path, (end - start));
            if (HttpStatus.SC_FORBIDDEN == responseStatus.value()) {
                throw new ClientAccessException(responseStatus.toString());
            }
            assertThat(responseStatus.value()).isEqualTo(HttpStatus.SC_OK);
            return response.getBody();
        } catch (RestClientException e) {
            throw new ClientException(e);
        }
    }

}
