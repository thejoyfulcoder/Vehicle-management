package com.sipl.vehiclemanagement.service;

import java.util.List;

import com.sipl.vehiclemanagement.dto.PostVehicle;
import com.sipl.vehiclemanagement.dto.PutVehicle;
import com.sipl.vehiclemanagement.dto.VehicleResponseDto;
import com.sipl.vehiclemanagement.exception.ResourceAlreadyExistsException;
import com.sipl.vehiclemanagement.exception.ResourceNotFoundException;

public interface VehicleManagerService {
    
	 List<VehicleResponseDto> getAllVehicles();
	 
	 VehicleResponseDto getVehicleByRegistrationNumber(String regNo) throws ResourceNotFoundException;  //Throws ResourceNotFoundException if vehicle doesn't exist
	 
	 VehicleResponseDto createVehicle(PostVehicle postVehicleObject) throws ResourceAlreadyExistsException; 
	 
	 VehicleResponseDto  updateVehicle(PutVehicle putVehicleObject, String regNo) throws ResourceNotFoundException;  //Throws ResourceNotFoundException if vehicle doesn't exist
	 
	 void deleteVehicle(String regNo) throws ResourceNotFoundException;  //Throws ResourceNotFoundException if vehicle doesn't exist
	 
}
