package com.sipl.vehiclemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sipl.vehiclemanagement.model.User;
import com.sipl.vehiclemanagement.model.Vehicle;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
     
	 Optional<User> findByUsername(String username);

     @Transactional
	 boolean existsByUsername(String username);
}
