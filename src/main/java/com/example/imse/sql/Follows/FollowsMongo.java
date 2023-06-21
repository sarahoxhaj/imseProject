package com.example.imse.sql.Follows;

import com.example.imse.sql.User.User;
import jakarta.persistence.*;

@Entity
@Table(name = "follows")
public class FollowsMongo {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "follower_id", referencedColumnName = "id")
    private User follower;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    @Override
    public String toString() {
        return "Follows{" +
                "id=" + id +
                ", user=" + user +
                ", follower=" + follower +
                '}';
    }
}
