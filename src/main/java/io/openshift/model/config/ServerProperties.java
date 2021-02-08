package io.openshift.model.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Setter;
import lombok.Synchronized;

@Component
@ConfigurationProperties(prefix = "server")
@Setter(onMethod_= {@Synchronized})
@Data
public class ServerProperties {
	private final Object $lock = new Object[0];
	
	private Map<String, String> application;
}