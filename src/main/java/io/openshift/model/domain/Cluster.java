package io.openshift.model.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Cluster {
    private String name;
    private String url;
    private List<Namespace> namespaces;
}
