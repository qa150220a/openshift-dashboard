package io.openshift.client.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;

import io.openshift.client.ClientException;
import io.openshift.client.ClientFactory;
import io.openshift.model.domain.Cluster;

@Service
public class ClientFactoryImpl implements ClientFactory {
    private final ReentrantLock lock = new ReentrantLock();
    
    private static final Map<String, Client> clients = new HashMap<>();
    
	@Override
	public Client getClientForCluster(Cluster cluster) throws ClientException {
        lock.lock();
        try {
            Client client = clients.get(cluster.getName());
            if (null == client) {
            	client = Client.builder().baseUrl(cluster.getUrl()).build();
               	clients.put(cluster.getName(), client);
//            	throw new ClientException("unknown cluster " + cluster.getName());
            }
            return client;
        } finally {
            lock.unlock();
        }
	}

}
