package io.openshift.model.dto.imagestream;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TagEventConditionDTO {
    /**
     * Generation is the spec tag generation that this status corresponds to
     */
    private Integer generation;

    /**
     * lastTransitionTime is the time the condition transitioned from one status to another.
     */
    private Date lastTransitionTime;

    /**
     * Message is a human readable description of the details about last transition, complementing reason.
     */
    private String message;

    /**
     * Reason is a brief machine readable explanation for the condition’s last transition.
     */
    private String reason;

    /**
     * Status of the condition, one of True, False, Unknown.
     */
    private String status;

    /**
     * Type of tag event condition, currently only ImportSuccess
     */
    private String type;
}