package com.vpark.vparkservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.AgentparkingLocationDTO;
import com.vpark.vparkservice.dto.ParkingTypeDTO;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.entity.ParkingType;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IParkingLocationRepository;
import com.vpark.vparkservice.repository.IParkingTypeRepository;

@Service
public class AgentParkingLocService  {
	
    @Autowired
	private IParkingLocationRepository parkingLocationRepository;
       
	 @Autowired
	 private Environment ENV;
	 
	 @Autowired
	 private ModelMapper modelMapper;
	    
	  @Autowired
	  private IParkingTypeRepository   parkingTypeRepository ;
	    
	 public EsResponse<ParkingLocation> createNewLocation(AgentparkingLocationDTO parkingLocationDto  , long userId) {
	        try {
	        	ParkingLocation parkingLocVo  = modelMapper.map(parkingLocationDto, ParkingLocation.class) ;
	        	
	        	ParkingType parkingTypeVo = new ParkingType() ;
	        	parkingTypeVo.setId(parkingLocationDto.getParkingTypeId());
	        	parkingLocVo.setParkingTypeId(parkingTypeVo);
	        	
	        	User user = new User() ;
	        	user.setId(userId);
	        	parkingLocVo.setUser(user);
	        	
	        	this.parkingLocationRepository.save(parkingLocVo);
	
	            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK  , this.ENV.getProperty("parking.location.creation.success"));
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.creation.failed"));
	        }
	    }
	 
	 
	 public EsResponse<?> findAllParkingLocationById(long userId) {
	        try {
	        	List<ParkingLocation> parkingLocVos = this.parkingLocationRepository.FindByUserId(userId);
	        	
	        	List<AgentparkingLocationDTO> parkLocDtos = parkingLocVos .stream()
	      			  .map((parkingLocVo) -> {
	      				AgentparkingLocationDTO  parkLocDto =  modelMapper.map(parkingLocVos , AgentparkingLocationDTO.class);
	      				return parkLocDto ;
	      			  })
	      			  .collect(Collectors.toList());
	       
	            return  new EsResponse<>(IConstants.RESPONSE_STATUS_OK, parkLocDtos , this.ENV.getProperty("vehicle.found"));
	                  
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.not.found"));
	        }
	    }
	 
	 
	 
	 
	 public EsResponse<List<ParkingTypeDTO>> findAllParkingType() {
	     try{
	    	List<ParkingType> parkingTypeVos    =  parkingTypeRepository.findAll() ;
	    	 
	    		List<ParkingTypeDTO> parkingTypeDtos = parkingTypeVos .stream()
	      			  .map(parkingType -> modelMapper.map(parkingType, ParkingTypeDTO.class))
	      			  .collect(Collectors.toList());
	    	 
	        return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, parkingTypeDtos , this.ENV.getProperty("parking.type.found"));
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.type.not.found"));
	    }
	    }
}
