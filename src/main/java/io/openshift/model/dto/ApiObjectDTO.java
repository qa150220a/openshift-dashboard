package io.openshift.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
@Getter
public abstract class ApiObjectDTO {
    /**
     * APIVersion defines the versioned schema of this representation of an object. Servers should convert recognized
     * schemas to the latest internal value, and may reject unrecognized values.
     * More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#resources
     */
    private String apiVersion;

    /**
     *
     * Kind is a string value representing the REST resource this object represents. Servers may infer this from
     * the endpoint the client submits requests to. Cannot be updated. In CamelCase.
     * More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#types-kinds
     */
    private String kind;

    private ObjectMetadataDTO metadata;
}
