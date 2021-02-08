package io.openshift.model.dto.imagestream;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.openshift.model.dto.ApiObjectDTO;
import io.openshift.model.dto.ObjectReferenceDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * ImageStreamTag [image.openshift.io/v1]
 * Specification
 * API endpoints
 * /apis/image.openshift.io/v1/imagestreamtags
 * /apis/image.openshift.io/v1/namespaces/{namespace}/imagestreamtags
 * /apis/image.openshift.io/v1/namespaces/{namespace}/imagestreamtags/{name}
 * Description
 * ImageStreamTag represents an Image that is retrieved by tag name from an ImageStream. Use this resource to interact
 * with the tags and images in an image stream by tag, or to see the image details for a particular tag. The image
 * associated with this resource is the most recently successfully tagged, imported, or pushed image (as described
 * in the image stream status.tags.items list for this tag). If an import is in progress or has failed the previous
 * image will be shown. Deleting an image stream tag clears both the status and spec fields of an image stream.
 * If no image can be retrieved for a given tag, a not found error will be returned.
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class ImageStreamTagDTO extends ApiObjectDTO {
    /**
     * conditions is an array of conditions that apply to the image stream tag.
     */
    private TagEventConditionDTO condition;

    /**
     * TagEventCondition contains condition information for a tag event.
     */
    private List<TagEventConditionDTO> conditions;

    /**
     * generation is the current generation of the tagged image - if tag is provided and this value is not equal
     * to the tag generation, a user has requested an import that has not completed, or conditions will be filled
     * out indicating any error.
     */
    private Integer generation;

    /**
     * Image is an immutable representation of a container image and metadata at a point in time. Images are named
     * by taking a hash of their contents (metadata and content) and any change in format, content, or metadata
     * results in a new name. The images resource is primarily for use by cluster administrators and integrations
     * like the cluster image registry - end users instead access images via the imagestreamtags or imagestreamimages
     * resources. While image metadata is stored in the API, any integration that implements the container image
     * registry API must provide its own storage for the raw manifest data, image config, and layer contents.
     */
    private ImageDTO image;

    /**
     * ImageLookupPolicy describes how an image stream can be used to override the image references used by pods,
     * builds, and other resources in a namespace.
     */
    private ImageLookupPolicyDTO lookupPolicy;

    /**
     * TagReference specifies optional annotations for images using this tag and an optional reference
     * to an ImageStreamTag, ImageStreamImage, or DockerImage this tag should track.
     */
    private TagReferenceDTO tag;

    /**
     * .image
     * Description
     * Image is an immutable representation of a container image and metadata at a point in time.
     * Images are named by taking a hash of their contents (metadata and content) and any change in format, content,
     * or metadata results in a new name. The images resource is primarily for use by cluster administrators and
     * integrations like the cluster image registry - end users instead access images via the imagestreamtags or
     * imagestreamimages resources. While image metadata is stored in the API, any integration that implements
     * the container image registry API must provide its own storage for the raw manifest data, image config,
     * and layer contents.
     */
    @Data
    @ToString
    @EqualsAndHashCode(callSuper = true)
    public static class ImageDTO extends ApiObjectDTO {
        /**
         * DockerImageConfig is a JSON blob that the runtime uses to set up the container. This is a part of manifest schema v2.
         */
        private String dockerImageConfig;

        /**
         * DockerImageLayers represents the layers in the image. May not be set if the image does not define that data.
         */
        private DockerImageLayersDTO dockerImageLayer;

        /**
         * ImageLayer represents a single layer of the image. Some images may have multiple layers. Some may have none.
         */
        private List<DockerImageLayersDTO> dockerImageLayers;

        /**
         * DockerImageManifest is the raw JSON of the manifest
         */
        private String dockerImageManifest;

        /**
         * DockerImageManifestMediaType specifies the mediaType of manifest. This is a part of manifest schema v2.
         */
        private String dockerImageManifestMediaType;

        /**
         * DockerImageMetadata contains metadata about this image
         */
        private DockerImageMetadataDTO /*RawExtensionDTO*/ dockerImageMetadata;

        /**
         * DockerImageMetadataVersion conveys the version of the object, which if empty defaults to "1.0"
         */
        private String dockerImageMetadataVersion;

        /**
         * DockerImageReference is the string that can be used to pull this image.
         */
        private String dockerImageReference;

        /**
         * DockerImageSignatures provides the signatures as opaque blobs. This is a part of manifest schema v1.
         */
        private List<String> dockerImageSignatures;

        /**
         * Signatures holds all signatures of the image.
         */
        private ImageSignatureDTO signature;

        /**
         * ImageSignature holds a signature of an image. It allows to verify image identity and possibly other claims
         * as long as the signature is trusted. Based on this information it is possible to restrict runnable images
         * to those matching cluster-wide policy. Mandatory fields should be parsed by clients doing image verification.
         * The others are parsed from signature’s content by the server. They serve just an informative purpose.
         */
        private List<ImageSignatureDTO> signatures;

    }

    /**
     * .image.dockerImageLayers
     * Description
     * DockerImageLayers represents the layers in the image. May not be set if the image does not define that data.
     */
    @Data
    @ToString
    public static class DockerImageLayersDTO {
        /**
         * MediaType of the referenced object.
         */
        private String mediaType;

        /**
         * Name of the layer as defined by the underlying store.
         */
        private String name;

        /**
         * Size of the layer in bytes as defined by the underlying store.
         */
        private Integer size;
    }

    /**
     * .image.signatures
     * Description
     * Signatures holds all signatures of the image.
     *
     * Type
     * array
     *
     * .image.signatures[]
     * Description
     * ImageSignature holds a signature of an image. It allows to verify image identity and possibly other claims as long as the signature is trusted. Based on this information it is possible to restrict runnable images to those matching cluster-wide policy. Mandatory fields should be parsed by clients doing image verification. The others are parsed from signature's content by the server. They serve just an informative purpose.
     *
     * Type
     * object
     */
    @Data
    @ToString
    @EqualsAndHashCode(callSuper = true)
    public static class ImageSignatureDTO extends ApiObjectDTO {
        // TODO:
    }

    /**
     * https://docs.openshift.com/container-platform/4.5/rest_api/objects/index.html#rawextension-pkg-runtime
     * RawExtension [pkg/runtime]
     * Description
     * RawExtension is used to hold extensions in external versions.
     *
     * To use this, make a field which has RawExtension as its type in your external, versioned struct, and Object
     * in your internal struct. You also need to register your various plugin types.
     */
    @Data
    @ToString
    public static class RawExtensionDTO {

    }

    /**
     * .tag
     * Description
     * TagReference specifies optional annotations for images using this tag and an optional reference to
     * an ImageStreamTag, ImageStreamImage, or DockerImage this tag should track.
     */
    @Data
    @ToString
    public static class TagReferenceDTO {
        /**
         * Optional; if specified, annotations that are applied to images retrieved via ImageStreamTags.
         */
        private Map<String, String> annotations;

        /**
         * Optional; if specified, a reference to another image that this tag should point to.
         * Valid values are ImageStreamTag, ImageStreamImage, and DockerImage. ImageStreamTag references can only
         * reference a tag within this same ImageStream.
         */
        private ObjectReferenceDTO from;

        /**
         * Generation is a counter that tracks mutations to the spec tag (user intent). When a tag reference
         * is changed the generation is set to match the current stream generation (which is incremented every time
         * spec is changed). Other processes in the system like the image importer observe that the generation of spec
         * tag is newer than the generation recorded in the status and use that as a trigger to import the newest
         * remote tag. To trigger a new import, clients may set this value to zero which will reset the generation
         * to the latest stream generation. Legacy clients will send this value as nil which will be merged
         * with the current tag generation.
         */
        private Integer generation;

        /**
         * TagImportPolicy controls how images related to this tag will be imported.
         */
        private TagImportPolicyDTO importPolicy;

        /**
         * Name of the tag
         */
        private String name;

        /**
         * Reference states if the tag will be imported. Default value is false, which means the tag will be imported.
         */
        private Boolean reference;

        /**
         * TagReferencePolicy describes how pull-specs for images in this image stream tag are generated when image
         * change triggers in deployment configs or builds are resolved. This allows the image stream author
         * to control how images are accessed.
         */
        private TagReferencePolicyDTO referencePolicy;
    }


    /**
     * .lookupPolicy
     * Description
     * ImageLookupPolicy describes how an image stream can be used to override the image references used by pods,
     * builds, and other resources in a namespace.
     */
    @Data
    @ToString
    public static class ImageLookupPolicyDTO {
        /**
         * local will change the docker short image references (like "mysql" or "php:latest") on objects in
         * this namespace to the image ID whenever they match this image stream, instead of reaching out to a remote
         * registry. The name will be fully qualified to an image ID if found. The tag’s referencePolicy is taken
         * into account on the replaced value. Only works within the current namespace.
         */
        private Boolean local;
    }

    /* ------------------------- */
    @Data
    @ToString
    @EqualsAndHashCode(callSuper = true)
    public static class DockerImageMetadataDTO extends ApiObjectDTO {
        private String Id;
        private Date Created;
        private String Container;
        private ContainerConfigDTO ContainerConfig;

        private String DockerVersion;
        private String Author;
        private ContainerConfigDTO Config;

        private String Architecture;
        private Integer Size;
    }

    @Data
    @ToString
    public static class ContainerConfigDTO {
        private String Hostname;
        private String User;
//        private Map<String, String> ExposedPorts;
        private List<String> Env;
        private List<String> Cmd;
        private String Image;
        private String WorkingDir;
        private List<String> Entrypoint;
        private Map<String, String> Labels;
    }
}
