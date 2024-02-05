package com.sipl.vehiclemanagement.service.impl;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
import com.sipl.vehiclemanagement.mapper.UserMapper;
import com.sipl.vehiclemanagement.mapper.VehicleMapper;
import com.sipl.vehiclemanagement.model.User;
import com.sipl.vehiclemanagement.model.Vehicle;
import com.sipl.vehiclemanagement.repository.UserRepository;
import com.sipl.vehiclemanagement.repository.VehicleManagerRepository;
import com.sipl.vehiclemanagement.service.VehicleManagerService;
import com.sipl.vehiclemanagement.util.EncryptionUtil;
import com.sipl.vehiclemanagement.util.PdfGenerator;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class VehicleManagerServiceImpl implements VehicleManagerService{
    
	@Autowired
	private VehicleManagerRepository vehicleManagerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VehicleMapper vehicleMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<VehicleResponseDto> getAllVehicles() {
         List<Vehicle> allVehicles= vehicleManagerRepository.findAll();
        return  vehicleMapper.vehicleListToVehicleResponseDtoList(allVehicles);
	}
	
	
	@Override
	public List<VehicleResponseDto> getVehiclesByPage(int pageNo, int pageSize) {
		
		Pageable paging= PageRequest.of(pageNo, pageSize);
		Page<Vehicle> pageOfVehicles=vehicleManagerRepository.findAll(paging);
		if(pageOfVehicles.hasContent()) {
			return vehicleMapper.vehicleListToVehicleResponseDtoList(pageOfVehicles.getContent());
		}else {
			return new ArrayList<VehicleResponseDto>();
		}
	} 
	
	

	@Override
	@Cacheable (value="vehicleByRegistrationNumberCache")
	public VehicleResponseDto getVehicleByRegistrationNumber(String regNo) throws ResourceNotFoundException {
		
	     Optional<Vehicle> optionalContainer = vehicleManagerRepository.findByVehicleRegistrationNumber(regNo);
	             
	             if(optionalContainer.isEmpty()) {
	            	 throw new ResourceNotFoundException("Vehicle", "registrationNumber", regNo);
	             }
	             Vehicle fetchedVehicle= optionalContainer.get();
	             System.out.println("cached Service running");
	             return vehicleMapper.vehicleToVehicleResponseDto(fetchedVehicle);
	}


	@Override
	public VehicleResponseDto createVehicle(PostVehicle postVehicleObject) throws ResourceAlreadyExistsException {
		   boolean vehicleExists= vehicleManagerRepository.existsByVehicleRegistrationNumber(postVehicleObject.getVehicleRegistrationNumber());
		   if(vehicleExists) {
			   throw new ResourceAlreadyExistsException("Vehicle", "RegistrationNumber", postVehicleObject.getVehicleRegistrationNumber());
		   }
		   Vehicle vehicle= vehicleMapper.postvehicleToVehicle(postVehicleObject);
//		   vehicle.setCreationTime(LocalDateTime.now());
	      Vehicle savedVehicle =vehicleManagerRepository.save(vehicle);
	      return vehicleMapper.vehicleToVehicleResponseDto(savedVehicle);
	}


		@Override
	public VehicleResponseDto updateVehicle(PutVehicle putVehicleObject, String regNo) throws ResourceNotFoundException {
			   Optional<Vehicle> optionalContainer= vehicleManagerRepository.findByVehicleRegistrationNumber(regNo);
			   if(optionalContainer.isPresent()) {
				    Vehicle existingVehicleFromDb= optionalContainer.get();
		              Vehicle mappedVehicle=vehicleMapper.putvehicleToVehicle(putVehicleObject);
		              mappedVehicle.setCreatedBy(existingVehicleFromDb.getCreatedBy());
		              mappedVehicle.setCreationTime(existingVehicleFromDb.getCreationTime());
		              Vehicle updatedVehicle= vehicleManagerRepository.save(mappedVehicle);
		              return vehicleMapper.vehicleToVehicleResponseDto(updatedVehicle);
                     
			   }else {
				   throw new ResourceNotFoundException("Vehicle", "registrationNumber", regNo);
			   }
	}


	@Override
	public void deleteVehicle(String regNo) throws ResourceNotFoundException {
		   boolean vehicleExists= vehicleManagerRepository.existsByVehicleRegistrationNumber(regNo);
		   if(!vehicleExists) {
			   throw new ResourceNotFoundException("Vehicle", "registrationNumber", regNo);
		   }
		   vehicleManagerRepository.deleteByVehicleRegistrationNumber(regNo);
		   
	}



	@Override
	public UserResponseDto signup(UserSignup userSignupObject) throws ResourceAlreadyExistsException ,NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
    BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeySpecException {
		if(userRepository.existsByUsername(userSignupObject.getUsername())) {        //checking if the user already Exists
			 throw new ResourceAlreadyExistsException("User", "username", userSignupObject.getUsername());
		}else {
			
			User user= userMapper.userSignupToUser(userSignupObject);  //Mapping signupObject to User entity
			 String password = user.getPassword();
              SecretKey key= EncryptionUtil.generateKeyFromPassword(password);      //Generating a key from password
			
			 String cipherText = EncryptionUtil.encrypt( password, key);
			    user.setPassword(cipherText);
			User persistedUser = userRepository.save(user);        //persisting the user
	        return userMapper.userToUserResponse(persistedUser);      //mapping to returning the response of type UserResponseDto
		}
		
	}


	@Override
	public UserResponseDto login(UserLogin userLoginObject)throws ResourceNotFoundException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, IncorrectPasswordException, InvalidKeySpecException {
           Optional<User> optionalUserContainer= userRepository.findByUsername(userLoginObject.getUsername());
           
           if(optionalUserContainer.isEmpty()) {
        	     throw new ResourceNotFoundException("User", "username", userLoginObject.getUsername());
           }else {
        	   User userFromDb = optionalUserContainer.get();
        	  
        	   String encryptedPasswordFromDb= userFromDb.getPassword();
               SecretKey key= EncryptionUtil.generateKeyFromPassword(userLoginObject.getPassword());   //Generating a key from password
        	   String decryptedPasswordPlainText = EncryptionUtil.decrypt(encryptedPasswordFromDb, key);
        	   
        	   
        	   if(decryptedPasswordPlainText.equals(userLoginObject.getPassword())) {
        		     return userMapper.userToUserResponse(userFromDb);
        	   }else {
        		     throw new IncorrectPasswordException();
        	   }
         }
	}

	 //RestTemplate
	@Override
	public Object getIndianVehicles() throws RestClientException {
	    RestTemplate rest= new RestTemplate();
	    String url= "http://localhost:3001/vehicles/indian";
	  
	    ResponseEntity<Object> response = rest.getForEntity(url, Object.class);
	     return response.getBody();    
	}


	@Override
	public void generatePdf(HttpServletResponse response) throws DocumentException, IOException {
		
		response.setContentType("application/pdf");
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
		String currentDataTime= dateFormat.format(new java.util.Date());
		String headerKey="Content-Disposition";
		String headerValue= "attachment; filename=pdf_" + currentDataTime + ".pdf";
		response.setHeader(headerKey, headerValue);
		
		List<Vehicle> vehicleList= vehicleManagerRepository.findAll();
		List<VehicleResponseDto> vehicleResponseDtoList= vehicleMapper.vehicleListToVehicleResponseDtoList(vehicleList);
		
		PdfGenerator.generate(response, vehicleResponseDtoList);
		
	}

}
