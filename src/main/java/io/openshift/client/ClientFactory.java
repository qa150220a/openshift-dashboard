package io.openshift.client;

import io.openshift.client.impl.Client;
import io.openshift.model.domain.Cluster;

public interface ClientFactory {
	Client getClientForCluster(final Cluster cluster) throws ClientException;
}
