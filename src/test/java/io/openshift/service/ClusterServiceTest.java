package io.openshift.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.google.common.collect.ImmutableList;

import io.openshift.client.ClientException;
import io.openshift.client.ClientFactory;
import io.openshift.client.impl.Client;
import io.openshift.dashboard.App;
import io.openshift.model.config.ClusterServiceProperties;
import io.openshift.model.domain.Cluster;
import io.openshift.model.domain.DeploymentConfig;
import io.openshift.model.domain.Namespace;
import io.openshift.model.dto.ObjectMetadataDTO;
import io.openshift.model.dto.deploymentconfig.DeploymentConfigDTO;
import io.openshift.model.dto.deploymentconfig.DeploymentConfigListDTO;
import io.openshift.model.dto.project.ProjectDTO;
import io.openshift.model.dto.project.ProjectListDTO;
import io.openshift.model.mapper.Mapper;
import io.openshift.service.impl.ClusterServiceImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ClusterServiceImpl.class, Mapper.class, ClusterServiceProperties.class, App.class })
@SpringBootTest
public class ClusterServiceTest {
	@Autowired
	private ClusterService clusterService;

	@MockBean
	private ClientFactory clientFactory;
	
	@Mock
	private Client client;
	
	private final static String FIRST_CLUSTER_NAME = "cluster alpha";
	private final static String INVALID_CLUSTER_NAME = "cluster void";

	@Test
	public void canGetAllClusters() {
		final List<Cluster> clusters = clusterService.getAllClusters();
		assertThat(clusters).isNotEmpty();
	}

	@Test
	public void canFindClusterByName() {
		final Optional<Cluster> cluster = clusterService.findClusterByName(FIRST_CLUSTER_NAME);
		assertThat(cluster.isPresent()).isTrue();
		assertThat(cluster.get().getName()).isEqualTo(FIRST_CLUSTER_NAME);
	}

	@Test
	public void whenSearchingClusterByUnknownNameThenReturnNone() {
		final Optional<Cluster> cluster = clusterService.findClusterByName(INVALID_CLUSTER_NAME);
		assertThat(cluster.isPresent()).isFalse();
	}

	@Test
	public void whenSearchingClusterByNullThenThrowException() {
		Assertions.assertThrows(AssertionError.class, () -> {
			clusterService.findClusterByName(null);
		});
	}

	@Test
	public void whenObtainingNamespacesForClusterWithoutProjectsThenReturnEmptyList() throws ClientException {
		final Cluster cluster = Cluster.builder().name(FIRST_CLUSTER_NAME).build();
		final ProjectListDTO projectListDTO = new ProjectListDTO();
		final List<ProjectDTO> projects = ImmutableList.of();
		projectListDTO.setItems(projects);
		
		Mockito.when(clientFactory.getClientForCluster(cluster))
			.thenReturn(client);
		Mockito.when(client.getProjects())
			.thenReturn(projectListDTO);
		
		final List<Namespace> namespaces = clusterService.getNamespacesForCluster(cluster);
		assertThat(namespaces).isNotNull();
		assertThat(namespaces).hasSize(0);
		
		Mockito.verify(clientFactory, Mockito.times(1)).getClientForCluster(cluster);
		Mockito.verifyNoMoreInteractions(clientFactory);
		Mockito.verify(client, Mockito.times(1)).getProjects();
    	Mockito.verifyNoMoreInteractions(client);
	}
	
	@Test
	public void whenObtainingNamespacesForClusterWithProjectThenReturnMappedListInCorrectOrder() throws ClientException {
		final String FIRST_PROJECT_NAME = "project alpha";
		final String SECOND_PROJECT_NAME = "project bravo";
				
		final Cluster cluster = Cluster.builder().name(FIRST_CLUSTER_NAME).build();
		final ProjectListDTO projectListDTO = new ProjectListDTO();
		final ProjectDTO projectOneDTO = new ProjectDTO();
		projectOneDTO.setMetadata(ObjectMetadataDTO
				.builder()
				.name(FIRST_PROJECT_NAME)
				.build());
		final ProjectDTO projectTwoDTO = new ProjectDTO();
		projectTwoDTO.setMetadata(ObjectMetadataDTO
				.builder()
				.name(SECOND_PROJECT_NAME)
				.build());
		final List<ProjectDTO> projects = ImmutableList.of(projectOneDTO, projectTwoDTO);
		projectListDTO.setItems(projects);
		
		Mockito.when(clientFactory.getClientForCluster(cluster))
			.thenReturn(client);
		Mockito.when(client.getProjects())
			.thenReturn(projectListDTO);
		
		final List<Namespace> namespaces = clusterService.getNamespacesForCluster(cluster);
		assertThat(namespaces).isNotNull();
		assertThat(namespaces).hasSize(2);
		assertThat(namespaces.get(0).getName()).isEqualTo(FIRST_PROJECT_NAME);
		assertThat(namespaces.get(1).getName()).isEqualTo(SECOND_PROJECT_NAME);
		
		Mockito.verify(clientFactory, Mockito.times(1)).getClientForCluster(cluster);
		Mockito.verifyNoMoreInteractions(clientFactory);
		Mockito.verify(client, Mockito.times(1)).getProjects();
    	Mockito.verifyNoMoreInteractions(client);
	}

	@Test
	public void whenObtainingDeploymentConfigsForClusterAndNameSpaceWithoutDeploymentsThenReturnEmptyList() throws ClientException {
		final Cluster cluster = Cluster.builder().name(FIRST_CLUSTER_NAME).build();
		final String namespaceName = "namespace alpha";
		
		final DeploymentConfigListDTO deploymentConfigListDTO = new DeploymentConfigListDTO();
		final List<DeploymentConfigDTO> deployments = ImmutableList.of();
		deploymentConfigListDTO.setItems(deployments);
		
		Mockito.when(clientFactory.getClientForCluster(cluster))
			.thenReturn(client);
		Mockito.when(client.getDeploymentConfigsForNameSpace(namespaceName))
			.thenReturn(deploymentConfigListDTO);
		
		final List<DeploymentConfig> dcs = clusterService.getDeploymentConfigsForClusterAndNamespace(cluster, namespaceName);
		assertThat(dcs).isNotNull();
		assertThat(dcs).hasSize(0);
		
		Mockito.verify(clientFactory, Mockito.times(1)).getClientForCluster(cluster);
		Mockito.verifyNoMoreInteractions(clientFactory);
		Mockito.verify(client, Mockito.times(1)).getDeploymentConfigsForNameSpace(namespaceName);
    	Mockito.verifyNoMoreInteractions(client);
	}
	
	@Test
	public void whenObtainingDeploymentConfigsForClusterAndNameSpaceWithDeploymentsThenReturnListInCorrectOrder() throws ClientException {
		final String FIRST_DEPLOYMENT_NAME = "deployment alpha";
		final String SECOND_DEPLOYMENT_NAME = "deployment bravo";
		final Cluster cluster = Cluster.builder().name(FIRST_CLUSTER_NAME).build();
		final String namespaceName = "namespace alpha";
		
		final DeploymentConfigListDTO deploymentConfigListDTO = new DeploymentConfigListDTO();
		DeploymentConfigDTO deploymentOne = new DeploymentConfigDTO();
		deploymentOne.setMetadata(ObjectMetadataDTO
				.builder()
				.name(FIRST_DEPLOYMENT_NAME)
				.build());
		DeploymentConfigDTO deploymentTwo = new DeploymentConfigDTO();
		deploymentTwo.setMetadata(ObjectMetadataDTO
				.builder()
				.name(SECOND_DEPLOYMENT_NAME)
				.build());
		final List<DeploymentConfigDTO> deployments = ImmutableList.of(deploymentOne, deploymentTwo);
		deploymentConfigListDTO.setItems(deployments);
		
		Mockito.when(clientFactory.getClientForCluster(cluster))
			.thenReturn(client);
		Mockito.when(client.getDeploymentConfigsForNameSpace(namespaceName))
			.thenReturn(deploymentConfigListDTO);
		
		final List<DeploymentConfig> dcs = clusterService.getDeploymentConfigsForClusterAndNamespace(cluster, namespaceName);
		assertThat(dcs).isNotNull();
		assertThat(dcs).hasSize(2);
		assertThat(dcs.get(0).getName()).isEqualTo(FIRST_DEPLOYMENT_NAME);
		assertThat(dcs.get(1).getName()).isEqualTo(SECOND_DEPLOYMENT_NAME);
		
		Mockito.verify(clientFactory, Mockito.times(1)).getClientForCluster(cluster);
		Mockito.verifyNoMoreInteractions(clientFactory);
		Mockito.verify(client, Mockito.times(1)).getDeploymentConfigsForNameSpace(namespaceName);
    	Mockito.verifyNoMoreInteractions(client);
	}
}
