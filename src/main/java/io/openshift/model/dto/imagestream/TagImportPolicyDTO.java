package io.openshift.model.dto.imagestream;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TagImportPolicyDTO {
    /**
     * Insecure is true if the server may bypass certificate verification or connect directly over HTTP during image import.
     */
    private Boolean insecure;

    /**
     * Scheduled indicates to the server that this tag should be periodically checked to ensure it is up to date, and imported
     */
    private Boolean scheduled;
}