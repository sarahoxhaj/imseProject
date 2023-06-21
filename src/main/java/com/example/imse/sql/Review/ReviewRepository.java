package com.example.imse.sql.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT review.book.isbn, publisher.name " +
            "FROM Review review " +
            "INNER JOIN review.book book " +
            "INNER JOIN book.publisher publisher " +
            "GROUP BY review.book.isbn " +
            "ORDER BY COUNT(*) DESC " +
            "LIMIT 5")
    List<Object[]> findTopBooksByReviewCount();
}
