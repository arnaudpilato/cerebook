package wcs.cerebook.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class CerebookCartography {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Double x;
    private Double y;

    @OneToMany(mappedBy = "cartography")
    private List<CerebookUser> users;

    public CerebookCartography() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public List<CerebookUser> getUsers() {
        return users;
    }

    public void setUsers(List<CerebookUser> users) {
        this.users = users;
    }
}
