package io.openshift.model.dto.imagestream;

import java.util.List;

import io.openshift.model.dto.ApiObjectDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class ImageStreamListDTO extends ApiObjectDTO {
    private List<ImageStreamDTO> items;
}
