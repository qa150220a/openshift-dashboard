package io.openshift.model.dto.imagestream;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.openshift.model.dto.ApiObjectDTO;
import io.openshift.model.dto.ObjectReferenceDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
/**
 * https://docs.openshift.com/container-platform/4.5/rest_api/image_apis/imagestream-image-openshift-io-v1.html
 * ImageStream [image.openshift.io/v1]
 * Specification
 * API endpoints
 * /apis/image.openshift.io/v1/imagestreams
 * /apis/image.openshift.io/v1/namespaces/{namespace}/imagestreams
 * /apis/image.openshift.io/v1/namespaces/{namespace}/imagestreams/{name}
 * /apis/image.openshift.io/v1/namespaces/{namespace}/imagestreams/{name}/layers
 * /apis/image.openshift.io/v1/namespaces/{namespace}/imagestreams/{name}/status
 * /apis/image.openshift.io/v1/namespaces/{namespace}/imagestreams/{name}/secrets
 * Description
 * An ImageStream stores a mapping of tags to images, metadata overrides that are applied when images are tagged
 * in a stream, and an optional reference to a container image repository on a registry. Users typically update
 * the spec.tags field to point to external images which are imported from container registries using credentials
 * in your namespace with the pull secret type, or to existing image stream tags and images which are immediately
 * accessible for tagging or pulling. The history of images applied to a tag is visible in the status.tags field and
 * any user who can view an image stream is allowed to tag that image into their own image streams. Access to pull
 * images from the integrated registry is granted by having the "get imagestreams/layers" permission on a given image
 * stream. Users may remove a tag by deleting the imagestreamtag resource, which causes both spec and status for that
 * tag to be removed. Image stream history is retained until an administrator runs the prune operation, which removes
 * references that are no longer in use. To preserve a historical image, ensure there is a tag in spec pointing to
 * that image by its digest.
 */
public class ImageStreamDTO extends ApiObjectDTO {
    /**
     * ImageStreamSpec represents options for ImageStreams.
     */
    private ImageStreamSpecDTO spec;

    /**
     * ImageStreamStatus contains information about the state of this image stream.
     */
    private ImageStreamStatusDTO status;

    @Data
    @ToString
    public static class ImageStreamSpecDTO {
        /**
         * dockerImageRepository is optional, if specified this stream is backed by a container repository on this
         * server Deprecated: This field is deprecated as of v3.7 and will be removed in a future release.
         * Specify the source for the tags to be imported in each tag via the spec.tags.from reference instead.
         */
        private String dockerImageRepository;

        /**
         *
         * ImageLookupPolicy describes how an image stream can be used to override the image references used by pods,
         * builds, and other resources in a namespace.
         */
        private ImageLookupPolicyDTO lookupPolicy;

        /**
         * tags map arbitrary string values to specific image locators
         */
        private TagDTO tag;

        /**
         *
         * TagReference specifies optional annotations for images using this tag and an optional reference to
         * an ImageStreamTag, ImageStreamImage, or DockerImage this tag should track.
         */
        private List<TagDTO> tags;
    }

    @Data
    @ToString
    public static class ImageLookupPolicyDTO {
        /**
         * local will change the docker short image references (like "mysql" or "php:latest") on objects in this
         * namespace to the image ID whenever they match this image stream, instead of reaching out to a remote registry.
         * The name will be fully qualified to an image ID if found. The tag’s referencePolicy is taken into account
         * on the replaced value. Only works within the current namespace.
         */
        private boolean local;
    }

    @Data
    @ToString
    public static class TagDTO {
        /**
         * Optional; if specified, annotations that are applied to images retrieved via ImageStreamTags.
         */
        private Map<String, String> annotations;

        /**
         * Optional; if specified, a reference to another image that this tag should point to. Valid values are
         * ImageStreamTag, ImageStreamImage, and DockerImage. ImageStreamTag references can only reference a tag within
         * this same ImageStream.
         */
        private ObjectReferenceDTO from;

        /**
         *
         * Generation is a counter that tracks mutations to the spec tag (user intent). When a tag reference is changed
         * the generation is set to match the current stream generation (which is incremented every time spec is changed).
         * Other processes in the system like the image importer observe that the generation of spec tag is newer than
         * the generation recorded in the status and use that as a trigger to import the newest remote tag. To trigger
         * a new import, clients may set this value to zero which will reset the generation to the latest stream
         * generation. Legacy clients will send this value as nil which will be merged with the current tag generation.
         */
        private Integer generation;

        /**
         * TagImportPolicy controls how images related to this tag will be imported.
         */
        private TagImportPolicyDTO importPolicy;

        /**
         * Name of the tag
         */
        // @NotNull
        private String tag;

        /**
         * Reference states if the tag will be imported. Default value is false, which means the tag will be imported.
         */
        private Boolean reference;

        /**
         * TagReferencePolicy describes how pull-specs for images in this image stream tag are generated when image
         * change triggers in deployment configs or builds are resolved. This allows the image stream author to control
         * how images are accessed.
         */
        private TagReferencePolicyDTO referencePolicy;
    }

    @Data
    @ToString
    public static class ImageStreamStatusDTO {
        /**
         * DockerImageRepository represents the effective location this stream may be accessed at.
         * May be empty until the server determines where the repository is located
         */
        private String dockerImageRepository;

        /**
         *
         * PublicDockerImageRepository represents the public location from where the image can be pulled outside
         * the cluster. This field may be empty if the administrator has not exposed the integrated registry externally.
         */
        private String publicDockerImageRepository;

        /**
         * Tags are a historical record of images associated with each tag. The first entry in the TagEvent array
         * is the currently tagged image.
         */
        private NamedTagEventListDTO tag;

        /**
         * NamedTagEventList relates a tag to its image history.
         */
        private List<NamedTagEventListDTO> tags;
    }

    @Data
    @ToString
    public static class NamedTagEventListDTO {
        /**
         * Conditions is an array of conditions that apply to the tag event list.
         */
        private TagEventConditionDTO condition;

        /**
         * TagEventCondition contains condition information for a tag event.
         */
        private  List<TagEventConditionDTO>conditions;

        /**
         * Standard object’s metadata.
         */
        private TagEventDTO item;

        /**
         * TagEvent is used by ImageStreamStatus to keep a historical record of images associated with a tag.
         */
        private List<TagEventDTO>items;

        /**
         *
         * Tag is the tag for which the history is recorded
         */
        private String tag;
    }

    @Data
    @ToString
    public static class TagEventDTO {
        /**
         * Created holds the time the TagEvent was created
         */
        private Date created;

        /**
         * DockerImageReference is the string that can be used to pull this image
         */
        private String dockerImageReference;

        /**
         * Generation is the spec tag generation that resulted in this tag being updated
         */
        private Integer generation;

        /**
         * Image is the image
         */
        private String image;
    }
}
