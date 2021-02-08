package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
/**
 * Capabilities [core/v1]
 * Description
 * Adds and removes POSIX capabilities from running containers.
 */
public class CapabilitiesDTO {
    /**
     * Added capabilities
     */
    private List<String> add;

    /**
     * Removed capabilities
     */
    private List<String> drop;
}
