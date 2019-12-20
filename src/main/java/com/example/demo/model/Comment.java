package com.example.demo.model;

import java.util.Random;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="cmts")
public class Comment {
	
	@Id
	private String slug;
	private String pslug="None"; // parent slug
	private String text;
	@DBRef
	private User usr;
	
	public User getUsr() {
		return usr;
	}

	public void setUsr(User usr) {
		this.usr = usr;
	}

	public String getSlug() {
		return slug;
	}

	public String getPslug() {
		return pslug;
	}

	public void setPslug(String pslug) {
		this.pslug = pslug;
		slug = pslug+"_";
		Random rand = new Random();
		for(int i=0;i<4;i++) {
			slug+=""+((char) (65+rand.nextInt(26)));
		}
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	

}
