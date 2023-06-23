package com.example.imse.nosql.Publisher;

import com.example.imse.nosql.Book.BookMongo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Data
@Document(collection = "publisher")
@EqualsAndHashCode
public class PublisherMongo {
    @Id
    private String id;
    private String email;
    private String name;
    @DBRef
    private List<BookMongo> books=new ArrayList<>();
    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}';
    }

}
