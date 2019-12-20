package com.example.demo.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.repointface.Crepo;
import com.example.demo.repointface.Urepo;

@RestController
public class Controller {
	
	//------------------------usr collection----------------------
	@Autowired
	Urepo urepo;
	
	@GetMapping("users")
	public List<User> getall(){
		return urepo.findAll();
	}
	
	@PostMapping("login")
	public String login(@RequestBody User u, HttpSession session) {
		User temp = urepo.Authenticate(u.getUserid(),u.getPassword()); 
		if(temp!=null) {
			session.setAttribute("UN", temp);
			return "welcome back "+temp.getUname();
		}
		else return "bad credentials";
	}
	
	@PostMapping("signup")
	public String signup(@RequestBody User u) {
		urepo.insert(u);
		return "congratulations "+u.getUname()+", your account has been created";
	}
	
	
	@PostMapping("logout")
	public String logout(HttpSession session){
		session.removeAttribute("UN");
		return "logout successful";
	}
	
	@DeleteMapping("del_users")
	public String deleteall() {
		urepo.deleteAll();
		return "all user profile deleted";
	}
	
	@PutMapping("forgotPassword")
	public String resetpassword(@RequestBody User u) {
		User t = urepo.findById(u.getUserid()).orElse(null);
		if(t==null) return "account doesnot exist";	
		t.setPassword(u.getPassword());
		urepo.save(t);
		return "password reset!";
	}
	
	//------------------------------cmts collection-----------------------
	
	@Autowired
	Crepo crepo;
	
	@GetMapping("comments")
	public List<Comment> getcomments(){
		return crepo.findAll();
	}
	
	@GetMapping("comments/{pslug}")
	public List<Comment> getReplies(@PathVariable String pslug){
		return crepo.findByPslug(pslug);
	}
	
	@PostMapping("posting")
	public Comment postComment(@RequestBody Comment c, HttpSession session){
		User u = (User) session.getAttribute("UN");
		if(u!=null) {
			c.setUsr(u);
			crepo.insert(c);
			return c;
		}
		else {
			Comment temp = new Comment();
			temp.setText("Please login first");
			return temp;
		}
	}
	
	@DeleteMapping("del_comts")
	public String delallcomts() {
		crepo.deleteAll();
		return "all comments deleted";
	}

}
