package com.sipl.vehiclemanagement.responseObject;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ApiResponseBody {
     private String message;  
     private int status;
    
	private  Object data;
     
	public ApiResponseBody(String message, HttpStatus httpStatus, Object data) {
		super();
		this.message = message;
		this.status = httpStatus.value();
		this.data = data;
	}
	
	
    public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

	public Object getData() {
		return data;
	}

     
}
