package io.openshift.model.dto.core;

import io.openshift.model.dto.api.util.IntOrStringDTO;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * https://docs.openshift.com/container-platform/4.5/rest_api/objects/index.html#tcpsocketaction-core-v1
 * TCPSocketAction [core/v1]
 * Description
 * TCPSocketAction describes an action based on opening a socket
 */
public class TCPSocketActionDTO {
    /**
     * Optional: Host name to connect to, defaults to the pod IP.
     */
    private String host;

    /**
     * Number or name of the port to access on the container. Number must be in the range 1 to 65535.
     * Name must be an IANA_SVC_NAME.
     */
    private IntOrStringDTO port;
}
