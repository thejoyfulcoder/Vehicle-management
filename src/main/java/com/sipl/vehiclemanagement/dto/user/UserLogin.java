package com.sipl.vehiclemanagement.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLogin {
    
	@NotBlank (message = "username cannot be null")
	  @Size(min=2, max=30)
    private String username;

	@NotBlank (message = "password cannot be black")
	  @Size(min=8, max=40)
    private String password;
	
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
