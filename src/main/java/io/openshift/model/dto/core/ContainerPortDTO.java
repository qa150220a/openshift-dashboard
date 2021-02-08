package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * ContainerPort [core/v1]
 * Description
 * ContainerPort represents a network port in a single container.
 */
public class ContainerPortDTO {
    /**
     * Number of port to expose on the pod’s IP address. This must be a valid port number, 0 < x < 65536.
     */
    private Integer containerPort;

    /**
     * What host IP to bind the external port to.
     */
    private String hostIP;

    /**
     * Number of port to expose on the host. If specified, this must be a valid port number, 0 < x < 65536.
     * If HostNetwork is specified, this must match ContainerPort. Most containers do not need this.
     */
    private Integer hostPort;

    /**
     * If specified, this must be an IANA_SVC_NAME and unique within the pod. Each named port in a pod must have
     * a unique name. Name for the port that can be referred to by services.
     */
    private String name;

    /**
     * Protocol for port. Must be UDP, TCP, or SCTP. Defaults to "TCP".
     */
    private String protocol;
}
