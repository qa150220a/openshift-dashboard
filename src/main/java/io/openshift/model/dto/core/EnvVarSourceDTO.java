package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * EnvVarSource [core/v1]
 * Description
 * EnvVarSource represents a source for the value of an EnvVar.
 */
public class EnvVarSourceDTO {
    /**
     * Selects a key of a ConfigMap.
     */
    private ConfigMapKeySelectorDTO configMapKeyRef;

    /**
     * Selects a field of the pod: supports metadata.name, metadata.namespace, metadata.labels, metadata.annotations,
     * spec.nodeName, spec.serviceAccountName, status.hostIP, status.podIP, status.podIPs.
     */
    private ObjectFieldSelectorDTO fieldRef;

    /**
     * Selects a resource of the container: only resources limits and requests (limits.cpu, limits.memory,
     * limits.ephemeral-storage, requests.cpu, requests.memory and requests.ephemeral-storage) are currently supported.
     */
    private ResourceFieldSelectorDTO resourceFieldRef;

    /**
     * Selects a key of a secret in the pod’s namespace
     */
    private SecretKeySelectorDTO secretKeyRef;
}
