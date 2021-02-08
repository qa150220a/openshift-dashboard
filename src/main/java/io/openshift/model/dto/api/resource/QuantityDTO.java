package io.openshift.model.dto.api.resource;

import lombok.Data;
import lombok.ToString;

/**
 * see https://docs.openshift.com/container-platform/4.5/rest_api/objects/index.html#quantity-api-resource
 */
@Data
@ToString
public class QuantityDTO {
    /**
     * Quantity is a fixed-point representation of a number. It provides convenient marshaling/unmarshaling in JSON and YAML, in addition to String() and AsInt64() accessors.
     */
    private QuantityValueDTO cpu;
    private QuantityValueDTO memory;
}
