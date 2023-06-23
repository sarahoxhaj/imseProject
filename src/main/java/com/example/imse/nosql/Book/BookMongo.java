package com.example.imse.nosql.Book;


import com.example.imse.nosql.Author.AuthorMongo;
import com.example.imse.nosql.Publisher.PublisherMongo;
import com.example.imse.nosql.Review.ReviewMongo;
import jakarta.persistence.GeneratedValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "book")
@EqualsAndHashCode
public class BookMongo {
    @Id
    //@GeneratedValue
    private String isbn;
    private String title;
    private String description;
    private String genre;
    @DBRef
    private List<ReviewMongo> reviews=new ArrayList<>();
    @DBRef
    private List<AuthorMongo> authors=new ArrayList<>();
    @DBRef
    private PublisherMongo publisher;

}
