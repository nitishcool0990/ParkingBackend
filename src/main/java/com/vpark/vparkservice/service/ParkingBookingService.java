package com.vpark.vparkservice.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IParkingLocationRepository;

@Service
public class ParkingBookingService {
	
	 @Autowired
	 private IParkingLocationRepository parkingLocationRepository;
	    
	 @Autowired
	 private Environment ENV;
	    
	
	public EsResponse<ParkingLocationDto> getParkingInfo(long parkingId, long vehicleTypeId) {
		try {
			
			List<Object[]> objList = parkingLocationRepository.getParkingInfo(parkingId, vehicleTypeId);
//			/Select pl.parkName,pl.openTime,pl.closeTime,pl.description,pl.rating,pd.hourlyRate,pd.monthlyRate
			ParkingLocationDto parkingLocDTO =null;
			if(!objList.isEmpty()) {
					Object[] obj = objList.get(0);
					parkingLocDTO = new ParkingLocationDto((Long)obj[0],  (double)obj[6], (double)obj[7], (double)obj[5], obj[4].toString(),obj[1].toString(),obj[2].toString(),obj[3].toString());
					 return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, parkingLocDTO,this.ENV.getProperty("booking.parking.details"));
			}else {
				 return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internalerror"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internalerror"));
		}
		
		
	}

}
