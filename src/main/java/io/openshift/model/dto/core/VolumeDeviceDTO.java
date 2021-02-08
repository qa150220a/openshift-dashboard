package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * https://docs.openshift.com/container-platform/4.5/rest_api/objects/index.html#volumedevice-core-v1
 * VolumeDevice [core/v1]
 * Description
 * volumeDevice describes a mapping of a raw block device within a container.
 */
public class VolumeDeviceDTO {
    /**
     * devicePath is the path inside of the container that the device will be mapped to.
     */
    private String devicePath;

    /**
     * name must match the name of a persistentVolumeClaim in the pod
     */
    private String name;
}
