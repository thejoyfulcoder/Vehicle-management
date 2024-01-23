package com.sipl.vehiclemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sipl.vehiclemanagement.model.Vehicle;

import jakarta.transaction.Transactional;

public interface VehicleManagerRepository extends JpaRepository<Vehicle, Long>{
     
	 Optional<Vehicle> findByVehicleRegistrationNumber(String vehicleRegistrationNumber);

     @Transactional
	 boolean existsByVehicleRegistrationNumber(String vehicleRegistrationNumber);
	 
	 @Transactional
	 void deleteByVehicleRegistrationNumber(String vehicleRegistrationNumber);
}
