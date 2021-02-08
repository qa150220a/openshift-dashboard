package io.openshift.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OwnerReferenceDTO {
    /**
     * API version of the referent.
     */
    private String apiVersion;

    /**
     * If true, AND if the owner has the "foregroundDeletion" finalizer, then the owner cannot be deleted from
     * the key-value store until this reference is removed. Defaults to false. To set this field, a user needs
     * "delete" permission of the owner, otherwise 422 (Unprocessable Entity) will be returned.
     */
    private boolean blockOwnerDeletion;

    /**
     * If true, this reference points to the managing controller.
     */
    private boolean controller;

    /**
     * Kind of the referent.
     * More info: https://git.k8s.io/community/contributors/devel/sig-architecture/api-conventions.md#types-kinds
     */
    private String kind;

    /**
     * Name of the referent.
     * More info: http://kubernetes.io/docs/user-guide/identifiers#names
     */
    private String name;

    /**
     * UID of the referent.
     * More info: http://kubernetes.io/docs/user-guide/identifiers#uids
     */
    private String uid;
}
