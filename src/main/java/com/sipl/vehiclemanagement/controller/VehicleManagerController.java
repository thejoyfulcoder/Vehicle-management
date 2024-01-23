package com.sipl.vehiclemanagement.controller;

import org.springframework.http.ResponseEntity;

import com.sipl.vehiclemanagement.dto.PostVehicle;
import com.sipl.vehiclemanagement.dto.PutVehicle;
import com.sipl.vehiclemanagement.responseObject.ApiResponseBody;

public interface VehicleManagerController {
    
	 ResponseEntity<ApiResponseBody> getAllVehicles();
	
	 ResponseEntity<ApiResponseBody> getVehicleById(String regNo);
	 
	 ResponseEntity<ApiResponseBody> createVehicle(PostVehicle postVehicleObject);
	 
	 ResponseEntity<ApiResponseBody> deleteVehicle(String regNo);

	ResponseEntity<ApiResponseBody> updateVehicle(String regNo, PutVehicle putVehicleObject);
}
