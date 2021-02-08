package io.openshift.model.dto.core;

import io.openshift.model.dto.ObjectMetadataDTO;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class PodTemplateSpecDTO {
    /**
     * Standard object’s metadata.
     * More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#metadata
     */
    private ObjectMetadataDTO metadata;

    /**
     * PodSpec is a description of a pod.
     */
    private PodSpecDTO spec;
}