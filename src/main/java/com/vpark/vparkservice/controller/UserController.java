package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ProfileDto;
import com.vpark.vparkservice.dto.UserAccountDTO;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.UserProfile;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kalana.w on 5/17/2020.
 */
@RestController
public class UserController  implements IUserController  {
    @Autowired
    private UserService userService;
    @Autowired
    private Environment ENV;

    
    @Override
	public ResponseEntity<EsResponse<?>> userMobileRegistration(String mobileNo) {
		 return ResponseEntity.ok(this.userService.userRegistration(mobileNo));
	}
    
    @Override
    public ResponseEntity<EsResponse<User>> otpVaildation(@PathVariable String mobileNo,@PathVariable String otp){
    	 return ResponseEntity.ok(this.userService.updateUserStatus(mobileNo,otp));
    }
    
    @Override
    public ResponseEntity<EsResponse<?>> createNewUser(@RequestBody User user) {
        return ResponseEntity.ok(this.userService.createNewUser(user));
    }

  
    @Override
    public ResponseEntity<EsResponse<ProfileDto>> findUserProfile(long userId) {
        if (userId <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.userService.findUserProfile(userId));
    }
    
    
    public ResponseEntity<EsResponse<UserAccountDTO>> findUserWallet( long userId){
    	 if (userId <= 0) {
             return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
         }
         return ResponseEntity.ok(this.userService.findUserWallet(userId));
    }
    
    @Override
    public ResponseEntity<EsResponse<?>> updateUserProfile(long userId, UserProfile userProfile) {
        if (userId <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.userService.updateUserProfile(userId, userProfile));
    }
    

    @Override
    public ResponseEntity<EsResponse<?>> createUserPassword(long userId, User user) {
        if (userId <= 0 || userId != user.getId()) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
    return null;
    }

  
 

	
}
