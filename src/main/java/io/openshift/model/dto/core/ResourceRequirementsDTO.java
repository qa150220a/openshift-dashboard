package io.openshift.model.dto.core;

import io.openshift.model.dto.api.resource.QuantityDTO;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * ResourceRequirements [core/v1]
 * Description
 * ResourceRequirements describes the compute resource requirements.
 */
public class ResourceRequirementsDTO {
    /**
     * Limits describes the maximum amount of compute resources allowed.
     * More info: https://kubernetes.io/docs/concepts/configuration/manage-compute-resources-container/
     */
    private QuantityDTO limits;

    /**
     * Requests describes the minimum amount of compute resources required. If Requests is omitted for a container,
     * it defaults to Limits if that is explicitly specified, otherwise to an implementation-defined value.
     * More info: https://kubernetes.io/docs/concepts/configuration/manage-compute-resources-container/
     */
    private QuantityDTO requests;
}
