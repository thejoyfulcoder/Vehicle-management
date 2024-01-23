package com.sipl.vehiclemanagement.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table( name="VEHICLE")
public class Vehicle {
	
	@Id 
	@Column( nullable=false, unique=true)
	private String vehicleRegistrationNumber;
	
	@Column (nullable=false)
	private String ownerName;
	
	@Column (nullable =false)
	private String brand;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column
	private LocalDateTime registrationExpires;
	
	@Column
	private String isActive;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Column
	private String createdBy;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Temporal( TemporalType.TIMESTAMP)
	@Column
	private LocalDateTime creationTime;
	
	@Column
	private String modifiedBy;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column
	private LocalDateTime modificationTime;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}


	
}
