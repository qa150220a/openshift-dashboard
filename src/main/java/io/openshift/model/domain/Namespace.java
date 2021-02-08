package io.openshift.model.domain;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Namespace {
    private String name;
    private String displayName;
    private List<DeploymentConfig> deploymentConfigs;
}
