package com.example.imse.sql.Publisher;

import com.example.imse.sql.Book.Book;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "publisher")
public class PublisherMongo {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

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
