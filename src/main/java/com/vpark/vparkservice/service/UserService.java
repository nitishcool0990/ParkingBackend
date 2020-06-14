package com.vpark.vparkservice.service;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ProfileDto;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.UserOtp;
import com.vpark.vparkservice.entity.UserProfile;
import com.vpark.vparkservice.mapper.UserMapper;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.OTPResponse;
import com.vpark.vparkservice.repository.IUserOtp;
import com.vpark.vparkservice.repository.IUserRepository;
import com.vpark.vparkservice.urlcalling.URLCaller;
import com.vpark.vparkservice.util.CommonProperties;
import com.vpark.vparkservice.util.OTPGenerateUtil;
import java.util.Date;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    
    @Autowired
    private URLCaller urlCaller;
    
    @Autowired
	private CommonProperties commonProperties;
    
    @Autowired
    private IUserOtp userOTP;
    
    @Autowired
    private UserMapper userMapper ;
    
    Date date =new Date();
    
    
    public EsResponse<?> userRegistration(String mobileNo) {
		try {
			User userVo = this.userRepository.findByMobileNo(mobileNo).orElse(null);
			
			if (userVo == null || (userVo != null && userVo.getStatus().equals(IConstants.Status.INACTIVE)) || (userVo != null && userVo.getStatus().equals(IConstants.Status.ACTIVE) && !userVo.getPassword().equals("park1234"))) {
				
				if(userVo==null) {
					 userVo = new User();
					userVo.setMobileNo(mobileNo);
		    		 this.userRepository.save(userVo);
    			}
				String otp ="1234";
				OTPResponse response =null;
				if(commonProperties.isBuildForTest()) {
					response = new OTPResponse();
				}else {
					 otp = OTPGenerateUtil.OTP(IConstants.OTP_LEN);
					 response = urlCaller.callGetURL(commonProperties.getOtpURL() + commonProperties.getOtpApiKey() + "/SMS/" + userVo.getMobileNo() + "/" + otp, -1, null);
				}				
				
				
				if (response != null) {
					UserOtp userOtpVo = userMapper.mapOTPResponseToOTPEntity(userVo, response, otp);
					userOTP.save(userOtpVo);
				}
				
				return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("user.verifyMobile.success"));
			} else {
				if (userVo.getStatus().equals(IConstants.Status.ACTIVE) && userVo.getPassword().equals("park1234")) {
					return new EsResponse<>(IConstants.RESPONSE_OPEN_PROFILE, this.ENV.getProperty("user.profile.need.update"));
				} else {
					return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.found"));
				}
			}
		}catch (Exception e) {
    		e.printStackTrace();
    		 return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.verifyMobile.failed"));
		}
    }

    
    
    public EsResponse<?> createNewUser(User user) {
        try {
         	User userObj = this.userRepository.findByMobileNo(user.getMobileNo()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with mobileNo: " + user.getMobileNo()));
         	if(userObj!=null) {
         		user.setPassword(this.bcryptEncoder.encode(user.getPassword()));
         		user.setId(userObj.getId());
         		  user.setStatus(IConstants.Status.ACTIVE);
         		BeanUtils.copyProperties(user, userObj);
         	
	            User userSaved = this.userRepository.save(userObj);
	           
	            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK,  this.ENV.getProperty("user.registration.success"));
         }else {
        	 return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.registration.fail"));
         }
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
        if ( null != user.getUserProfile() ||   user.getUserProfile().getId() > 0) {
            userProfile.setId(user.getUserProfile().getId());
        }
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
    
    public EsResponse<User> updateUserStatus(String mobileNo, String otp) {
      
        try {
        	List<String> userOTP = this.userOTP.verifyOTPWithMobileNumber(mobileNo, otp);
        	if(userOTP != null  &&  !userOTP.isEmpty()) {
	        	User user = this.userRepository.findByMobileNo(mobileNo).orElseThrow(() -> new UsernameNotFoundException("User Not Found with mobileNo: " + mobileNo));
	        	if(user!=null && user.getStatus().equals(IConstants.Status.INACTIVE)) {
	        		
	        	  user.setStatus(IConstants.Status.ACTIVE);
	        	  this.userRepository.save(user);
	        	  return new EsResponse<>(IConstants.RESPONSE_STATUS_OK,  this.ENV.getProperty("user.update.success"));
	        	}else {
	        		  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.update.failed"));
	        	}
        	}else {
        		 return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.verifyOTP.failed"));
        	}
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.update.failed"));
        }
    }
}
