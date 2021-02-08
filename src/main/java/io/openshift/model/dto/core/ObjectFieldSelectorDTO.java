package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * ObjectFieldSelector [core/v1]
 * Description
 * ObjectFieldSelector selects an APIVersioned field of an object.
 */
public class ObjectFieldSelectorDTO {
    /**
     * Version of the schema the FieldPath is written in terms of, defaults to "v1".
     */
    private String apiVersion;

    /**
     * Path of the field to select in the specified API version.
     */
    private String fieldPath;
}
