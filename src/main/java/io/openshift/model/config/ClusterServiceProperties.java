package io.openshift.model.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.Setter;
import lombok.Synchronized;


@Configuration
@ConfigurationProperties(prefix = "config")
@Setter(onMethod_= {@Synchronized})
@Data
public class ClusterServiceProperties {
	private final Object $lock = new Object[0];
	
	private String globalRegistry;
	private List<ClusterConfig> clusters;
}
