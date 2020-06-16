package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ProfileDto;
import com.vpark.vparkservice.dto.UserAccountDTO;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.UserProfile;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.RequestAttribute ;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kalana.w on 5/17/2020.
 */
@RequestMapping("users/")
public interface IUserController {

	@GetMapping(IConstants.VERSION_1+"/userMobileReg/{mobileNo}")
	 ResponseEntity<EsResponse<?>> userMobileRegistration(@PathVariable String mobileNo);
	
    @GetMapping(IConstants.VERSION_1 + "/verifyOTP/{mobileNo}/{otp}")
    ResponseEntity<EsResponse<User>> otpVaildation(@PathVariable String mobileNo,@PathVariable String otp);
	
    @PostMapping(IConstants.VERSION_1)
    ResponseEntity<EsResponse<?>> createNewUser(@RequestBody User user);

    @GetMapping(IConstants.VERSION_1 + "/profile")
    ResponseEntity<EsResponse<ProfileDto>> findUserProfile(@RequestAttribute("Id") long id);
    
    @GetMapping(IConstants.VERSION_1 + "/wallet")
    ResponseEntity<EsResponse<UserAccountDTO>> findUserWallet(@RequestAttribute("Id") long id);
    
    @PatchMapping(IConstants.VERSION_1 + "/updateprofile")
    ResponseEntity<EsResponse<?>> updateUserProfile(@RequestAttribute("Id") long id, @RequestBody UserProfile userProfile);

    @PutMapping(IConstants.VERSION_1)
    ResponseEntity<EsResponse<?>> createUserPassword(@RequestAttribute("Id") long id , @RequestBody User user);

    
    
}
