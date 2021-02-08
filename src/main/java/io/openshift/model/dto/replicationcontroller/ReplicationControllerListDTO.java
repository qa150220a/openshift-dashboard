package io.openshift.model.dto.replicationcontroller;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

import io.openshift.model.dto.ApiObjectDTO;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class ReplicationControllerListDTO extends ApiObjectDTO {
    private List<ReplicationControllerDTO> items;
}
