package io.openshift.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.openshift.client.ClientException;
import io.openshift.client.ClientFactory;
import io.openshift.client.impl.Client;
import io.openshift.model.config.ClusterServiceProperties;
import io.openshift.model.domain.Cluster;
import io.openshift.model.domain.DeploymentConfig;
import io.openshift.model.domain.Namespace;
import io.openshift.model.dto.deploymentconfig.DeploymentConfigListDTO;
import io.openshift.model.dto.project.ProjectDTO;
import io.openshift.service.ClusterService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClusterServiceImpl implements ClusterService {
	@Autowired
	private ClusterServiceProperties serviceProperties;

    @Autowired
    private ClientFactory clientFactory;

	private List<Cluster> clusters;
	
	@PostConstruct
	public void initIt() {
		clusters = serviceProperties.getClusters().stream()
				.map(cluster -> modelMapper.map(cluster, Cluster.class))
				.collect(Collectors.toList());
	}
    
    private final Type namespaceListType = new TypeToken<ArrayList<Namespace>>() {
    }.getType();
    private final Type deploymentConfigListType = new TypeToken<ArrayList<DeploymentConfig>>() {
    }.getType();

    @Autowired
    private ModelMapper modelMapper;
    
/*    public static String imageRefToVersion(final String imageRef, final String namespace, final String containerName, final Map<String, String> images) {
        String imageVersion;
        if (null != imageRef && imageRef.startsWith(GLOBAL_REGISTRY)) {
            // TODO: use regexp with named groups
            String[] pathParts = imageRef.split("/", 3);
            String[] nameParts = pathParts[2].split(":", 2);
            if (nameParts[0].contentEquals(containerName)) {
                imageVersion = "(G)" + nameParts[1] + (pathParts[1].contentEquals(namespace) ? "" : "!" + pathParts[1]);
            } else {
                imageVersion = "(G)" + pathParts[1] + '/' + pathParts[2];
            }
        } else {
            imageVersion = images.get(imageRef);
            if (null != imageVersion) {
                imageVersion = "(L)" + imageVersion;
            } else imageVersion = imageRef;
        }
        return imageVersion;
    } */


    @Override
    public List<Cluster> getAllClusters() {
        return clusters;
    }

    @Override
    public Optional<Cluster> findClusterByName(final String clusterName) {
    	assertThat(clusterName).isNotEmpty();
        return clusters.stream().filter(c -> c.getName().contentEquals(clusterName)).findFirst();
    }

    @Override
    public List<Namespace> getNamespacesForCluster(final Cluster cluster) throws ClientException {
        final Client client = clientFactory.getClientForCluster(cluster);

        final long start = System.currentTimeMillis();
        List<ProjectDTO> projects = client.getProjects().getItems();
        final long end = System.currentTimeMillis();
        log.info("getProjects for {} took {} ms", cluster.getName(), (end - start));
        
        return modelMapper.map(projects, namespaceListType);
    }


    @Override
    public List<DeploymentConfig> getDeploymentConfigsForClusterAndNamespace(final Cluster cluster, final String namespaceName) throws ClientException {
        final Client client = clientFactory.getClientForCluster(cluster);
        
        final long start = System.currentTimeMillis();
        DeploymentConfigListDTO dcs = client.getDeploymentConfigsForNameSpace(namespaceName);
        final long end = System.currentTimeMillis();
        log.info("getDeploymentConfigsForNameSpace for {} took {} ms", namespaceName, (end - start));
        
        return modelMapper.map(dcs.getItems(), deploymentConfigListType);
    }

}
