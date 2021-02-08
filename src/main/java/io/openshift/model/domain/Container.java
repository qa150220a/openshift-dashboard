package io.openshift.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Container {
    private String name;
    private String imageVersion;
    private String imageReference;
}
