package com.vpark.vparkservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vpark.vparkservice.entity.UserOtp ;


public interface IUserOtp extends JpaRepository<UserOtp , Integer>{
	
	@Query("select mobileNo from UserOtp uotp where uotp.mobileNo = ?1 and uotp.otp = ?2 ")
	 List<String> verifyOTPWithMobileNumber(String mobileNo, String otp);

}
