package io.openshift.model.dto.project;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.openshift.model.dto.ApiObjectDTO;
import io.openshift.model.dto.NamespaceConditionDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * see https://docs.openshift.com/container-platform/4.5/rest_api/project_apis/project-project-openshift-io-v1.html
 * Project [project.openshift.io/v1]
 * Specification
 * API endpoints
 * /apis/project.openshift.io/v1/projects
 * /apis/project.openshift.io/v1/projects/{name}
 * Description
 * Projects are the unit of isolation and collaboration in OpenShift. A project has one or more members, a quota
 * on the resources that the project may consume, and the security controls on the resources in the project.
 * Within a project, members may have different roles - project administrators can set membership, editors can create
 * and manage the resources, and viewers can see but not access running containers. In a normal cluster project
 * administrators are not able to alter their quotas - that is restricted to cluster administrators.
 *
 * Listing or watching projects will return only projects the user has the reader role on.
 *
 * An OpenShift project is an alternative representation of a Kubernetes namespace. Projects are exposed as editable
 * to end users while namespaces are not. Direct creation of a project is typically restricted to administrators,
 * while end users should use the requestproject resource.
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = true)
public class ProjectDTO extends ApiObjectDTO {
    /**
     * ProjectSpec describes the attributes on a Project
     */
    private ProjectSpecDTO spec;

    /**
     * ProjectStatus is information about the current status of a Project
     */
    private ProjectStatusDTO status;

    @Data
    @ToString
    public static class ProjectStatusDTO {
        /**
         * Represents the latest available observations of the project current state.
         */
        private List<NamespaceConditionDTO> conditions;

        /**
         * Phase is the current lifecycle phase of the project
         */
        private Phase phase;

        public enum Phase {
            @SerializedName("Active")
            ACTIVE;
        }
    }

    @Data
    @Builder
    @ToString
    public static class ProjectSpecDTO {
        /**
         * Finalizers is an opaque list of values that must be empty to permanently remove object from storage
         */
        private List<String> finalizers;
    }

}
