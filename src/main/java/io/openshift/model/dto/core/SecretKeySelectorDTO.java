package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * SecretKeySelector [core/v1]
 * Description
 * SecretKeySelector selects a key of a Secret.
 */
public class SecretKeySelectorDTO {
    /**
     * The key of the secret to select from. Must be a valid secret key.
     */
    private String key;

    /**
     * Name of the referent.
     * More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/names/#names
     */
    private String name;

    /**
     * Specify whether the Secret or its key must be defined
     */
    private Boolean optional;
}
