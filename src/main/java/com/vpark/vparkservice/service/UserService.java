package com.vpark.vparkservice.service;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by kalana.w on 5/17/2020.
 */
@Service
public class UserService
{
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private Environment ENV;

	public EsResponse<?> createNewUser( User user )
	{
		try
		{
			user.setPassword( this.bcryptEncoder.encode( user.getPassword() ) );
			return new EsResponse<>( IConstants.RESPONSE_STATUS_OK, this.userRepository.save( user ), this.ENV.getProperty( "user.registration.success" ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			return new EsResponse<>( IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty( "user.registration.fail" ) );
		}
	}
}
