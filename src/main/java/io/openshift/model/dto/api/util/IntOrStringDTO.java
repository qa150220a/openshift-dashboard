package io.openshift.model.dto.api.util;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
/**
 * https://docs.openshift.com/container-platform/4.5/rest_api/objects/index.html#intorstring-util-intstr
 * IntOrString [util/intstr]
 * Description
 * IntOrString is a type that can hold an int32 or a string. When used in JSON or YAML marshalling and unmarshalling,
 * it produces or consumes the inner type. This allows you to have, for example, a JSON field that can accept a name or number.
 */
public class IntOrStringDTO {
    private String value;
}
