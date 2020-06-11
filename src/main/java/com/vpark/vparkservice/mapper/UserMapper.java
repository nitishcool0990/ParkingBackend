package com.vpark.vparkservice.mapper;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.UserOtp;
import com.vpark.vparkservice.model.OTPResponse;

@Component("UserMapper")
public class UserMapper {

    Date date =new Date();
	
	public  UserOtp  mapOTPResponseToOTPEntity( User user ,  OTPResponse response  , String otp){
		
		Calendar calDate = Calendar.getInstance();
    	long time= calDate.getTimeInMillis();           	
    	UserOtp userOtpVo = new UserOtp();
    	userOtpVo.setMobileNo(user.getMobileNo());
    	userOtpVo.setOtp(otp.toString());
    	userOtpVo.setValidateTime(new Date(time + (5 * 60000)));
    	userOtpVo.setLinuxModifiedOn(date.getTime()/1000);
    	userOtpVo.setModifiedOn(date);
    	userOtpVo.setStatus(response.getStatus());
    	userOtpVo.setDetails(response.getDetails());
		return userOtpVo;
    	
	}
}
