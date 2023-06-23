package com.example.imse.nosql.Book;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMongoRepository extends MongoRepository<BookMongo, String> {
    @Query("{ 'reviews': [] }")
    List<BookMongo> getBooksWithoutReviews();

}
