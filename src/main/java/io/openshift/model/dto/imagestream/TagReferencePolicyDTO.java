package io.openshift.model.dto.imagestream;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TagReferencePolicyDTO {
    /**
     *
     * Type determines how the image pull spec should be transformed when the image stream tag is used in deployment
     * config triggers or new builds. The default value is Source, indicating the original location of the image
     * should be used (if imported). The user may also specify Local, indicating that the pull spec should point to
     * the integrated container image registry and leverage the registry’s ability to proxy the pull to an upstream
     * registry. Local allows the credentials used to pull this image to be managed from the image stream’s namespace,
     * so others on the platform can access a remote image but have no access to the remote secret. It also allows
     * the image layers to be mirrored into the local registry which the images can still be pulled even
     * if the upstream registry is unavailable.
     */
    private String type;
}