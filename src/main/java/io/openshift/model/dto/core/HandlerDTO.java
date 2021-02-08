package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * Handler [core/v1]
 * Description
 * Handler defines a specific action that should be taken
 */
public class HandlerDTO {
    /**
     * One and only one of the following should be specified. Exec specifies the action to take.
     */
    private ExecActionDTO exec;

    /**
     * HTTPGet specifies the http request to perform.
     */
    private HTTPGetActionDTO httpGet;

    /**
     * TCPSocket specifies an action involving a TCP port. TCP hooks not yet supported
     */
    private TCPSocketActionDTO tcpSocket;
}
