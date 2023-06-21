package com.example.imse.sql.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMongoRepository extends MongoRepository<User, String> {
}

