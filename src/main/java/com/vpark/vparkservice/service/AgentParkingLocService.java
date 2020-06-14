package com.vpark.vparkservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.AgentParkingLocationDTO;
import com.vpark.vparkservice.dto.ParkingDetailsDTO;
import com.vpark.vparkservice.dto.ParkingTypeDTO;
import com.vpark.vparkservice.entity.ParkingDetails;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.entity.ParkingType;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.mapper.ParkingLocMapper;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IParkingDetailsRepository;
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
	 private ParkingLocMapper  parkingLocMapper;
	    
	  @Autowired
	  private IParkingTypeRepository   parkingTypeRepository ;
	  
	  @Autowired
	  private IParkingDetailsRepository parkingDetailsRepository ;
	  
	    
	 public EsResponse<ParkingLocation> createNewLocation(AgentParkingLocationDTO parkingLocationDto  , long userId) {
	        try {
	        	ParkingLocation parkingLocVo  = modelMapper.map(parkingLocationDto, ParkingLocation.class) ;
	        	parkingLocMapper.mapAgentParkingLocToVo(parkingLocationDto, parkingLocVo);
	        	
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
	 
	 
	 public EsResponse<List<AgentParkingLocationDTO>> findAllParkingLocationByUserId(long userId) {
	        try {
	        	List<ParkingLocation> parkingLocVos = this.parkingLocationRepository.FindByUserId(userId);
	        	
	        	List<AgentParkingLocationDTO> parkLocDtos = parkingLocVos .stream()
	      			  .map((parkingLocVo) -> {
	      				AgentParkingLocationDTO  parkLocDto =  modelMapper.map(parkingLocVo , AgentParkingLocationDTO.class);
	      				parkLocDto.setParkingLocid(parkingLocVo.getId());
	      				return parkLocDto ;
	      			  })
	      			  .collect(Collectors.toList());
	       
	            return  new EsResponse<>(IConstants.RESPONSE_STATUS_OK, parkLocDtos , this.ENV.getProperty("parking.location.found"));
	                  
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.not.found"));
	        }
	    }
	 
	 
	 public EsResponse<List<ParkingDetailsDTO>> findParkingDetailsById(long locId ){
		 try {
	        	List<ParkingDetails> ParkingDetailsVos = this.parkingDetailsRepository.findByparkingLocationId(locId);
	        	
	        	List<ParkingDetailsDTO> parkingDetailsDtos = ParkingDetailsVos.stream()
	      			  .map((ParkingDetailsVo) -> {
	      				ParkingDetailsDTO  parkDetailsDto =  modelMapper.map(ParkingDetailsVo , ParkingDetailsDTO.class);
	      				parkDetailsDto.setVehicleName(ParkingDetailsVo.getVehicleType().getVehicleName());
	      				
	      				return parkDetailsDto ;
	      			  })
	      			  .collect(Collectors.toList());
	       
	            return  new EsResponse<>(IConstants.RESPONSE_STATUS_OK, parkingDetailsDtos , this.ENV.getProperty("parking.location.found"));
	                  
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.not.found"));
	        }
	 }
	 
	 
	public EsResponse<?> deleteParkingLocation(long locId) {
		try {
			Optional<ParkingLocation> parkingLocVo = this.parkingLocationRepository.findById(locId);

			if (null != parkingLocVo)
				this.parkingLocationRepository.deleteById(locId);

			return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("parking.location.delete.success"));
			
		} catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.delete.failed"));
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
