package com.example.imse.sql.Book;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMongoRepository extends MongoRepository<Book, Integer> {
    @Aggregation(pipeline = {
            "{$lookup: {from: 'review', localField: '_id', foreignField: 'book._id', as: 'reviews'}}",
            "{$match: {reviews: {$size: 0}}}",
            "{$project: {_id: 0, publisher: '$publisher.name', title: 1}}"
    })
    List<Object[]> getBooksWithoutReviews();
}
