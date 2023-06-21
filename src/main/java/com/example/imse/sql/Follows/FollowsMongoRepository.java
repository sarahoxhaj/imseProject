package com.example.imse.sql.Follows;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowsMongoRepository extends MongoRepository<Follows,Integer> {
}