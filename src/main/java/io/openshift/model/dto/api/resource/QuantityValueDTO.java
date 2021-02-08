package io.openshift.model.dto.api.resource;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class QuantityValueDTO {
    private String value;
}
