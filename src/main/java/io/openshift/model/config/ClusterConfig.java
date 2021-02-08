package io.openshift.model.config;

import lombok.Data;

@Data
public class ClusterConfig {
	private String name;
	private String url;
}
