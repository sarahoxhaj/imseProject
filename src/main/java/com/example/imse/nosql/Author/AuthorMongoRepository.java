package com.example.imse.nosql.Author;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorMongoRepository extends MongoRepository<AuthorMongo,String> {

}

