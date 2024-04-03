package com.sipl.vehiclemanagement.service;



import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sipl.vehiclemanagement.repository.VehicleManagerRepository;

@SpringBootTest
public class VehicleManagerServiceTests {
	
	@Autowired
	private VehicleManagerRepository vehicleManagerRepository;
	@Test
	public void firstTest() {
		 assertNotNull(vehicleManagerRepository.findAll());
	}
 
}
