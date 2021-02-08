package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AffinityDTO {
    /**
     * Describes node affinity scheduling rules for the pod.
     */
    private NodeAffinityDTO nodeAffinity;

    /**
     * Describes pod affinity scheduling rules (e.g. co-locate this pod in the same node, zone, etc. as some other pod(s)).
     */
    private PodAffinityDTO podAffinity;

    /**
     * Describes pod anti-affinity scheduling rules (e.g. avoid putting this pod in the same node, zone, etc. as some other pod(s)).
     */
    private PodAntiAffinityDTO podAntiAffinity;

    @Data
    @ToString
    private static class NodeAffinityDTO {
        // TODO
    }

    @Data
    @ToString
    private static class PodAffinityDTO {
        // TODO
    }

    @Data
    @ToString
    private static class PodAntiAffinityDTO {
        // TODO
    }

}
