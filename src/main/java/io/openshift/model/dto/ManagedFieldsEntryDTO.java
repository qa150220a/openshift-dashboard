package io.openshift.model.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ManagedFieldsEntryDTO {
    /**
     * APIVersion defines the version of this resource that this field set applies to. The format is "group/version"
     * just like the top-level APIVersion field. It is necessary to track the version of a field set because it cannot
     * be automatically converted.
     */
    private String apiVersion;

    /**
     * FieldsType is the discriminator for the different fields format and version.
     * There is currently only one possible value: "FieldsV1"
     */
    private String fieldsType;

    /**
     * FieldsV1 holds the first JSON version format as described in the "FieldsV1" type.
     */
    private String fieldsV1;

    /**
     * Manager is an identifier of the workflow managing these fields.
     */
    private String manager;

    /**
     * Operation is the type of operation which lead to this ManagedFieldsEntry being created.
     * The only valid values for this field are 'Apply' and 'Update'.
     */
    private String operation;

    /**
     * Time is timestamp of when these fields were set. It should always be empty if Operation is 'Apply'
     */
    private Date time;
}
