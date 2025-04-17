package com.jounral.jounralapp.repository;

import com.jounral.jounralapp.model.JournalEntry;
import com.jounral.jounralapp.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, ObjectId> {

    User findByUserName(String userName);
}
