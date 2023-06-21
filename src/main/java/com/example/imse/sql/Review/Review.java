package com.example.imse.sql.Review;

import com.example.imse.sql.Book.Book;
import com.example.imse.sql.User.User;
import jakarta.persistence.*;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @Column(nullable = false)
    private String comment;
//    @Column(nullable = false)
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Book book;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                ", user=" + user +
                ", book=" + book +
                '}';
    }

    public void setUserId(Integer userId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setId(userId);
    }

    public void setBookISBN(Integer bookISBN){
        if(this.book==null){
            this.book=new Book();
        }
        this.book.setISBN(bookISBN);
    }




}

