package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * SecretEnvSource [core/v1]
 * Description
 * SecretEnvSource selects a Secret to populate the environment variables with.
 *
 * The contents of the target Secret's Data field will represent the key-value pairs as environment variables.
 */
public class SecretEnvSourceDTO {
    /**
     * Name of the referent. More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/names/#names
     */
    private String name;

    /**
     * Specify whether the Secret must be defined
     */
    private Boolean optional;
}
