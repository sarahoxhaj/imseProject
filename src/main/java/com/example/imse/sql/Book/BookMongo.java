package com.example.imse.sql.Book;

import com.example.imse.sql.Author.Author;
import com.example.imse.sql.Publisher.Publisher;
import com.example.imse.sql.Review.Review;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Entity
@Document(collection = "book")
public class BookMongo extends Book {
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Field("mongoIsbn")
    private Integer mongoIsbn;
    @Column(nullable = false, unique = true)
    @Field("mongoTitle")
    private String title;
    @Column(nullable = false)
    @Field("mongoDescription")
    private String description;
    @Column(nullable = false)
    @Field("mongoGenre")
    private String genre;

    @OneToMany(mappedBy = "book")
    @Field("mongoReview")
    private List<Review> reviews;


    //    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
//    private List<Author> authors;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @Field("mongoAuthor")
    private List<Author> authors;


    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @Field("mongoPublisher")
    private Publisher publisher;

    public Integer getMongoIsbn() {
        return mongoIsbn;
    }

    public void setMongoIsbn(Integer mongoIsbn) {
        this.mongoIsbn = mongoIsbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN=" + mongoIsbn +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", reviews=" + reviews +
                ", authors=" + authors +
                ", publisher=" + publisher +
                '}';
    }


}
