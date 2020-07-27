package com.vpark.vparkservice.service;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ProfileDto;
import com.vpark.vparkservice.dto.UserAccountDTO;
import com.vpark.vparkservice.entity.ReferalCodeHistory;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.UserOtp;
import com.vpark.vparkservice.entity.UserProfile;
import com.vpark.vparkservice.entity.UserWallet;
import com.vpark.vparkservice.mapper.UserMapper;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.OTPResponse;
import com.vpark.vparkservice.repository.IReferalCodeHistoryRepository;
import com.vpark.vparkservice.repository.IUserOtp;
import com.vpark.vparkservice.repository.IUserProfileRepository;
import com.vpark.vparkservice.repository.IUserRepository;
import com.vpark.vparkservice.repository.IUserWalletRepository;
import com.vpark.vparkservice.urlcalling.URLCaller;
import com.vpark.vparkservice.util.CommonProperties;
import com.vpark.vparkservice.util.OTPGenerateUtil;
import com.vpark.vparkservice.util.ReferalCodeUtil;
import com.vpark.vparkservice.util.Utility;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


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
    private ReferalCodeUtil  referalCodeUtil ;
    
    @Autowired
	private CommonProperties commonProperties;
    
    @Autowired
    private IUserOtp userOTP;
    
    @Autowired
    private UserMapper userMapper ;
    
    @Autowired
    private IUserWalletRepository   userWalletRepository ;
    
    @Autowired
    private IUserProfileRepository  userProfileRepository ;
    
    @Autowired
    private IReferalCodeHistoryRepository referalCodeHisRepository ;
    
    Date date =new Date();
    
    
    public EsResponse<?> userRegistration(String mobileNo) {
		try {
			User userVo = this.userRepository.findByMobileNo(mobileNo);
			
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

    
	public EsResponse<User> updateUserStatus(String mobileNo, String otp) {
		try {
			List<String> userOTP = this.userOTP.verifyOTPWithMobileNumber(mobileNo, otp);
			if (userOTP != null && !userOTP.isEmpty()) {
				User user = this.userRepository.findByMobileNo(mobileNo);
				if (user != null && user.getStatus().equals(IConstants.Status.INACTIVE)) {

					user.setStatus(IConstants.Status.ACTIVE);
					this.userRepository.save(user);
					return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("user.update.success"));
				} else {
					if(user != null && user.getStatus().equals(IConstants.Status.ACTIVE)) {
						return new EsResponse<>(IConstants.RESPONSE_OPEN_RESET_SCREEN,this.ENV.getProperty("user.reset.password"));
					}else {
					
						return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR,this.ENV.getProperty("user.update.failed"));
					}
				}
			} else {
				return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.verifyOTP.failed"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.update.failed"));
		}
	}
    
    
    
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public EsResponse<?> createNewUser(User user) {
		try {
			UserProfile userProfileObj = this.userProfileRepository.findByEmail( user.getUserProfile().getEmail());
			if(null != userProfileObj){
				return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.email.already.exist"));
			}
			
			User userObj = this.userRepository.findByMobileNo(user.getMobileNo());
			if (userObj != null) {
				
				user.setPassword(this.bcryptEncoder.encode(user.getPassword()));
				user.setId(userObj.getId());
				user.setStatus(IConstants.Status.ACTIVE);
				
				if(null != user.getUserProfile().getReferalCode()  &&  !user.getUserProfile().getReferalCode().isEmpty() ){
					
					User referUserObj  = this.userRepository.findByReferalCodeLike(Utility.queryLikeAny(user.getUserProfile().getReferalCode()));
					if(null == referUserObj){
						return new EsResponse<>(IConstants.RESPONSE_VERIFICATION_FAILED ,this.ENV.getProperty("user.verify.referal.code.failed"));
					}
					ReferalCodeHistory  referalCodeHistoryVo = userMapper.createReferalCodeHistoryVo(user  , referUserObj);
					this.referalCodeHisRepository.save(referalCodeHistoryVo);
					
				}
				
				user.getUserProfile().setReferalCode(user.getId()+referalCodeUtil.getSaltString(4));
			
				UserWallet userWalletVo = new UserWallet ();
				userWalletVo.setUser(userObj);
				
				BeanUtils.copyProperties(user, userObj);

				this.userRepository.save(userObj);
				this.userWalletRepository.save(userWalletVo);

				return new EsResponse<>(IConstants.RESPONSE_STATUS_OK ,this.ENV.getProperty("user.profile.creation.success"));
			} else {
				return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.not.found"));
			}
		} catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.profile.creation.failed"));
        }
    }

    
    
	public EsResponse<ProfileDto> findUserProfile(long id) {
		try {
			ProfileDto profileDto = new ProfileDto();
			Optional<User> userVo = this.userRepository.findById(id);

			if (userVo.isPresent()) {
				profileDto = modelMapper.map(userVo.get().getUserProfile(), ProfileDto.class);
				profileDto.setMobileNo(userVo.get().getMobileNo());
				profileDto.setUserRole(userVo.get().getUserType().toString());
			}

			return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, profileDto, this.ENV.getProperty("user.found"));

		} catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.not.found"));
		}
	}
	
	
	
	public EsResponse<UserAccountDTO> findUserWallet(long userId) {
		try {
			UserAccountDTO userAccDto  = new UserAccountDTO() ;
			Optional<UserWallet> userWalletVo = this.userWalletRepository.findByUserId(userId);
			
			if (userWalletVo.isPresent()) {
				UserWallet userWallet = userWalletVo.get();
				double amt  =  userWallet.getDeposit() + userWallet.getBonus()+userWallet.getReal() ;
				userAccDto.setTotalAmt(amt);
			}
			else
				userAccDto.setTotalAmt(0);

			return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, userAccDto, this.ENV.getProperty("user.wallet.found"));

		} catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.wallet.not.found"));
		}
	}

  
	

   
	public EsResponse<ProfileDto> updateUserProfile(long id, UserProfile userProfile) {
		try {
			Optional<User> userVo = this.userRepository.findById(id);
			
			if (userVo.isPresent() && null != userVo.get().getUserProfile() && userVo.get().getUserProfile().getId() > 0) {
				userProfile.setId(userVo.get().getUserProfile().getId());
				userProfile.setReferalCode(userVo.get().getUserProfile().getReferalCode());
				userVo.get().setUserProfile(userProfile);
			}
			this.userRepository.save(userVo.get());

			return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("user.profile.update.success"));
		} catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR,
					this.ENV.getProperty("user.profile.update.failed"));
		  }
	  }
	
	
	public EsResponse<User> resetPassword(String pass, String repass,String mobileNo) {
		try {
			
			if(pass.equalsIgnoreCase(repass)) {
				User userVo = this.userRepository.findUserByMobileNo(mobileNo).orElse(null);
				if( userVo!=null) {
					
					userVo.setPassword(this.bcryptEncoder.encode(pass));
					this.userRepository.save(userVo);
					return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("user.profile.update.success"));
	
				}else {
					return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.not.found"));
				}
			}else {
				
				return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.not.same.password"));
			}
		}catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR,
					this.ENV.getProperty("user.profile.update.failed"));
		  }
		
	}
    
   
}
