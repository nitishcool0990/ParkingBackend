package com.vpark.vparkservice.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.MyParkingHistoryDTO;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.entity.ParkBookingHistory;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IParkBookingHistoryRepository;
import com.vpark.vparkservice.repository.IParkingLocationRepository;


@Service
public class ParkingBookingService {
	
	 @Autowired
	 private IParkingLocationRepository parkingLocationRepository;
	    
	 @Autowired
	 private Environment ENV;
	 
	 @Autowired
	 private ModelMapper modelMapper;
	 
	 @Autowired
	 private IParkBookingHistoryRepository   ParkBookingHistoryRepository ;
	    
	
	public EsResponse<ParkingLocationDto> getParkingInfo(long parkingId, long vehicleTypeId) {
		try {
			List<Object[]> objList = parkingLocationRepository.getParkingInfo(parkingId, vehicleTypeId);
			ParkingLocationDto parkingLocDTO = null;
			if (!objList.isEmpty()) {
				Object[] obj = objList.get(0);
				parkingLocDTO = new ParkingLocationDto((Long) obj[0], (double) obj[6], (double) obj[7], (double) obj[5],
						obj[4].toString(), obj[1].toString(), obj[2].toString(), obj[3].toString(), obj[8].toString());
				return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, parkingLocDTO , this.ENV.getProperty("booking.parking.details"));
			} else {
				return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR , this.ENV.getProperty("exception.internalerror"));
			 }

		} catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR , this.ENV.getProperty("exception.internal.error"));
		   }
	 }
	
	
	
	public EsResponse<	List<MyParkingHistoryDTO> > getUserParkingHistory(long userId){
		try{
			List<ParkBookingHistory>  parkBookingHistVos = ParkBookingHistoryRepository.findByUserId(userId);
			
			List<MyParkingHistoryDTO> myParkingHistoryDtos    = parkBookingHistVos.stream()
					 .map((parkHistoryVo) ->{
				MyParkingHistoryDTO  myParkingHistoryDto  = modelMapper.map(parkHistoryVo , MyParkingHistoryDTO.class) ;
				                                        myParkingHistoryDto.setBookingDate(parkHistoryVo.getCreatedDate()) ;
				                                        
				return myParkingHistoryDto;
				
			}).collect(Collectors.toList());
			
			return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, myParkingHistoryDtos , this.ENV.getProperty("booking.parking.history.found"));
		}
		catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR , this.ENV.getProperty("exception.internal.error"));
		   }
	
	}

}
