package io.openshift.model.dto.api;

import lombok.Data;

import java.util.List;

@Data
public class ApiGroupDTO {
    private String name;
    private List<ApiVersionDTO> versions;
    private ApiVersionDTO preferredVersion;
    private String serverAddressByClientCIDRs;
}
