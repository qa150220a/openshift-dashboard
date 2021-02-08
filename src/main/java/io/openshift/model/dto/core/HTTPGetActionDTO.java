package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

import java.util.List;

import io.openshift.model.dto.api.util.IntOrStringDTO;

@Data
@ToString
/**
 * https://docs.openshift.com/container-platform/4.5/rest_api/objects/index.html#httpgetaction-core-v1
 * HTTPGetAction [core/v1]
 * Description
 * HTTPGetAction describes an action based on HTTP Get requests.
 */
public class HTTPGetActionDTO {
    /**
     * Host name to connect to, defaults to the pod IP. You probably want to set "Host" in httpHeaders instead.
     */
    private String host;

    /**
     * Custom headers to set in the request. HTTP allows repeated headers.
     */
    private List<HTTPHeaderDTO> httpHeaders;

    /**
     * Path to access on the HTTP server.
     */
    private String path;

    /**
     * Name or number of the port to access on the container. Number must be in the range 1 to 65535.
     * Name must be an IANA_SVC_NAME.
     */
    private IntOrStringDTO port;

    /**
     * Scheme to use for connecting to the host. Defaults to HTTP.
     */
    private String scheme;
}
