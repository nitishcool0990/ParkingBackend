package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kalana.w on 5/21/2020.
 */
@RequestMapping("sessions/")
public interface ILoginController
{
	@PostMapping(IConstants.VERSION_1 + "/authenticate")
	ResponseEntity<EsResponse<JwtResponse>> login( @RequestHeader String userName, @RequestHeader String password ) throws Exception;
}
