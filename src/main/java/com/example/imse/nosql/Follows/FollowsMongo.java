package com.example.imse.nosql.Follows;


import com.example.imse.nosql.User.UserMongo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "follows")
@EqualsAndHashCode
public class FollowsMongo {
    @Id
    private String id;
    @DBRef
    private UserMongo user;
    @DBRef
    private UserMongo follower;

    @Override
    public String toString() {
        return "Follows{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", follower=" + follower +
                '}';
    }
}