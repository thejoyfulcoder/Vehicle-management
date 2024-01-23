package com.sipl.vehiclemanagement.exception;

public class ResourceAlreadyExistsException extends Exception {
      
	public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
			super(String.format("Resource %s already exists with %s: %s",resourceName, fieldName, fieldValue));

		}
	}
