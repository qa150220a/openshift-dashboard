package io.openshift.model.dto.deploymentconfig;

import java.util.List;

import io.openshift.model.dto.ApiObjectDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class DeploymentConfigListDTO extends ApiObjectDTO {
    private List<DeploymentConfigDTO> items;
}
