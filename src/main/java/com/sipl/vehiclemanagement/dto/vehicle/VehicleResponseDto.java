package com.sipl.vehiclemanagement.dto.vehicle;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class VehicleResponseDto implements Serializable {
	private String vehicleRegistrationNumber;

	private String ownerName;

	private String brand;

	private LocalDateTime registrationExpires;
	
	private String isActive;

	public String getVehicleRegistrationNumber() {
		return vehicleRegistrationNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getBrand() {
		return brand;
	}

	public LocalDateTime getRegistrationExpires() {
		return registrationExpires;
	}

	public String getIsActive() {
		return isActive;
	}
	
}
