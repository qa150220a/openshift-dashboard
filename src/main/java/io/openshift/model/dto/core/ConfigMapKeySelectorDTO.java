package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * ConfigMapKeySelector [core/v1]
 * Description
 * Selects a key from a ConfigMap.
 */
public class ConfigMapKeySelectorDTO {
    /**
     * The key to select.
     */
    private String key;

    /**
     * Name of the referent.
     * More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/names/#names
     */
    private String name;

    /**
     * Specify whether the ConfigMap or its key must be defined
     */
    private Boolean optional;
}
