package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * EnvFromSource [core/v1]
 * Description
 * EnvFromSource represents the source of a set of ConfigMaps
 */
public class EnvFromSourceDTO {
    /**
     * The ConfigMap to select from
     */
    private ConfigMapEnvSourceDTO configMapRef;

    /**
     * An optional identifier to prepend to each key in the ConfigMap. Must be a C_IDENTIFIER.
     */
    private String prefix;

    /**
     * The Secret to select from
     */
    private SecretEnvSourceDTO secretRef;
}
