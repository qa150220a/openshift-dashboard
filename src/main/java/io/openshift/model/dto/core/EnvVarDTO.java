package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * EnvVar [core/v1]
 * Description
 * EnvVar represents an environment variable present in a Container.
 */
public class EnvVarDTO {
    /**
     * Name of the environment variable. Must be a C_IDENTIFIER.
     */
    private String name;

    /**
     * Variable references $(VAR_NAME) are expanded using the previous defined environment variables in the container
     * and any service environment variables. If a variable cannot be resolved, the reference in the input string
     * will be unchanged. The $(VAR_NAME) syntax can be escaped with a double , ie: (VAR_NAME). Escaped references
     * will never be expanded, regardless of whether the variable exists or not. Defaults to "".
     */
    private String value;

    /**
     * Source for the environment variable’s value. Cannot be used if value is not empty.
     */
    private EnvVarSourceDTO valueFrom;
}
