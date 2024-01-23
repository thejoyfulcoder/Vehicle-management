package com.sipl.vehiclemanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.sipl.vehiclemanagement.dto.PostVehicle;
import com.sipl.vehiclemanagement.dto.PutVehicle;
import com.sipl.vehiclemanagement.dto.VehicleResponseDto;
import com.sipl.vehiclemanagement.model.Vehicle;
import java.time.LocalDateTime;
@Mapper (componentModel= MappingConstants.ComponentModel.SPRING)
public interface VehicleMapper {
	
	VehicleMapper INSTANCE= Mappers.getMapper(VehicleMapper.class);
      
//	  @Mapping(target="modifiedBy", constant="notApplicable")
//	  @Mapping(target="modificationTime", constant="notApplicable")
      @Mapping(target="creationTime", source="creationTime" , defaultExpression="java(java.time.LocalDateTime.now())")
	  @Mapping(target = "createdBy", source = "creatorName")
      Vehicle postvehicleToVehicle(PostVehicle postVehicleObject);
       
      @Mapping(target="modificationTime", source="modificationTime", defaultExpression="java(java.time.LocalDateTime.now())")
	  @Mapping(target = "modifiedBy", source = "modifierName")
      Vehicle putvehicleToVehicle(PutVehicle putVehicleObject);
      
      VehicleResponseDto vehicleToVehicleResponseDto(Vehicle vehicle);
      
      List<VehicleResponseDto> vehicleListToVehicleResponseDtoList(List<Vehicle> vehicleList);
      
}
