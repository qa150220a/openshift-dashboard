package io.openshift.service;

import java.util.List;
import java.util.Optional;

import io.openshift.client.ClientException;
import io.openshift.model.domain.Cluster;
import io.openshift.model.domain.DeploymentConfig;
import io.openshift.model.domain.Namespace;

public interface ClusterService {
    List<Cluster> getAllClusters();
    Optional<Cluster> findClusterByName(String clusterName);
    List<Namespace> getNamespacesForCluster(Cluster cluster) throws ClientException;
    List<DeploymentConfig> getDeploymentConfigsForClusterAndNamespace(Cluster cluster, String namespace) throws ClientException;
}
