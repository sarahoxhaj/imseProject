package com.example.imse.sql.Book;

import com.example.imse.sql.Author.Author;
import com.example.imse.sql.Publisher.Publisher;
import com.example.imse.sql.Review.Review;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer isbn;
    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String genre;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews;


//    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
//    private List<Author> authors;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;


    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    public Integer getIsbn() {
        return isbn;
    }

    public void setISBN(Integer ISBN) {
        this.isbn = isbn;
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
                "ISBN=" + isbn +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", reviews=" + reviews +
                ", authors=" + authors +
                ", publisher=" + publisher +
                '}';
    }



}
