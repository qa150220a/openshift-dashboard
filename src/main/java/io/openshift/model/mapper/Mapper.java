package io.openshift.model.mapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.Converter;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import io.openshift.model.domain.Container;
import io.openshift.model.domain.DeploymentConfig;
import io.openshift.model.domain.Namespace;
import io.openshift.model.dto.core.ContainerDTO;
import io.openshift.model.dto.deploymentconfig.DeploymentConfigDTO;
import io.openshift.model.dto.project.ProjectDTO;

@Component
public class Mapper extends org.modelmapper.ModelMapper {
	// TODO: move to properties
    private static final String PROJECT_DISPLAY_NAME_ANNOTATION = "openshift.io/display-name";

	public Mapper() {
		super();
		
		typeMap(ProjectDTO.class, Namespace.class)
				.addMappings(mapper -> mapper.map(src -> src.getMetadata().getName(), Namespace::setName));
//				.addMappings(mapper -> mapper. /* when(hasAnnotations). */ map(src -> src.getMetadata().getAnnotations().get(PROJECT_DISPLAY_NAME_ANNOTATION), Namespace::setDisplayName));

		typeMap(ContainerDTO.class, Container.class)
				.addMappings(mapper -> mapper.map(ContainerDTO::getName, Container::setName))
				.addMappings(mapper -> mapper.map(ContainerDTO::getImage, Container::setImageReference));
//				.addMappings(mapper -> mapper.map(src -> imageRefToVersion(src.getImage(),
//						namespace.getName(), src.getName(), images), Container::setImageVersion));

		Converter<Integer, Integer> replaceNullWithZero = ctx -> Optional.ofNullable( ctx.getSource() ).orElse( 0 );

		Type containerListType = new TypeToken<ArrayList<Container>>() {
		}.getType();
		Converter<List<ContainerDTO>, List<Container>> toContainerList = ctx -> ctx.getSource() == null ? null
				: map(ctx.getSource(), containerListType);
		typeMap(DeploymentConfigDTO.class, DeploymentConfig.class)
				.addMappings(mapper -> mapper.map(src -> src.getMetadata().getName(), DeploymentConfig::setName))
				.addMappings(mapper -> mapper.using(replaceNullWithZero).map(src -> src.getStatus().getReplicas(), DeploymentConfig::setReplicas))
				.addMappings(mapper -> mapper.using(replaceNullWithZero).map(src -> src.getStatus().getReadyReplicas(),
						DeploymentConfig::setReadyReplicas))
				.addMappings(mapper -> mapper.using(toContainerList).map(
						src -> src.getSpec().getTemplate().getSpec().getContainers(), DeploymentConfig::setContainers));
		
	}

}
