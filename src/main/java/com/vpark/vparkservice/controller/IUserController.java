package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.model.EsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kalana.w on 5/17/2020.
 */
@RequestMapping("users/")
public interface IUserController
{
	@PostMapping(IConstants.VERSION_1)
	ResponseEntity<EsResponse<?>> createNewUser( @RequestBody User user );
}
