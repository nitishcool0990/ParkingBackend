package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ProfileDto;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.UserProfile;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kalana.w on 5/17/2020.
 */
@RestController
public class UserController implements IUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private Environment ENV;

    @Override
    public ResponseEntity<EsResponse<?>> createNewUser(@RequestBody User user) {
        return ResponseEntity.ok(this.userService.createNewUser(user));
    }

    @Override
    public ResponseEntity<EsResponse<User>> findUserById(long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.userService.findUserById(id));
    }

    @Override
    public ResponseEntity<EsResponse<ProfileDto>> findUserProfile(long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.userService.findUserProfile(id));
    }

    @Override
    public ResponseEntity<EsResponse<User>> updateUser(long id, User user) {
        if (id <= 0 || id != user.getId()) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.userService.updateUser(id, user));
    }

    @Override
    public ResponseEntity<EsResponse<ProfileDto>> updateUserProfile(long id, UserProfile userProfile) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
        }
        return ResponseEntity.ok(this.userService.updateUserProfile(id, userProfile));
    }
}
