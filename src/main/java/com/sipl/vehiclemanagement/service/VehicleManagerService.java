package com.sipl.vehiclemanagement.service;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.web.client.RestClientException;

import com.lowagie.text.DocumentException;
import com.sipl.vehiclemanagement.dto.user.UserLogin;
import com.sipl.vehiclemanagement.dto.user.UserResponseDto;
import com.sipl.vehiclemanagement.dto.user.UserSignup;
import com.sipl.vehiclemanagement.dto.vehicle.PostVehicle;
import com.sipl.vehiclemanagement.dto.vehicle.PutVehicle;
import com.sipl.vehiclemanagement.dto.vehicle.VehicleResponseDto;
import com.sipl.vehiclemanagement.exception.IncorrectPasswordException;
import com.sipl.vehiclemanagement.exception.ResourceAlreadyExistsException;
import com.sipl.vehiclemanagement.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletResponse;

public interface VehicleManagerService {
    
	 List<VehicleResponseDto> getAllVehicles();
	 
	 List<VehicleResponseDto> getVehiclesByPage(int pageNo, int pageSize);
	 
	 VehicleResponseDto getVehicleByRegistrationNumber(String regNo) throws ResourceNotFoundException;  //Throws ResourceNotFoundException if vehicle doesn't exist
	 
	 VehicleResponseDto createVehicle(PostVehicle postVehicleObject) throws ResourceAlreadyExistsException; 
	 
	 VehicleResponseDto  updateVehicle(PutVehicle putVehicleObject, String regNo) throws ResourceNotFoundException;  //Throws ResourceNotFoundException if vehicle doesn't exist
	 
	 void deleteVehicle(String regNo) throws ResourceNotFoundException;  //Throws ResourceNotFoundException if vehicle doesn't exist
	 
	 Object getIndianVehicles() throws RestClientException ;
	 
	 void generatePdf(HttpServletResponse response) throws DocumentException, IOException;
	 
	 UserResponseDto signup(UserSignup userSignupObject) throws ResourceAlreadyExistsException ,NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
	    BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeySpecException;
	 
	 UserResponseDto login(UserLogin userLoginObject) throws ResourceNotFoundException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, IncorrectPasswordException, InvalidKeySpecException;
}
