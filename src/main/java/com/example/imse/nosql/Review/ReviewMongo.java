//package com.example.imse.sql.Review;
package com.example.imse.nosql.Review;

import com.example.imse.nosql.Book.BookMongo;
import com.example.imse.nosql.User.UserMongo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "review")
@EqualsAndHashCode
public class ReviewMongo {
    @Id
    private String id;
    private String comment;
    private Integer rating;
    @DBRef
    private UserMongo user;
    @DBRef
    private BookMongo book;

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                ", user=" + user.getId() +
                ", book=" + book.getTitle() +
                '}';
    }
    
}
