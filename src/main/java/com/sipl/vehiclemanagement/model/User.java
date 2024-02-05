package com.sipl.vehiclemanagement.model;

import org.hibernate.envers.Audited;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Audited
public class User {
	
	public User() {
		super();
	}

	public User(Long userId, String username, String email, String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long userId;

    private String username;

    private String email;
    
    private String password;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    

    
}
