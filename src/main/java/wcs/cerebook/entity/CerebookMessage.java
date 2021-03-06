package wcs.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CerebookMessage implements Comparable<CerebookMessage> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Date date;
    @ManyToOne(optional = true)
    private CerebookUser currentUser;
    @ManyToMany()
    private final List<CerebookUser> userDestination = new ArrayList<>();

    public CerebookMessage() {
    }

    public CerebookMessage(String message, Date date, CerebookUser currentUser) {
        this.message = message;
        this.date = date;
        this.currentUser = currentUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CerebookUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CerebookUser currentUser) {
        this.currentUser = currentUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<CerebookUser> getUserDestination() {
        return userDestination;
    }


    @Override
    public int compareTo(CerebookMessage cerebookMessage) {
        return this.getDate().compareTo(cerebookMessage.getDate());
    }
}
