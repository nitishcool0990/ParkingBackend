package com.vpark.vparkservice.service;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ProfileDto;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.UserProfile;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by kalana.w on 5/17/2020.
 */
@Service
public class UserService {
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Environment ENV;

    public EsResponse<?> createNewUser(User user) {
        try {
            user.setPassword(this.bcryptEncoder.encode(user.getPassword()));
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.userRepository.save(user), this.ENV.getProperty("user.registration.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.registration.fail"));
        }
    }

    public EsResponse<User> findUserById(long id) {
        try {
            return this.userRepository.findById(id).map(user -> new EsResponse<>(IConstants.RESPONSE_STATUS_OK, user, this.ENV.getProperty("user.found")))
                    .orElseGet(() -> new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.not.found")));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.not.found"));
        }
    }

    public EsResponse<ProfileDto> findUserProfile(long id) {
        try {
            return this.userRepository.findById(id).map(user -> new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.modelMapper.map(user.getUserProfile(), ProfileDto.class), this.ENV.getProperty("user.found")))
                    .orElseGet(() -> new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.not.found")));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.not.found"));
        }
    }

    public EsResponse<User> updateUser(long id, User user) {
        EsResponse<User> userById = this.findUserById(id);
        if (userById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return userById;
        }
        try {
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.userRepository.save(user), this.ENV.getProperty("user.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.update.failed"));
        }
    }

    public EsResponse<ProfileDto> updateUserProfile(long id, UserProfile userProfile) {
        EsResponse<User> userById = this.findUserById(id);
        if (userById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.not.found"));
        }
        User user = userById.getData();
        user.setUserProfile(userProfile);
        this.userRepository.save(user);
        ProfileDto profileDto = this.modelMapper.map(this.userRepository.save(user).getUserProfile(), ProfileDto.class);
        try {
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, profileDto, this.ENV.getProperty("user.profile.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.profile.update.failed"));
        }

    }
}
