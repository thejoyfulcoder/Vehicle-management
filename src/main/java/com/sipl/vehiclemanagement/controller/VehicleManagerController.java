package com.sipl.vehiclemanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.sipl.vehiclemanagement.dto.user.UserLogin;
import com.sipl.vehiclemanagement.dto.user.UserSignup;
import com.sipl.vehiclemanagement.dto.vehicle.PostVehicle;
import com.sipl.vehiclemanagement.dto.vehicle.PutVehicle;
import com.sipl.vehiclemanagement.model.User;
import com.sipl.vehiclemanagement.responseObject.ApiResponseBody;

public interface VehicleManagerController {
    
	 ResponseEntity<ApiResponseBody> getAllVehicles();
	
	 ResponseEntity<ApiResponseBody> getVehicleById(String regNo);
	 
	 ResponseEntity<ApiResponseBody> createVehicle(PostVehicle postVehicleObject);
	 
	 ResponseEntity<ApiResponseBody> deleteVehicle(String regNo);

	ResponseEntity<ApiResponseBody> updateVehicle(String regNo, PutVehicle putVehicleObject);
	
	ResponseEntity<ApiResponseBody> signup(UserSignup signupObject, BindingResult bindingResult);
	
	ResponseEntity<ApiResponseBody> login(UserLogin loginObject, BindingResult bindingResult);
	
//	ResponseEntity<ApiResponseBody> login(UserLogin loginObject, BindingResult bindingResult);
}
