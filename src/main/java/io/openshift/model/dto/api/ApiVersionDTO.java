package io.openshift.model.dto.api;

import lombok.Data;

@Data
public class ApiVersionDTO {
    private String groupVersion;
    private String version;
}
