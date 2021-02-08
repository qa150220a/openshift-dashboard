package io.openshift.model.dto.deploymentconfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.openshift.model.dto.ApiObjectDTO;
import io.openshift.model.dto.ObjectReferenceDTO;
import io.openshift.model.dto.api.util.IntOrStringDTO;
import io.openshift.model.dto.core.EnvVarDTO;
import io.openshift.model.dto.core.PodTemplateSpecDTO;
import io.openshift.model.dto.core.ResourceRequirementsDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
/**
 * https://docs.openshift.com/container-platform/4.5/rest_api/workloads_apis/deploymentconfig-apps-openshift-io-v1.html
 * DeploymentConfig [apps.openshift.io/v1]
 * Specification
 * API endpoints
 * /apis/apps.openshift.io/v1/deploymentconfigs
 * /apis/apps.openshift.io/v1/namespaces/{namespace}/deploymentconfigs
 * /apis/apps.openshift.io/v1/namespaces/{namespace}/deploymentconfigs/{name}
 * /apis/apps.openshift.io/v1/namespaces/{namespace}/deploymentconfigs/{name}/log
 * /apis/apps.openshift.io/v1/namespaces/{namespace}/deploymentconfigs/{name}/scale
 * /apis/apps.openshift.io/v1/namespaces/{namespace}/deploymentconfigs/{name}/status
 * /apis/apps.openshift.io/v1/namespaces/{namespace}/deploymentconfigs/{name}/rollback
 * /apis/apps.openshift.io/v1/namespaces/{namespace}/deploymentconfigs/{name}/instantiate
 * Description
 * Deployment Configs define the template for a pod and manages deploying new images or configuration changes.
 * A single deployment configuration is usually analogous to a single micro-service. Can support many different
 * deployment patterns, including full restart, customizable rolling updates, and fully custom behaviors, as well
 * as pre- and post- deployment hooks. Each individual deployment is represented as a replication controller.
 *
 * A deployment is "triggered" when its configuration is changed or a tag in an Image Stream is changed.
 * Triggers can be disabled to allow manual control over a deployment. The "strategy" determines how the deployment
 * is carried out and may be changed at any time. The `latestVersion` field is updated when a new deployment is
 * triggered by any means.
 */
public class DeploymentConfigDTO extends ApiObjectDTO {
    /**
     * DeploymentConfigSpec represents the desired state of the deployment.
     */
    private DeploymentConfigSpecDTO spec;

    /**
     * DeploymentConfigStatus represents the current deployment state.
     */
    private DeploymentConfigStatusDTO status;

    @Data
    @Builder
    @ToString
    public static class DeploymentConfigSpecDTO {
        /**
         * MinReadySeconds is the minimum number of seconds for which a newly created pod should be ready without
         * any of its container crashing, for it to be considered available.
         * Defaults to 0 (pod will be considered available as soon as it is ready)
         */
        private Integer minReadySeconds;

        /**
         * Paused indicates that the deployment config is paused resulting in no new deployments on template
         * changes or changes in the template caused by other triggers.
         */
        private Boolean paused;

        /**
         * Replicas is the number of desired replicas.
         */
        private Integer replicas;

        /**
         * RevisionHistoryLimit is the number of old ReplicationControllers to retain to allow for rollbacks.
         * This field is a pointer to allow for differentiation between an explicit zero and not specified.
         * Defaults to 10. (This only applies to DeploymentConfigs created via the new group API resource,
         * not the legacy resource.)
         */
        private Integer revisionHistoryLimit;

        /**
         * Selector is a label query over pods that should match the Replicas count.
         */
        private Map<String, String> selector;

        /**
         * DeploymentStrategy describes how to perform a deployment.
         */
        private DeploymentStrategyDTO strategy;

        /**
         * Template is the object that describes the pod that will be created if insufficient replicas are detected.
         */
        private PodTemplateSpecDTO template;

        /**
         * Test ensures that this deployment config will have zero replicas except while a deployment is running.
         * This allows the deployment config to be used as a continuous deployment test - triggering on images,
         * running the deployment, and then succeeding or failing. Post strategy hooks and After actions can be used
         * to integrate successful deployment with an action.
         */
        private Boolean test;

        /**
         * Triggers determine how updates to a DeploymentConfig result in new deployments. If no triggers are defined,
         * a new deployment can only occur as a result of an explicit client update to the DeploymentConfig with a new
         * LatestVersion. If null, defaults to having a config change trigger.
         */
        private DeploymentTriggerPolicyDTO trigger;

        /**
         * DeploymentTriggerPolicy describes a policy for a single trigger that results in a new deployment.
         */
        private List<DeploymentTriggerPolicyDTO> triggers;
    }

    @Data
    @ToString
    public static class DeploymentTriggerPolicyDTO {
        /**
         * DeploymentTriggerImageChangeParams represents the parameters to the ImageChange trigger.
         */
        private DeploymentTriggerImageChangeParamsDTO imageChangeParams;

        /**
         * Type of the trigger
         */
        private String type;
    }

    @Data
    @ToString
    public static class DeploymentTriggerImageChangeParamsDTO {
        /**
         * Automatic means that the detection of a new tag value should result in an image update inside the pod template.
         */
        private Boolean automatic;

        /**
         * ContainerNames is used to restrict tag updates to the specified set of container names in a pod.
         * If multiple triggers point to the same containers, the resulting behavior is undefined. Future API versions
         * will make this a validation error. If ContainerNames does not point to a valid container, the trigger will
         * be ignored. Future API versions will make this a validation error.
         */
        private List<String> containerNames;

        /**
         * From is a reference to an image stream tag to watch for changes. From.Name is the only required subfield
         * - if From.Namespace is blank, the namespace of the current deployment trigger will be used.
         */
        private ObjectReferenceDTO from;

        /**
         * LastTriggeredImage is the last image to be triggered.
         */
        private String lastTriggeredImage;
    }

    @Data
    @ToString
    public static class DeploymentStrategyDTO {
        /**
         * ActiveDeadlineSeconds is the duration in seconds that the deployer pods for this deployment config may
         * be active on a node before the system actively tries to terminate them.
          */
        private Integer activeDeadlineSeconds;

        /**
         * Annotations is a set of key, value pairs added to custom deployer and lifecycle pre/post hook pods.
         */
        private Map<String, String> annotations;

        /**
         * CustomDeploymentStrategyParams are the input to the Custom deployment strategy.
         */
        private CustomDeploymentStrategyParamsDTO customParams;

        /**
         * Labels is a set of key, value pairs added to custom deployer and lifecycle pre/post hook pods.
         */
        private Map<String, String> labels;

        /**
         * RecreateDeploymentStrategyParams are the input to the Recreate deployment strategy.
         */
        private RecreateDeploymentStrategyParamsDTO recreateParams;

        /**
         * Resources contains resource requirements to execute the deployment and any hooks.
         */
        private ResourceRequirementsDTO resources;

        /**
         * RollingDeploymentStrategyParams are the input to the Rolling deployment strategy.
         */
        private RollingDeploymentStrategyParamsDTO rollingParams;

        /**
         * Type is the name of a deployment strategy.
         */
        private String type;
    }

    @Data
    @ToString
    public static class CustomDeploymentStrategyParamsDTO {
        /**
         * Command is optional and overrides CMD in the container Image.
         */
        private List<String> command;

        /**
         * Environment holds the environment which will be given to the container for Image.
         */
        private List<Object> environment;

        /**
         * Image specifies a container image which can carry out a deployment.
         */
        private String image;
    }

    @Data
    @ToString
    public static class RecreateDeploymentStrategyParamsDTO {
        // TODO
    }

    /**
     * .spec.strategy.rollingParams
     * Description
     * RollingDeploymentStrategyParams are the input to the Rolling deployment strategy.
     */
    @Data
    @ToString
    public static class RollingDeploymentStrategyParamsDTO {
        /**
         * IntervalSeconds is the time to wait between polling deployment status after update.
         * If the value is nil, a default will be used.
         */
        private Integer intervalSeconds;

        /**
         * MaxSurge is the maximum number of pods that can be scheduled above the original number of pods. Value can
         * be an absolute number (ex: 5) or a percentage of total pods at the start of the update (ex: 10%).
         * Absolute number is calculated from percentage by rounding up.
         *
         * This cannot be 0 if MaxUnavailable is 0. By default, 25% is used.
         *
         * Example: when this is set to 30%, the new RC can be scaled up by 30% immediately when the rolling update
         * starts. Once old pods have been killed, new RC can be scaled up further, ensuring that total number of
         * pods running at any time during the update is atmost 130% of original pods.
         */
        private IntOrStringDTO maxSurge;

        /**
         *
         * MaxUnavailable is the maximum number of pods that can be unavailable during the update. Value can be
         * an absolute number (ex: 5) or a percentage of total pods at the start of update (ex: 10%). Absolute number
         * is calculated from percentage by rounding down.
         *
         * This cannot be 0 if MaxSurge is 0. By default, 25% is used.
         *
         * Example: when this is set to 30%, the old RC can be scaled down by 30% immediately when the rolling update
         * starts. Once new pods are ready, old RC can be scaled down further, followed by scaling up the new RC,
         * ensuring that at least 70% of original number of pods are available at all times during the update.
         */
        private IntOrStringDTO maxUnavailable;

        /**
         * LifecycleHook defines a specific deployment lifecycle action. Only one type of action may be
         * specified at any time.
         */
        private LifecycleHookDTO post;

        /**
         * LifecycleHook defines a specific deployment lifecycle action. Only one type of action may be
         * specified at any time.
         */
        private LifecycleHookDTO pre;

        /**
         * TimeoutSeconds is the time to wait for updates before giving up. If the value is nil, a default will be used.
         */
        private Integer timeoutSeconds;

        /**
         * UpdatePeriodSeconds is the time to wait between individual pod updates. If the value is nil, a default will be used.
         */
        private Integer updatePeriodSeconds;
    }

    /**
     * .spec.strategy.rollingParams.post
     * Description
     * LifecycleHook defines a specific deployment lifecycle action. Only one type of action may be specified at any time.
     */
    @Data
    @ToString
    public static class LifecycleHookDTO {
        /**
         * ExecNewPodHook is a hook implementation which runs a command in a new pod based on the specified container
         * which is assumed to be part of the deployment template.
         */
        private ExecNewPodHookDTO execNewPod;

        /**
         * FailurePolicy specifies what action to take if the hook fails.
         */
        private String failurePolicy;

        /**
         * TagImages instructs the deployer to tag the current image referenced under a container onto an image stream tag.
         */
        private TagImageHookDTO tagImage;

        /**
         * TagImageHook is a request to tag the image in a particular container onto an ImageStreamTag.
         */
        private List<TagImageHookDTO> tagImages;
    }

    /**
     * .spec.strategy.rollingParams.post.execNewPod
     * Description
     * ExecNewPodHook is a hook implementation which runs a command in a new pod based on the specified container
     * which is assumed to be part of the deployment template.
     */
    @Data
    @ToString
    public static class ExecNewPodHookDTO {
        /**
         * Command is the action command and its arguments.
         */
        private List<String> command;

        /**
         * ContainerName is the name of a container in the deployment pod template whose container image will
         * be used for the hook pod’s container.
         */
        private String containerName;

        /**
         * Env is a set of environment variables to supply to the hook pod’s container.
         */
        private List<EnvVarDTO> env;

        /**
         * Volumes is a list of named volumes from the pod template which should be copied to the hook pod.
         * Volumes names not found in pod spec are ignored. An empty list means no volumes will be copied.
         */
        private List<String> volumes;
    }

    /**
     * .spec.strategy.rollingParams.post.tagImages
     * Description
     * TagImages instructs the deployer to tag the current image referenced under a container onto an image stream tag.
     *
     * Type
     * array
     *
     * .spec.strategy.rollingParams.post.tagImages[]
     * Description
     * TagImageHook is a request to tag the image in a particular container onto an ImageStreamTag.
     *
     * Type
     * object
     */
    @Data
    @ToString
    public static class TagImageHookDTO {
        /**
         * ContainerName is the name of a container in the deployment config whose image value will be used as
         * the source of the tag. If there is only a single container this value will be defaulted to the name of
         * that container.
         */
        private String containerName;

        /**
         * To is the target ImageStreamTag to set the container’s image onto.
         */
        private ObjectReferenceDTO to;
    }

    /**
     * .status
     * Description
     * DeploymentConfigStatus represents the current deployment state.
     */
    @Data
    @Builder
    @ToString
    public static class DeploymentConfigStatusDTO {
        /**
         * AvailableReplicas is the total number of available pods targeted by this deployment config.
         */
        private Integer availableReplicas;

        /**
         * Conditions represents the latest available observations of a deployment config’s current state.
         */
        private DeploymentConditionDTO condition;

        /**
         * DeploymentCondition describes the state of a deployment config at a certain point.
         */
        private List<DeploymentConditionDTO> conditions;

        /**
         * DeploymentDetails captures information about the causes of a deployment.
         */
        private DeploymentDetailsDTO details;

        /**
         * LatestVersion is used to determine whether the current deployment associated with a deployment config is out of sync.
         */
        private Integer latestVersion;

        /**
         * ObservedGeneration is the most recent generation observed by the deployment config controller.
         */
        private Integer observedGeneration;

        /**
         * Total number of ready pods targeted by this deployment.
         */
        private Integer readyReplicas;

        /**
         * Replicas is the total number of pods targeted by this deployment config.
         */
        private Integer replicas;

        /**
         * UnavailableReplicas is the total number of unavailable pods targeted by this deployment config.
         */
        private Integer unavailableReplicas;

        /**
         * UnavailableReplicas is the total number of unavailable pods targeted by this deployment config.
         */
        private Integer updatedReplicas;
    }

    /**
     * .status.conditions
     * Description
     * Conditions represents the latest available observations of a deployment config's current state.
     */
    @Data
    @ToString
    public static class DeploymentConditionDTO {
        /**
         * The last time the condition transitioned from one status to another.
         */
        private Date lastTransitionTime;

        /**
         * The last time this condition was updated.
         */
        private Date lastUpdateTime;

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
         * Type of deployment condition.
         */
        private String type;
    }

    /**
     * .status.details
     * Description
     * DeploymentDetails captures information about the causes of a deployment.
     */
    @Data
    @ToString
    public static class DeploymentDetailsDTO {
        /**
         * Causes are extended data associated with all the causes for creating a new deployment
         */
        private DeploymentCauseDTO cause;

        /**
         * DeploymentCause captures information about a particular cause of a deployment.
         */
        private List<DeploymentCauseDTO> causes;

        /**
         * Message is the user specified change message, if this deployment was triggered manually by the user
         */
        private String message;
    }

    /**
     * .status.details.causes
     * Description
     * Causes are extended data associated with all the causes for creating a new deployment
     *
     * Type
     * array
     *
     * .status.details.causes[]
     * Description
     * DeploymentCause captures information about a particular cause of a deployment.
     *
     * Type
     * object
     */
    @Data
    @ToString
    public static class DeploymentCauseDTO {
        /**
         * DeploymentCauseImageTrigger represents details about the cause of a deployment originating from an image change trigger
         */
        private DeploymentCauseImageTriggerDTO imageTrigger;

        /**
         * Type of the trigger that resulted in the creation of a new deployment
         */
        private String type;
    }

    /**
     * .status.details.causes[].imageTrigger
     * Description
     * DeploymentCauseImageTrigger represents details about the cause of a deployment originating from an image change trigger
     */
    @Data
    @ToString
    public static class DeploymentCauseImageTriggerDTO {
        /**
         * From is a reference to the changed object which triggered a deployment. The field may have the kinds
         * DockerImage, ImageStreamTag, or ImageStreamImage.
         */
        private ObjectReferenceDTO from;
    }
}
