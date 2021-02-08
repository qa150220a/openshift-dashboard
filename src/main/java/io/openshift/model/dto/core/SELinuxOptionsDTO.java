package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * SELinuxOptions [core/v1]
 * Description
 * SELinuxOptions are the labels to be applied to the container
 */
public class SELinuxOptionsDTO {
    /**
     * Level is SELinux level label that applies to the container.
     */
    private String level;

    /**
     * Role is a SELinux role label that applies to the container.
     */
    private String role;

    /**
     * Type is a SELinux type label that applies to the container.
     */
    private String type;

    /**
     * User is a SELinux user label that applies to the container.
     */
    private String user;
}
