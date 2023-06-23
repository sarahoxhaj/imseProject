package com.example.imse.nosql.Follows;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowsMongoRepository extends MongoRepository<FollowsMongo,String> {
}