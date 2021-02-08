package io.openshift.model.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class NamespaceConditionDTO {
    private Date lastTransitionTime;

    private String message;

    private String reason;

    /**
     * Status of the condition, one of True, False, Unknown.
     */
    private String status;

    /**
     * Type of namespace controller condition.
     */
    private String type;
}
