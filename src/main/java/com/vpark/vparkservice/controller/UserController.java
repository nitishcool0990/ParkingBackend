package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public ResponseEntity<EsResponse<?>> createNewUser(@RequestBody User user) {
        return ResponseEntity.ok(this.userService.createNewUser(user));
    }
}
