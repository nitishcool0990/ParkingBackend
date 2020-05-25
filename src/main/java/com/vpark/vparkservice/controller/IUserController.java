package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ProfileDto;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.UserProfile;
import com.vpark.vparkservice.model.EsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kalana.w on 5/17/2020.
 */
@RequestMapping("users/")
public interface IUserController {

    @PostMapping(IConstants.VERSION_1)
    ResponseEntity<EsResponse<?>> createNewUser(@RequestBody User user);

    @GetMapping(IConstants.VERSION_1 + "/{id}")
    ResponseEntity<EsResponse<User>> findUserById(@PathVariable long id);

    @GetMapping(IConstants.VERSION_1 + "/{id}/profile")
    ResponseEntity<EsResponse<ProfileDto>> findUserProfile(@PathVariable long id);

    @PutMapping(IConstants.VERSION_1 + "/{id}")
    ResponseEntity<EsResponse<User>> updateUser(@PathVariable long id, @RequestBody User user);

    @PatchMapping(IConstants.VERSION_1 + "/{id}/profile")
    ResponseEntity<EsResponse<ProfileDto>> updateUserProfile(@PathVariable long id, @RequestBody UserProfile userProfile);
}
