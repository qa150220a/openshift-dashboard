package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * WindowsSecurityContextOptions [core/v1]
 * Description
 * WindowsSecurityContextOptions contain Windows-specific options and credentials.
 */
public class WindowsSecurityContextOptionsDTO {
    /**
     * GMSACredentialSpec is where the GMSA admission webhook (https://github.com/kubernetes-sigs/windows-gmsa)
     * inlines the contents of the GMSA credential spec named by the GMSACredentialSpecName field.
     */
    private String gmsaCredentialSpec;

    /**
     * GMSACredentialSpecName is the name of the GMSA credential spec to use.
     */
    private String gmsaCredentialSpecName;

    /**
     * The UserName in Windows to run the entrypoint of the container process. Defaults to the user specified
     * in image metadata if unspecified. May also be set in PodSecurityContext. If set in both SecurityContext
     * and PodSecurityContext, the value specified in SecurityContext takes precedence.
     */
    private String runAsUserName;
}
