package com.sipl.vehiclemanagement.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sipl.vehiclemanagement.controller.VehicleManagerController;
import com.sipl.vehiclemanagement.dto.PostVehicle;
import com.sipl.vehiclemanagement.dto.PutVehicle;
import com.sipl.vehiclemanagement.dto.VehicleResponseDto;
import com.sipl.vehiclemanagement.exception.ResourceNotFoundException;
import com.sipl.vehiclemanagement.mapper.VehicleMapper;
import com.sipl.vehiclemanagement.responseObject.ApiResponseBody;
import com.sipl.vehiclemanagement.service.VehicleManagerService;

@Controller
@RequestMapping("/api/vehicles/")
public class VehicleManagerControllerImpl implements VehicleManagerController {
	
	VehicleManagerService vehiclemanagerservice;
	@Autowired
	VehicleMapper vehicleMapper;
	
	public VehicleManagerControllerImpl(VehicleManagerService vehiclemanagerservice) {
		this.vehiclemanagerservice = vehiclemanagerservice;
	}


	@Override
	@GetMapping 
	public ResponseEntity<ApiResponseBody> getAllVehicles() {
        try {
            return new ResponseEntity<ApiResponseBody>(new ApiResponseBody("Fetch Successfull", HttpStatus.OK,vehiclemanagerservice.getAllVehicles()), HttpStatus.OK);
         }catch(Exception e) {
         	 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,null),HttpStatus.INTERNAL_SERVER_ERROR);
         }
 	
	}


	@Override
	@GetMapping("/{registrationNumber}")
	public ResponseEntity<ApiResponseBody> getVehicleById(@PathVariable (value="registrationNumber") String regNo) {

	     try {
	           return new ResponseEntity<ApiResponseBody>(new ApiResponseBody("Fetch Successfull", HttpStatus.OK,vehiclemanagerservice.getVehicleByRegistrationNumber(regNo)), HttpStatus.OK);
	         }
	     catch(ResourceNotFoundException e) {
	           return new ResponseEntity<ApiResponseBody>(new ApiResponseBody(e.getMessage(),HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);
	         }
	      catch(Exception e) {
	         	 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR,null),HttpStatus.INTERNAL_SERVER_ERROR);
	         }	
	}

   
	@Override
	@PostMapping
	public ResponseEntity<ApiResponseBody> createVehicle(@RequestBody PostVehicle postVehicleObject) {
	       try {
	        	 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody("Vehicle Created Successfully",HttpStatus.CREATED,vehiclemanagerservice.createVehicle(postVehicleObject)),HttpStatus.CREATED);
	         }catch(Exception e) {
	        	 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,null),HttpStatus.INTERNAL_SERVER_ERROR);
	         }
	}
    
	   

	@Override
	@PutMapping("/{registrationNumber}")
	public ResponseEntity<ApiResponseBody> updateVehicle(@PathVariable (value="registrationNumber") String regNo, @RequestBody PutVehicle putVehicleObject) {
		   try {
        	   VehicleResponseDto updatedVehicleDto =vehiclemanagerservice.updateVehicle(putVehicleObject, regNo);
        	return new ResponseEntity<ApiResponseBody>(new ApiResponseBody("Vehicle Updated Successfully",HttpStatus.CREATED, updatedVehicleDto),HttpStatus.CREATED);
        }catch(ResourceNotFoundException e) {
        	 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody(e.getMessage(),HttpStatus.NOT_FOUND, null),HttpStatus.NOT_FOUND);
        }catch(Exception e) {
        	 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

    @DeleteMapping ("/{registrationNumber}")
	@Override
	public ResponseEntity<ApiResponseBody> deleteVehicle(@PathVariable(value="registrationNumber") String regNo) {
        try {
        	vehiclemanagerservice.deleteVehicle(regNo);
        	return new ResponseEntity<ApiResponseBody>(new ApiResponseBody("Vehicle Deleted Successfully",HttpStatus.OK, null),HttpStatus.OK);
        }catch(ResourceNotFoundException e) {
        	 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody(e.getMessage(),HttpStatus.NOT_FOUND, null),HttpStatus.NOT_FOUND);
        }catch(Exception e) {
        	 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}


}
