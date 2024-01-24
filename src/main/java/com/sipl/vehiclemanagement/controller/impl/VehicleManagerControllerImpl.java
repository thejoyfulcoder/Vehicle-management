package com.sipl.vehiclemanagement.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sipl.vehiclemanagement.controller.VehicleManagerController;
import com.sipl.vehiclemanagement.dto.user.UserLogin;
import com.sipl.vehiclemanagement.dto.user.UserSignup;
import com.sipl.vehiclemanagement.dto.vehicle.PostVehicle;
import com.sipl.vehiclemanagement.dto.vehicle.PutVehicle;
import com.sipl.vehiclemanagement.dto.vehicle.VehicleResponseDto;
import com.sipl.vehiclemanagement.exception.IncorrectPasswordException;
import com.sipl.vehiclemanagement.exception.ResourceAlreadyExistsException;
import com.sipl.vehiclemanagement.exception.ResourceNotFoundException;
import com.sipl.vehiclemanagement.responseObject.ApiResponseBody;
import com.sipl.vehiclemanagement.service.VehicleManagerService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/")
public class VehicleManagerControllerImpl implements VehicleManagerController {
	
	VehicleManagerService vehiclemanagerservice;

	public VehicleManagerControllerImpl(VehicleManagerService vehiclemanagerservice) {
		this.vehiclemanagerservice = vehiclemanagerservice;
	}


	@Override
	@GetMapping ("/vehicles")
	public ResponseEntity<ApiResponseBody> getAllVehicles() {
        try {
            return new ResponseEntity<ApiResponseBody>(new ApiResponseBody("Fetch Successfull", HttpStatus.OK,vehiclemanagerservice.getAllVehicles()), HttpStatus.OK);
         }catch(Exception e) {
         	 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,null),HttpStatus.INTERNAL_SERVER_ERROR);
         }
 	
	}


	@Override
	@GetMapping("/vehicles/{registrationNumber}")
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
	@PostMapping ("/vehicles")
	public ResponseEntity<ApiResponseBody> createVehicle(@RequestBody PostVehicle postVehicleObject) {
	       try {
	        	 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody("Vehicle Created Successfully",HttpStatus.CREATED,vehiclemanagerservice.createVehicle(postVehicleObject)),HttpStatus.CREATED);
	         }
	       catch(ResourceAlreadyExistsException  e) {
	    	   return new ResponseEntity<ApiResponseBody>(new ApiResponseBody(e.getMessage(),HttpStatus.CONFLICT,null),HttpStatus.CONFLICT);
	       }
	       catch(Exception e) {
	        	 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,null),HttpStatus.INTERNAL_SERVER_ERROR);
	         }
	}
    
	   

	@Override
	@PutMapping("/vehicles/{registrationNumber}")
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

    @DeleteMapping ("/vehicles/{registrationNumber}")
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


    @PostMapping ("users/signup")
	@Override
	public ResponseEntity<ApiResponseBody> signup(@RequestBody @Valid UserSignup signupObject, BindingResult bindingResult) {
    	if(bindingResult.hasErrors()){
    		return new ResponseEntity<ApiResponseBody> (new ApiResponseBody("Incorrect inputs", HttpStatus.BAD_REQUEST, null),HttpStatus.BAD_REQUEST);
    	}
    	else {
			try {
			return new ResponseEntity<ApiResponseBody> (new ApiResponseBody("Signup Successful",HttpStatus.CREATED, vehiclemanagerservice.signup(signupObject)),HttpStatus.CREATED);
			} catch(ResourceAlreadyExistsException e) {
				return new ResponseEntity<ApiResponseBody> (new ApiResponseBody(e.getMessage(),HttpStatus.CONFLICT,null),HttpStatus.CONFLICT);
			} catch (Exception e) {
				return new ResponseEntity<ApiResponseBody> (new ApiResponseBody(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR,null),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

    @PostMapping("users/login")
	@Override
	public ResponseEntity<ApiResponseBody> login(@RequestBody @Valid UserLogin loginObject, BindingResult bindingResult) {
	   	if(bindingResult.hasErrors()){
    		return new ResponseEntity<ApiResponseBody> (new ApiResponseBody("Incorrect inputs", HttpStatus.BAD_REQUEST, null),HttpStatus.BAD_REQUEST);
    	}
	   	else {
	   		try {
	   			return new ResponseEntity<ApiResponseBody> (new ApiResponseBody("Login Successfull", HttpStatus.OK, vehiclemanagerservice.login(loginObject)),HttpStatus.OK);
	   		}catch(ResourceNotFoundException e) {
	   		 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody(e.getMessage(),HttpStatus.NOT_FOUND, null),HttpStatus.NOT_FOUND);
	   		}catch(IncorrectPasswordException e) {
	   		 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody(e.getMessage(),HttpStatus.UNAUTHORIZED, null),HttpStatus.UNAUTHORIZED);
	   		}catch(Exception e) {
	   		 return new ResponseEntity<ApiResponseBody>(new ApiResponseBody(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR, null),HttpStatus.INTERNAL_SERVER_ERROR);
	   		}
	   	}
	}

    
//    @PostMapping ("users/login")
//	@Override
//	public ResponseEntity<ApiResponseBody> login(@RequestBody @Valid UserLogin loginObject, BindingResult bindingResult) {
//		// TODO Auto-generated method stub
//		return null;
//	}

      
}
