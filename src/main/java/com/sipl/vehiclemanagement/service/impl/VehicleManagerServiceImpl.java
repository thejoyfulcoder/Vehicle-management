package com.sipl.vehiclemanagement.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sipl.vehiclemanagement.dto.PostVehicle;
import com.sipl.vehiclemanagement.dto.PutVehicle;
import com.sipl.vehiclemanagement.dto.VehicleResponseDto;
import com.sipl.vehiclemanagement.exception.ResourceAlreadyExistsException;
import com.sipl.vehiclemanagement.exception.ResourceNotFoundException;
import com.sipl.vehiclemanagement.mapper.VehicleMapper;
import com.sipl.vehiclemanagement.model.Vehicle;
import com.sipl.vehiclemanagement.repository.VehicleManagerRepository;
import com.sipl.vehiclemanagement.service.VehicleManagerService;

@Service
public class VehicleManagerServiceImpl implements VehicleManagerService{
     
	private VehicleManagerRepository vehicleManagerRepository;
	
	@Autowired
	private VehicleMapper vehicleMapper;
	
	public VehicleManagerServiceImpl(VehicleManagerRepository vehicleManagerRepository) {
	      this.vehicleManagerRepository= vehicleManagerRepository;
	}
	

	
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


	
}
