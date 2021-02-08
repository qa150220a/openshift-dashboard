package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * ConfigMapEnvSource [core/v1]
 * Description
 * ConfigMapEnvSource selects a ConfigMap to populate the environment variables with.
 *
 * The contents of the target ConfigMap's Data field will represent the key-value pairs as environment variables.
 */
public class ConfigMapEnvSourceDTO {
    /**
     * Name of the referent.
     * More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/names/#names
     */
    private String name;

    /**
     * Specify whether the ConfigMap must be defined
     */
    private Boolean optional;
}
