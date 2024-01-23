package com.sipl.vehiclemanagement.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class VehicleResponseDto {
	private String vehicleRegistrationNumber;

	private String ownerName;

	private String brand;

	private LocalDateTime registrationExpires;
	
	private String isActive;
	
}
