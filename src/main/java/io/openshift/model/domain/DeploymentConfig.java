package io.openshift.model.domain;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class DeploymentConfig {
    private String name;
    private Integer replicas;
    private Integer readyReplicas;
    private List<Container> containers;
}
