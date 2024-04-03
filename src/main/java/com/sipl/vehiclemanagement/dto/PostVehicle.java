package com.sipl.vehiclemanagement.dto;

import java.net.http.HttpClient;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class PostVehicle {
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private String vehicleRegistrationNumber;

	private String ownerName;
    
	private String brand;

	private LocalDateTime registrationExpires;
	
	private String isActive;
	
	private String creatorName;

	private LocalDateTime creationTime;

	public String getVehicleRegistrationNumber() {
		return vehicleRegistrationNumber;
	}

	public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
		this.vehicleRegistrationNumber = vehicleRegistrationNumber;
	}

}
