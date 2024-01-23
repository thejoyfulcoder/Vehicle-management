package com.sipl.vehiclemanagement.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PutVehicle {
   
	private String vehicleRegistrationNumber;

	private String ownerName;

	private String brand;

	private LocalDateTime registrationExpires;
	
	private String isActive;
	
	private String modifierName;
	
	private LocalDateTime modificationTime;
	

}
