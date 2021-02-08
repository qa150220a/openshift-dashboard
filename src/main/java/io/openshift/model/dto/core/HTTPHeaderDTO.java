package io.openshift.model.dto.core;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * https://docs.openshift.com/container-platform/4.5/rest_api/objects/index.html#httpheader-core-v1
 * HTTPHeader [core/v1]
 * Description
 * HTTPHeader describes a custom header to be used in HTTP probes
 */
public class HTTPHeaderDTO {
    /**
     * The header field name
     */
    private String name;

    /**
     * The header field value
     */
    private String value;
}
