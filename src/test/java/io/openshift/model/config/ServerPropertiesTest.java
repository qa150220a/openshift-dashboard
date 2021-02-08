package io.openshift.model.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.openshift.dashboard.App;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ServerProperties.class, ClusterServiceProperties.class, App.class })
@SpringBootTest
public class ServerPropertiesTest {
 
    @Autowired
    private ServerProperties serverProperties;
 
    @Test
    public void whenYamlFileProvidedThenInjectSimpleMap() {
        assertThat(serverProperties.getApplication())
          .containsOnlyKeys("name", "url", "description");
 
        assertThat(serverProperties.getApplication()
          .get("name")).isEqualTo("InjectMapFromYAML");
    }
 
	@Autowired
	private ClusterServiceProperties serviceProperties;

	@Test
	public void whenYamlFileProvidedThenInjectComplexMap() {
		assertThat(serviceProperties.getGlobalRegistry()).isEqualTo("https://registry.localdomain");
		
		assertThat(serviceProperties.getClusters()).hasSize(2);

		assertThat(serviceProperties.getClusters().get(0).getName()).isEqualTo("cluster alpha");
		assertThat(serviceProperties.getClusters().get(0).getUrl()).isEqualTo("https://console-clusterl.localdomain");

		assertThat(serviceProperties.getClusters().get(1).getName()).isEqualTo("cluster bravo");
		assertThat(serviceProperties.getClusters().get(1).getUrl()).isEqualTo("https://console-cluster2.localdomain");
	}
}
