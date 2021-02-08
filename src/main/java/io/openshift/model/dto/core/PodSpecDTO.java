package io.openshift.model.dto.core;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@Builder
@ToString
/**
 * https://docs.openshift.com/container-platform/4.5/rest_api/objects/index.html#podspec-core-v1
 * PodSpec [core/v1]
 * Description
 * PodSpec is a description of a pod.
 */
public class PodSpecDTO {
    /**
     * Optional duration in seconds the pod may be active on the node relative to StartTime before
     * the system will actively try to mark it failed and kill associated containers.
     * Value must be a positive integer.
     */
    private Integer activeDeadlineSeconds;

    /**
     * If specified, the pod’s scheduling constraints
     */
    private AffinityDTO affinity;

    /**
     * AutomountServiceAccountToken indicates whether a service account token should be automatically mounted.
     */
    private Boolean automountServiceAccountToken;

    /**
     * List of containers belonging to the pod. Containers cannot currently be added or removed. There must be
     * at least one container in a Pod. Cannot be updated.
     */
    private List<ContainerDTO> containers;

    // TODO:...
}




