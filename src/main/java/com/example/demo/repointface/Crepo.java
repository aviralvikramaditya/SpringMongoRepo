package com.example.demo.repointface;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Comment;

public interface Crepo extends MongoRepository<Comment,String>{
	
	List<Comment> findByPslug(String pslug);

}
