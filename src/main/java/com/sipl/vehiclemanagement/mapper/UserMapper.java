package com.sipl.vehiclemanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.sipl.vehiclemanagement.dto.user.UserResponseDto;
import com.sipl.vehiclemanagement.dto.user.UserSignup;
import com.sipl.vehiclemanagement.model.User;

@Mapper (componentModel= MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
   
	UserMapper INSTANCE= Mappers.getMapper(UserMapper.class);
	
	User userSignupToUser(UserSignup signupObject);
	
	UserResponseDto userToUserResponse(User user);
	
	
}
