package com.example.imse.nosql.User;

import com.example.imse.nosql.Follows.FollowsMongo;
import com.example.imse.nosql.Review.ReviewMongo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Document(collection = "user")
@EqualsAndHashCode
public class UserMongo {

    @Id
    private String id;
    private String username;
    private String email;
    private char[] password;
    @DBRef
    private List<ReviewMongo> reviews=new ArrayList<>();
    @DBRef
    private List<FollowsMongo> following=new ArrayList<>();
    @DBRef
    private List<FollowsMongo> followers=new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password=" + Arrays.toString(password) +
                ", reviews=" + reviews +
                ", following=" + following +
                ", followers=" + followers +
                '}';
    }
}