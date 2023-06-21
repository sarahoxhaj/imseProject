package com.example.imse.sql.Publisher;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherMongoRepository extends MongoRepository<Publisher,Integer> {
}
