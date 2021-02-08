package io.openshift.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.openshift.client.ClientException;
import io.openshift.model.domain.Cluster;
import io.openshift.model.domain.DeploymentConfig;
import io.openshift.model.domain.Namespace;
import io.openshift.service.ClusterService;

@RestController
@RequestMapping("/cluster")
@CrossOrigin(origins = { "http://localhost:3000", "http://127.0.0.1:3000", "http://localhost:3080", "http://127.0.0.1:3080" })
public class ClusterController {
    @Autowired
    private ClusterService clusterService;

    @GetMapping
    public List<Cluster> all() {
        return clusterService.getAllClusters();
    }

    @GetMapping("{clusterName}/ns")
    public ResponseEntity<List<Namespace>> getNamespacesByClusterName(@PathVariable String clusterName) throws ClientException {
        Cluster cluster = clusterService.findClusterByName(clusterName).orElse(null);
        if (null == cluster) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clusterService.getNamespacesForCluster(cluster), HttpStatus.OK);
    }

    @GetMapping("{clusterName}/ns/{namespace}/dc")
    public ResponseEntity<List<DeploymentConfig>> getDeploymentConfigsByClusterName(@PathVariable String clusterName, @PathVariable String namespace) throws ClientException {
        Cluster cluster = clusterService.findClusterByName(clusterName).orElse(null);
        if (null == cluster) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clusterService.getDeploymentConfigsForClusterAndNamespace(cluster, namespace), HttpStatus.OK);
    }

}
