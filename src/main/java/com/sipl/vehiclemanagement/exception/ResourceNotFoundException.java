package com.sipl.vehiclemanagement.exception;

public class ResourceNotFoundException extends Exception {
    
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("Resource %s not found with  %s: %s",resourceName, fieldName, fieldValue));

	}
	
}
