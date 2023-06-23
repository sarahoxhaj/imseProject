package com.example.imse.nosql.Author;

import com.example.imse.nosql.Book.BookMongo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@Document(collection = "author")
@EqualsAndHashCode
public class AuthorMongo {
    @Id
    private String id;

    private String name;

    private String description;

    private Date birthday;
    @DBRef
    private List<BookMongo> books=new ArrayList<>();

}
