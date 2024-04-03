package com.sipl.vehiclemanagement;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sipl.vehiclemanagement.repository.VehicleManagerRepository;

@SpringBootTest
class VehicleManagementApplicationTests {
	

	@Test
	void contextLoads() {
	}
	
	
	@Autowired
	private VehicleManagerRepository vehicleManagerRepository;
	@Test
	public void firstTest() {
		 assertNotNull(vehicleManagerRepository.findAll());
	}

}
