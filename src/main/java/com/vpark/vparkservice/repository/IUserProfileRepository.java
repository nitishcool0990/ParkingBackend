package com.vpark.vparkservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vpark.vparkservice.entity.UserProfile;

@Repository
public interface IUserProfileRepository extends JpaRepository<UserProfile, Long>{

	
}
