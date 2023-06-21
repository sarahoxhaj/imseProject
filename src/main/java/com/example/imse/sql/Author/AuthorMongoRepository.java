package com.example.imse.sql.Author;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorMongoRepository extends MongoRepository<Author,Integer> {
}

