package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ProfileDto;
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

    @PostMapping(IConstants.VERSION_1)
    ResponseEntity<EsResponse<?>> createNewUser(@RequestBody User user);

    @GetMapping(IConstants.VERSION_1 )
    ResponseEntity<EsResponse<User>> findUserById(@RequestAttribute("Id") long userId);

    @GetMapping(IConstants.VERSION_1 + "/profile")
    ResponseEntity<EsResponse<ProfileDto>> findUserProfile(@RequestAttribute("Id") long id);

    @PutMapping(IConstants.VERSION_1)
    ResponseEntity<EsResponse<User>> updateUser(@RequestAttribute("Id") long id, @RequestBody User user);

    @PatchMapping(IConstants.VERSION_1 + "/profile")
    ResponseEntity<EsResponse<ProfileDto>> updateUserProfile(@RequestAttribute("Id") long id, @RequestBody UserProfile userProfile);
}
