package com.sipl.vehiclemanagement.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignup {
    
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]{1,19}$", message = "Invalid username: Username must start with an alphabet, not contain whitespace characters or special characters, and be between 2 and 20 characters in length.")
    private String username;
     
    @Email (message="Invalid email")
    @NotBlank( message="Invalid Email: Email cannot be blank")
    private String email;
    
    @NotBlank (message = "Invalid Password: Password cannot be blank")
    @Size(min=8, max=40, message="Invalid Password: Password should be minimum 8  and maximum 40 characters long")
    private String password;

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
