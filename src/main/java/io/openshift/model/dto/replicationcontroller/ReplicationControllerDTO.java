package io.openshift.model.dto.replicationcontroller;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

import io.openshift.model.dto.ApiObjectDTO;
import io.openshift.model.dto.core.PodTemplateSpecDTO;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
/**
 * https://docs.openshift.com/container-platform/4.5/rest_api/workloads_apis/replicationcontroller-core-v1.html
 * ReplicationController [core/v1]
 * Specification
 * API endpoints
 * /api/v1/replicationcontrollers
 * /api/v1/namespaces/{namespace}/replicationcontrollers
 * /api/v1/namespaces/{namespace}/replicationcontrollers/{name}
 * /api/v1/namespaces/{namespace}/replicationcontrollers/{name}/scale
 * /api/v1/namespaces/{namespace}/replicationcontrollers/{name}/status
 * Description
 * ReplicationController represents the configuration of a replication controller.
 */
public class ReplicationControllerDTO extends ApiObjectDTO {
    /**
     * ReplicationControllerSpec is the specification of a replication controller.
     */
    private ReplicationControllerSpecDTO spec;

    /**
     * ReplicationControllerStatus represents the current status of a replication controller.
     */
    private ReplicationControllerStatusDTO status;

    @Data
    @ToString
    public static class ReplicationControllerSpecDTO {
        /**
         * Minimum number of seconds for which a newly created pod should be ready without any of its container crashing,
         * for it to be considered available. Defaults to 0 (pod will be considered available as soon as it is ready)
         */
        private Integer minReadySeconds;

        /**
         * Replicas is the number of desired replicas. This is a pointer to distinguish between explicit zero and
         * unspecified. Defaults to 1.
         * More info: https://kubernetes.io/docs/concepts/workloads/controllers/replicationcontroller#what-is-a-replicationcontroller
         */
        private Integer replicas;

        /**
         *
         * Selector is a label query over pods that should match the Replicas count. If Selector is empty,
         * it is defaulted to the labels present on the Pod template. Label keys and values that must match
         * in order to be controlled by this replication controller, if empty defaulted to labels on Pod template.
         * More info: https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/#label-selectors
         */
        private Object selector;

        /**
         * PodTemplateSpec describes the data a pod should have when created from a template
         */
        private PodTemplateSpecDTO template;
    }


    @Data
    @ToString
    public static class ReplicationControllerStatusDTO {
        /**
         * The number of available replicas (ready for at least minReadySeconds) for this replication controller.
         */
        private Integer availableReplicas;

        /**
         * Represents the latest available observations of a replication controller’s current state.
         */
        private List<ReplicationControllerConditionDTO> conditions;

        /**
         * The number of pods that have labels matching the labels of the pod template of the replication controller.
         */
        private Integer fullyLabeledReplicas;

        /**
         * ObservedGeneration reflects the generation of the most recently observed replication controller.
         */
        private Integer observedGeneration;

        /**
         * The number of ready replicas for this replication controller.
         */
        private Integer readyReplicas;

        /**
         * Replicas is the most recently oberved number of replicas.
         * More info: https://kubernetes.io/docs/concepts/workloads/controllers/replicationcontroller#what-is-a-replicationcontroller
         */
        private Integer replicas;
    }

    @Data
    @ToString
    public static class ReplicationControllerConditionDTO {
        /**
         * The last time the condition transitioned from one status to another.
         */
        private Date lastTransitionTime;

        /**
         * A human readable message indicating details about the transition.
         */
        private String message;

        /**
         * The reason for the condition’s last transition.
         */
        private String reason;

        /**
         * Status of the condition, one of True, False, Unknown.
         */
        private String status;

        /**
         * Type of replication controller condition.
         */
        private String type;
    }
}
