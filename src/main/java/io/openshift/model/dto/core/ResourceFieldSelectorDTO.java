package io.openshift.model.dto.core;

import io.openshift.model.dto.api.resource.QuantityDTO;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * ResourceFieldSelector [core/v1]
 * Description
 * ResourceFieldSelector represents container resources (cpu, memory) and their output format
 */
public class ResourceFieldSelectorDTO {
    /**
     * Container name: required for volumes, optional for env vars
     */
    private String containerName;

    /**
     * Specifies the output format of the exposed resources, defaults to "1"
     */
    private QuantityDTO divisor;

    /**
     * Required: resource to select
     */
    private String resource;
}
