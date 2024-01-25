package com.sipl.vehiclemanagement.service.impl;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


@Service
public class VehicleManagerServiceImpl implements VehicleManagerService{
 
//	//Encryption credentials
//	private final IvParameterSpec ivParameterSpec = EncryptionUtil.generateIv();
//	private final String algorithm = "AES/CBC/PKCS5Padding";
    
	@Autowired
	private EncryptionUtil encryptionUtil;
	   
	@Autowired
	private VehicleManagerRepository vehicleManagerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VehicleMapper vehicleMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	
//	public VehicleManagerServiceImpl() throws NoSuchAlgorithmException {
//          this.key= EncryptionUtil.getKeyFromPassword(algorithm, algorithm)
//	}
//	

	
	@Override
	public List<VehicleResponseDto> getAllVehicles() {
         List<Vehicle> allVehicles= vehicleManagerRepository.findAll();
        return  vehicleMapper.vehicleListToVehicleResponseDtoList(allVehicles);
	}
	
	

	@Override
	public VehicleResponseDto getVehicleByRegistrationNumber(String regNo) throws ResourceNotFoundException {
		
	     Optional<Vehicle> optionalContainer = vehicleManagerRepository.findByVehicleRegistrationNumber(regNo);
	             
	             if(optionalContainer.isEmpty()) {
	            	 throw new ResourceNotFoundException("Vehicle", "registrationNumber", regNo);
	             }
	             Vehicle fetchedVehicle= optionalContainer.get();
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
}
