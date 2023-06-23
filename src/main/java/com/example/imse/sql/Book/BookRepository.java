package com.example.imse.sql.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
//    @Query("SELECT book.publisher.name, book.title " +
//            "FROM Book book " +
//            "LEFT JOIN book.reviews review " +
////            "LEFT JOIN book.authors author " +
//            "GROUP BY book.publisher.name " +
//            "HAVING COUNT(review) = 0")
//    columns: publisher name, book name and author namee

//    @Query("SELECT book.publisher.name, book.title " +
//            "FROM Book book " +
//            "where book.isbn not in (select review.book.isbn from Review review)  " +
//            "group by book.publisher.name")
//    List<Object[]> getBooksWithoutReviews();

    @Query("SELECT book.publisher.name, book.title, GROUP_CONCAT(author.name) " +
            "FROM Book book " +
            "JOIN book.authors author " +
            "WHERE book.isbn NOT IN (SELECT review.book.isbn FROM Review review) " +
            "GROUP BY book.publisher.name")
    List<Object[]> getBooksWithoutReviews();


}