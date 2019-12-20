package com.example.demo.repointface;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.model.User;

public interface Urepo extends MongoRepository<User, String> {
	
	@Query("{'userid':?0,'password':?1}")
	User Authenticate(String userid, String password);

}
