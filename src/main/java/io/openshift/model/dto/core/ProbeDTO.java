package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * https://docs.openshift.com/container-platform/4.5/rest_api/objects/index.html#probe-core-v1
 * Probe [core/v1]
 * Description
 * Probe describes a health check to be performed against a container to determine whether it is alive or ready to receive traffic.
 */
public class ProbeDTO {
    /**
     * One and only one of the following should be specified. Exec specifies the action to take.
     */
    private ExecActionDTO exec;

    /**
     * Minimum consecutive failures for the probe to be considered failed after having succeeded. Defaults to 3. Minimum value is 1.
     */
    private Integer failureThreshold;

    /**
     * HTTPGet specifies the http request to perform.
     */
    private HTTPGetActionDTO httpGet;

    /**
     * Number of seconds after the container has started before liveness probes are initiated.
     * More info: https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle#container-probes
     */
    private Integer initialDelaySeconds;

    /**
     * How often (in seconds) to perform the probe. Default to 10 seconds. Minimum value is 1.
     */
    private Integer periodSeconds;

    /**
     * Minimum consecutive successes for the probe to be considered successful after having failed.
     * Defaults to 1. Must be 1 for liveness and startup. Minimum value is 1.
     */
    private Integer successThreshold;

    /**
     * TCPSocket specifies an action involving a TCP port. TCP hooks not yet supported
     */
    private TCPSocketActionDTO tcpSocket;

    /**
     * Number of seconds after which the probe times out. Defaults to 1 second.
     * Minimum value is 1.
     * More info: https://kubernetes.io/docs/concepts/workloads/pods/pod-lifecycle#container-probes
     */
    private Integer timeoutSeconds;
}
