package wcs.cerebook.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class CerebookPost {
    public static enum Type {
        SimpleMedia,
        ResizedPicture
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm ")
    private Date createdAt;
    private String content;
    private String image;
    private boolean privatePost;
    private CerebookPost.Type mediaType;
    private boolean amazonS3Hosted;
    @ManyToOne(fetch = FetchType.LAZY)
    private CerebookUser cerebookUser;

    public List<CerebookComment> getComments() {
        return comments;
    }

    @OneToMany(mappedBy = "cerebookPost", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("id DESC")
    private final List<CerebookComment> comments = new ArrayList<CerebookComment>();
    //oneTomany one vers  les likes
    @OneToMany(
            mappedBy = "cerebookPost")
    private List<CerebookPostLike> cerebookPostLikes = new ArrayList<>();

    public Type getMediaType() {
        return mediaType;
    }

    public void setMediaType(Type mediaType) {
        this.mediaType = mediaType;
    }

    public boolean isAmazonS3Hosted() {
        return amazonS3Hosted;
    }

    public void setAmazonS3Hosted(boolean amazonS3Hosted) {
        this.amazonS3Hosted = amazonS3Hosted;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CerebookPostLike> getCerebookPostLikes() {
        return cerebookPostLikes;
    }

    public void setCerebookPostLikes(List<CerebookPostLike> cerebookPostLikes) {
        this.cerebookPostLikes = cerebookPostLikes;
    }

    public CerebookUser getCerebookUser() {
        return cerebookUser;
    }

    public void setCerebookUser(CerebookUser cerebookUser) {
        this.cerebookUser = cerebookUser;
    }


    @Temporal(TemporalType.TIME)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPrivatePost() {
        return privatePost;
    }

    public void setPrivatePost(boolean privatePost) {
        this.privatePost = privatePost;
    }

    public CerebookPost() {
    }

    public CerebookPost(Long id, Date createdAt, String content, String image, boolean privatePost, Type mediaType, boolean amazonS3Hosted, CerebookUser cerebookUser, List<CerebookPostLike> cerebookPostLikes) {
        this.id = id;
        this.createdAt = createdAt;
        this.content = content;
        this.image = image;
        this.privatePost = privatePost;
        this.mediaType = mediaType;
        this.amazonS3Hosted = amazonS3Hosted;
        this.cerebookUser = cerebookUser;
        this.cerebookPostLikes = cerebookPostLikes;
    }
}