package com.vpark.vparkservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.BonusDTO;
import com.vpark.vparkservice.entity.BonusCodes;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IBonusCodeRepository;
import com.vpark.vparkservice.repository.IBonusCodeUserRepository;

@Service

public class ParkingBonusService {
	
	 @Autowired
	 private Environment ENV;
	 
	 @Autowired
	 private IBonusCodeRepository bonusCodeRepo;
	 
	 @Autowired
	 private IBonusCodeUserRepository bonusCodeUserRepo;
	
	public EsResponse<List<BonusDTO>> getBonusList( long userId) {
		try {
			List<BonusCodes> bonusCodeList = this.bonusCodeRepo.fetchAllBonusCode();
			if(bonusCodeList!=null && bonusCodeList.size()>0) {
				List<BonusDTO> bonusDTOList = new ArrayList<BonusDTO>();
				List<Long> bonusCodeID = new   ArrayList<Long>();
				BonusDTO bonusDto = null;
				List<Object[]> objectList =this.bonusCodeUserRepo.findAllBonusCodeByuserId(userId);
				for(BonusCodes bonusCode :bonusCodeList) {
					if(objectList.size()>0) {
						for(Object[] obj :objectList) {
							if(!bonusCodeID.contains((long)obj[1])) {
								if(bonusCode.getId() == (long)obj[1] && bonusCode.getMaxUsedCount() > (long)obj[2]) {
									bonusDto = new BonusDTO();
									bonusDto.setBonusCode(bonusCode.getCode());
									bonusDto.setDesc(bonusCode.getRemarks());
									bonusCodeID.add(bonusCode.getId());
									break;
								}else if(bonusCode.getId() != (long)obj[1] ) {
									bonusDto = new BonusDTO();
									bonusDto.setBonusCode(bonusCode.getCode());
									bonusDto.setDesc(bonusCode.getRemarks());
									bonusCodeID.add(bonusCode.getId());
	
									break;
								}else {
									bonusDto=null;
								}
							}
							
						}
					}else {
						bonusDto = new BonusDTO();
						bonusDto.setBonusCode(bonusCode.getCode());
						bonusDto.setDesc(bonusCode.getRemarks());
					}
					if(bonusDto !=null) {
						bonusDTOList.add(bonusDto);
					}
				}
				if(bonusDTOList.size()>0) {
				  return new EsResponse<>(IConstants.RESPONSE_STATUS_OK,bonusDTOList, this.ENV.getProperty("bonus.avaible"));
				}else {
					  return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("bonus.not.avaible"));
				}
			}else{
				  return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("bonus.not.avaible"));
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internal.error"));
		}

	}
}
