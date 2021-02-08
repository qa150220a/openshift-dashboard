package io.openshift.model.dto.project;

import java.util.List;

import io.openshift.model.dto.ApiObjectDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class ProjectListDTO extends ApiObjectDTO {
    private List<ProjectDTO> items;
}
