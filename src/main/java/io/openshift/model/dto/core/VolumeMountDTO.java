package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * https://docs.openshift.com/container-platform/4.5/rest_api/objects/index.html#volumemount-core-v1
 * VolumeMount [core/v1]
 * Description
 * VolumeMount describes a mounting of a Volume within a container.
 */
public class VolumeMountDTO {
    /**
     * Path within the container at which the volume should be mounted. Must not contain ':'.
     */
    private String mountPath;

    /**
     * mountPropagation determines how mounts are propagated from the host to container and the other way around.
     * When not set, MountPropagationNone is used. This field is beta in 1.10.
     */
    private String mountPropagation;

    /**
     * This must match the Name of a Volume.
     */
    private String name;

    /**
     * Mounted read-only if true, read-write otherwise (false or unspecified). Defaults to false.
     */
    private Boolean readOnly;

    /**
     * Path within the volume from which the container’s volume should be mounted. Defaults to "" (volume’s root).
     */
    private String subPath;

    /**
     * Expanded path within the volume from which the container’s volume should be mounted. Behaves similarly
     * to SubPath but environment variable references $(VAR_NAME) are expanded using the container’s environment.
     * Defaults to "" (volume’s root). SubPathExpr and SubPath are mutually exclusive.
     */
    private String subPathExpr;
}
