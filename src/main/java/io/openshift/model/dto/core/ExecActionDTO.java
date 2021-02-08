package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
/**
 * https://docs.openshift.com/container-platform/4.5/rest_api/objects/index.html#execaction-core-v1
 * ExecAction [core/v1]
 * Description
 * ExecAction describes a "run in container" action.
 */
public class ExecActionDTO {
    /**
     * Command is the command line to execute inside the container, the working directory for the command is root ('/')
     * in the container’s filesystem. The command is simply exec’d, it is not run inside a shell, so traditional shell
     * instructions ('|', etc) won’t work. To use a shell, you need to explicitly call out to that shell.
     * Exit status of 0 is treated as live/healthy and non-zero is unhealthy.
     */
    private List<String> command;
}
