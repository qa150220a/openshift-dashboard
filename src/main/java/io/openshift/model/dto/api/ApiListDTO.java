package io.openshift.model.dto.api;

import java.util.List;

import io.openshift.model.dto.ApiObjectDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class ApiListDTO extends ApiObjectDTO {
    private List<ApiGroupDTO> groups;
}
