package com.example.imse.sql.Review;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewMongoRepository extends MongoRepository<Review, Integer> {
    @Aggregation(pipeline = {
            "{$group: {_id: '$book.isbn', count: {$sum: 1}, publisher: {$first: '$book.publisher.name'}}}",
            "{$sort: {count: -1}}",
            "{$limit: 5}",
            "{$project: {_id: 0, isbn: '$_id', publisher: 1}}"
    })
    List<Object[]> findTopBooksByReviewCount();
}
