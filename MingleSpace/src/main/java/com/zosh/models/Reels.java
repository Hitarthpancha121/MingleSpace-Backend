package com.zosh.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Reels {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;  
	
	private String title;
	
	private String vedio;
	
	@ManyToOne
	private User user;

	
	public Reels() {
		
	}
	
	
	public Reels(Integer id, String title, String vedio, User user) {
		super();
		this.id = id;
		this.title = title;
		this.vedio = vedio;
		this.user = user;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVedio() {
		return vedio;
	}

	public void setVedio(String vedio) {
		this.vedio = vedio;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
