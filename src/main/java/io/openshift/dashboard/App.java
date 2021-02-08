package io.openshift.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackageClasses = { Mapper.class, ClusterController.class, ClusterServiceImpl.class, ClusterServiceProperties.class, ClientFactoryImpl.class } )
@ComponentScan(basePackages = "io.openshift")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
