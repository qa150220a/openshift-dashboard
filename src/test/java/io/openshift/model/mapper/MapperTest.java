package io.openshift.model.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import io.openshift.model.domain.Container;
import io.openshift.model.domain.DeploymentConfig;
import io.openshift.model.domain.Namespace;
import io.openshift.model.dto.ObjectMetadataDTO;
import io.openshift.model.dto.core.ContainerDTO;
import io.openshift.model.dto.core.PodSpecDTO;
import io.openshift.model.dto.core.PodTemplateSpecDTO;
import io.openshift.model.dto.deploymentconfig.DeploymentConfigDTO;
import io.openshift.model.dto.deploymentconfig.DeploymentConfigDTO.DeploymentConfigSpecDTO;
import io.openshift.model.dto.deploymentconfig.DeploymentConfigDTO.DeploymentConfigStatusDTO;
import io.openshift.model.dto.project.ProjectDTO;

public class MapperTest {
	private static final String PROJECT_DISPLAY_NAME_ANNOTATION = "openshift.io/display-name";
	private static final String PROJECT_NAME = "test project";
	private static final String PROJECT_DISPLAY_NAME = "test project display name";
	private static final String CONTAINER_NAME = "test container";
	private static final String IMAGE_REFERENCE = "image ref";
	private static final String DEPLOYMENT_NAME = "test deployment";
	private static final Integer REPLICAS = 3;
	private static final Integer READY_REPLICAS = 1;
	
	
//    DeploymentConfigDTO dc = new DeploymentConfigDTO(spec=DeploymentConfigDTO.DeploymentConfigSpecDTO(minReadySeconds=null, paused=null, replicas=0, revisionHistoryLimit=null, selector={name=cbanker-ext-stub}, strategy=DeploymentConfigDTO.DeploymentStrategyDTO(activeDeadlineSeconds=21600, annotations=null, customParams=null, labels=null, recreateParams=null, resources=ResourceRequirementsDTO(limits=null, requests=null), rollingParams=DeploymentConfigDTO.RollingDeploymentStrategyParamsDTO(intervalSeconds=1, maxSurge=IntOrStringDTO(value=25%), maxUnavailable=IntOrStringDTO(value=25%), post=null, pre=null, timeoutSeconds=120, updatePeriodSeconds=1), type=Rolling), template=PodTemplateSpecDTO(metadata=ObjectMetadataDTO(annotations=null, clusterName=null, creationTimestamp=null, deletionGracePeriodSeconds=null, deletionTimestamp=null, finalizers=null, generateName=null, generation=null, labels={name=cbanker-ext-stub}, managedFields=null, name=null, namespace=null, ownerReferences=null, resourceVersion=null, selfLink=null, uid=null), spec=PodSpecDTO(activeDeadlineSeconds=null, affinity=null, automountServiceAccountToken=null, containers=[ContainerDTO(args=null, command=null, env=[EnvVarDTO(name=NAMESPACE, value=null, valueFrom=EnvVarSourceDTO(configMapKeyRef=null, fieldRef=ObjectFieldSelectorDTO(apiVersion=v1, fieldPath=metadata.namespace), resourceFieldRef=null, secretKeyRef=null)), EnvVarDTO(name=POD_NAME, value=null, valueFrom=EnvVarSourceDTO(configMapKeyRef=null, fieldRef=ObjectFieldSelectorDTO(apiVersion=v1, fieldPath=metadata.name), resourceFieldRef=null, secretKeyRef=null)), EnvVarDTO(name=ARTIFACT_VERSION, value=1.0.0.0.14-SNAPSHOT, valueFrom=null)], envFrom=null, image=172.17.245.36:5000/1187/cbanker-ext-stub@sha256:94f7653fabf0fd23e8120b5c937edaac5c7997a39aa16688fe2255353ffa0e81, imagePullPolicy=IfNotPresent, lifecycle=null, livenessProbe=null, name=cbanker-ext-stub, ports=[ContainerPortDTO(containerPort=8080, hostIP=null, hostPort=null, name=null, protocol=TCP), ContainerPortDTO(containerPort=30000, hostIP=null, hostPort=null, name=null, protocol=TCP)], readinessProbe=ProbeDTO(exec=null, failureThreshold=3, httpGet=HTTPGetActionDTO(host=null, httpHeaders=null, path=/server/health, port=IntOrStringDTO(value=30000), scheme=HTTP), initialDelaySeconds=20, periodSeconds=10, successThreshold=1, tcpSocket=null, timeoutSeconds=5), resources=ResourceRequirementsDTO(limits=QuantityDTO(cpu=null, memory=QuantityValueDTO(value=384Mi)), requests=QuantityDTO(cpu=null, memory=QuantityValueDTO(value=200Mi))), securityContext=null, startupProbe=null, stdin=null, stdinOnce=null, terminationMessagePath=/dev/termination-log, terminationMessagePolicy=File, tty=null, volumeDevices=null, volumeMounts=[VolumeMountDTO(mountPath=/apps/cbanker-external-app-stub/config, mountPropagation=null, name=volume-mio35, readOnly=null, subPath=null, subPathExpr=null)], workingDir=null)])), test=false, trigger=null, triggers=[DeploymentConfigDTO.DeploymentTriggerPolicyDTO(imageChangeParams=io.openshift.dto.deploymentconfig.DeploymentConfigDTO$DeploymentTriggerImageChangeParamsDTO@1c87509c, type=ImageChange)]), status=DeploymentConfigDTO.DeploymentConfigStatusDTO(availableReplicas=0, condition=null, conditions=[DeploymentConfigDTO.DeploymentConditionDTO(lastTransitionTime=Thu Jun 06 09:40:54 CEST 2019, lastUpdateTime=Thu Jun 06 09:41:04 CEST 2019, message=replication controller "cbanker-ext-stub-4" successfully rolled out, reason=NewReplicationControllerAvailable, status=True, type=Progressing), DeploymentConfigDTO.DeploymentConditionDTO(lastTransitionTime=Thu Dec 26 05:40:56 CET 2019, lastUpdateTime=Thu Dec 26 05:40:56 CET 2019, message=Deployment config does not have minimum availability., reason=null, status=False, type=Available)], details=DeploymentConfigDTO.DeploymentDetailsDTO(cause=null, causes=[DeploymentConfigDTO.DeploymentCauseDTO(imageTrigger=DeploymentConfigDTO.DeploymentCauseImageTriggerDTO(from=ObjectReferenceDTO(apiVersion=null, fieldPath=null, kind=ImageStreamTag, name=cbanker-ext-stub:latest, namespace=1187, resourceVersion=null, uid=null)), type=ImageChange)], message=image change), latestVersion=4, observedGeneration=17, readyReplicas=null, replicas=0, unavailableReplicas=0, updatedReplicas=0));
	private final ModelMapper modelMapper = new Mapper();
	
	@Test
	public void canMapProjectToNamespace() {
		final ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setMetadata(ObjectMetadataDTO
				.builder()
				.name(PROJECT_NAME)
				.annotations(ImmutableMap.of(PROJECT_DISPLAY_NAME_ANNOTATION, PROJECT_DISPLAY_NAME))
				.build());
		final Namespace expectedNamespace = new Namespace().setName(PROJECT_NAME);
		
		final Namespace namespace = modelMapper.map(projectDTO, Namespace.class);
		assertThat(namespace).isNotNull();
		assertThat(namespace).isEqualTo(expectedNamespace);
	}
	
	@Test
	public void canMapContainer() {
		final ContainerDTO containerDTO = new ContainerDTO();
		containerDTO.setName(CONTAINER_NAME);
		containerDTO.setImage(IMAGE_REFERENCE);
		final Container expectedContainer = new Container().setName(CONTAINER_NAME).setImageReference(IMAGE_REFERENCE);
		
		final Container container = modelMapper.map(containerDTO, Container.class);
		assertThat(container).isNotNull();
		assertThat(container).isEqualTo(expectedContainer);
	}
	
	@Test
	public void canMapDeploymentConfig() {
		final DeploymentConfigDTO deploymentConfigDTO = new DeploymentConfigDTO();
		deploymentConfigDTO.setMetadata(ObjectMetadataDTO.builder().name(DEPLOYMENT_NAME).build());
		deploymentConfigDTO.setStatus(DeploymentConfigStatusDTO
				.builder()
				.replicas(REPLICAS)
				.readyReplicas(READY_REPLICAS)
				.build());
		final DeploymentConfig expectedDeploymentConfig = new DeploymentConfig()
				.setName(DEPLOYMENT_NAME)
				.setReadyReplicas(READY_REPLICAS)
				.setReplicas(REPLICAS);
		
		final DeploymentConfig deploymentConfig = modelMapper.map(deploymentConfigDTO, DeploymentConfig.class);
		assertThat(deploymentConfig).isNotNull();
		assertThat(deploymentConfig).isEqualTo(expectedDeploymentConfig);		
	}

	@Test
	public void whenMappingDeploymentConfigWithNullReplicasThenReturnZero() {
		final DeploymentConfigDTO deploymentConfigDTO = new DeploymentConfigDTO();
		deploymentConfigDTO.setMetadata(ObjectMetadataDTO.builder().name(DEPLOYMENT_NAME).build());
		deploymentConfigDTO.setStatus(DeploymentConfigStatusDTO
				.builder()
				.replicas(null)
				.readyReplicas(null)
				.build());
		final DeploymentConfig expectedDeploymentConfig = new DeploymentConfig()
				.setName(DEPLOYMENT_NAME)
				.setReadyReplicas(0)
				.setReplicas(0);
		
		final DeploymentConfig deploymentConfig = modelMapper.map(deploymentConfigDTO, DeploymentConfig.class);
		assertThat(deploymentConfig).isNotNull();
		assertThat(deploymentConfig).isEqualTo(expectedDeploymentConfig);		
	}

	
	@Test
	public void canMapDeploymentConfigWithContainers() {
		final ContainerDTO containerDTO = new ContainerDTO();
		containerDTO.setName(CONTAINER_NAME);
		containerDTO.setImage(IMAGE_REFERENCE);
		final Container expectedContainer = new Container().setName(CONTAINER_NAME).setImageReference(IMAGE_REFERENCE);
		
		final DeploymentConfigDTO deploymentConfigDTO = new DeploymentConfigDTO();
		deploymentConfigDTO.setMetadata(ObjectMetadataDTO.builder().name(DEPLOYMENT_NAME).build());
		deploymentConfigDTO.setSpec(DeploymentConfigSpecDTO
				.builder()
				.template(PodTemplateSpecDTO
						.builder()
						.spec(PodSpecDTO
								.builder()
								.containers(
										ImmutableList.of(containerDTO))
								.build())
						.build())
				.build());
		final DeploymentConfig expectedDeploymentConfig = new DeploymentConfig()
				.setName(DEPLOYMENT_NAME)
				.setReadyReplicas(0)
				.setReplicas(0)
				.setContainers(ImmutableList.of(expectedContainer));
		
		final DeploymentConfig deploymentConfig = modelMapper.map(deploymentConfigDTO, DeploymentConfig.class);
		assertThat(deploymentConfig).isNotNull();
		assertThat(deploymentConfig).isEqualTo(expectedDeploymentConfig);		
	}

	
	@Test
	public void canMapDeploymentConfigWithoutContainers() {
		final DeploymentConfigDTO deploymentConfigDTO = new DeploymentConfigDTO();
		deploymentConfigDTO.setMetadata(ObjectMetadataDTO.builder().name(DEPLOYMENT_NAME).build());
		deploymentConfigDTO.setSpec(DeploymentConfigSpecDTO
				.builder()
				.template(PodTemplateSpecDTO
						.builder()
						.spec(PodSpecDTO
								.builder()
								.containers(null)
								.build())
						.build())
				.build());
		final DeploymentConfig expectedDeploymentConfig = new DeploymentConfig()
				.setName(DEPLOYMENT_NAME)
				.setReadyReplicas(0)
				.setReplicas(0);
		
		final DeploymentConfig deploymentConfig = modelMapper.map(deploymentConfigDTO, DeploymentConfig.class);
		assertThat(deploymentConfig).isNotNull();
		assertThat(deploymentConfig).isEqualTo(expectedDeploymentConfig);		
	}

}
