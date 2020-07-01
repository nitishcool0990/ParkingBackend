package com.vpark.vparkservice.service;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.dto.ParkingReviewDTO;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.entity.ParkingReviews;
import com.vpark.vparkservice.entity.ParkingSearchLocation;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.mapper.ParkingLocMapper;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IParkingLocationRepository;
import com.vpark.vparkservice.repository.IParkingReviewsRepository;
import com.vpark.vparkservice.repository.IParkingSearchLocation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by kalana.w on 5/22/2020.
 */
@Service
public class ParkingLocationService {
	
    @Autowired
    private IParkingLocationRepository parkingLocationRepository;
    
    @Autowired
    private Environment ENV;
    
    @Autowired
	 private ModelMapper modelMapper;
    
    @Autowired
    private  IParkingReviewsRepository   parkingReviewsRepository ;
        
    @Autowired
    private ParkingLocMapper parkingLocMapper ;
    
    @Autowired
    private IParkingSearchLocation parkingSearchLoc;


    public EsResponse<ParkingLocation> findLocationDetailsById(long id) {
        try {
            Optional<ParkingLocation> byId = this.parkingLocationRepository.findById(id);
            return byId.map(vehicle -> new EsResponse<>(IConstants.RESPONSE_STATUS_OK, vehicle, this.ENV.getProperty("parking.location.found")))
                    .orElseGet(() -> new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.not.found")));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.not.found"));
        }
    }

    
 

    public EsResponse<?> updateParkReviews( ParkingReviewDTO parkingReviewDto) {
      try {
    	  Optional<ParkingReviews> parkingReviewVo = this.parkingReviewsRepository.findById(parkingReviewDto.getReviewId());
    	  
    	  if(parkingReviewVo.isPresent()){
    		  parkingReviewVo.get().setReply(parkingReviewDto.getReply());
    	  }
    	  this.parkingReviewsRepository.save(parkingReviewVo.get());
         
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("parking.review.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.review.update.failed"));
        }
    }

    
    
    public EsResponse<?> addParkReview(  long userId ,  ParkingReviewDTO parkingReviewDto) {
        try {
        	
        	List<ParkingReviews > parkingReviewsVos  =  this.parkingReviewsRepository.findAllByLocationId(parkingReviewDto.getParkingLocId());
        	Integer sum = parkingReviewsVos.stream().map(parkingReviewsVo ->  parkingReviewsVo.getRating()   ).reduce(0, Integer::sum) ; 
        	
        	 sum = sum  +parkingReviewDto.getRating();
        	double avgRating = sum / (parkingReviewsVos.size() + 1);
        	
        	Optional<ParkingLocation>  parkLocVo  = this.parkingLocationRepository.findById(parkingReviewDto.getParkingLocId());
        	parkLocVo.ifPresent(parkLocVo1 ->{ 
        		parkLocVo1.setRating(avgRating);
        		this.parkingLocationRepository.save(parkLocVo1);
        		});
        
        	ParkingReviews  parkingReviewsVo = modelMapper.map(parkingReviewDto, ParkingReviews.class) ;
        	
        	User user = new User() ;
        	user.setId(userId);
        	parkingReviewsVo.setUser(user);
        	   
        	this.parkingReviewsRepository.save(parkingReviewsVo);
        	
      
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK , this.ENV.getProperty("parking.review.save.success"));
            
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.review.save.failed"));
        }
    }

    
    public EsResponse<?> deleteParkReview( long reviewId) {
       try{
        Optional<ParkingReviews> parkingReviewVo = this.parkingReviewsRepository.findById(reviewId);

    	if(null != parkingReviewVo)
               this.parkingReviewsRepository.deleteById(reviewId);
    		  
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK , this.ENV.getProperty("parking.review.delete.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.review.delete.failed"));
        }
    }

    
    public EsResponse<List<ParkingLocationDto>> findLocationByCoordinates(double  latitude,double  longitude,int  vehicleTypeId  , long userId) {
    	try {
    		 List<Object[]> closestParkingList = parkingLocationRepository.getClosestParkingArea("KM",latitude, longitude, 2, 20,vehicleTypeId);
    		
    		 if(closestParkingList!=null && closestParkingList.size()>0) {
	    		 List<ParkingLocationDto> list = closestParkingList.stream()
	    				 
	    				 .map(objectArray->new ParkingLocationDto((BigInteger)objectArray[0], objectArray[1], objectArray[2], (double)objectArray[3], objectArray[4].toString(), 
	    						                                                             (double)objectArray[5], (double)objectArray[6])).collect(Collectors.toList());
	    		  
	    		 return new EsResponse<>(IConstants.RESPONSE_STATUS_OK,list,  this.ENV.getProperty("parking.location.found"));
    		 }
    		 else {
    			 // Add this location in parking search location
    			 ParkingSearchLocation parkSearchLocVo  = parkingLocMapper.createParkingSearchLocationVo(latitude , longitude ,  userId);
    			 this.parkingSearchLoc.save(parkSearchLocVo);
    			 return new EsResponse<>(IConstants.RESPONSE_STATUS_OK,  this.ENV.getProperty("parking.location.not.found"));
    		 }
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internal.error"));
        }
}
    
    
    
    public EsResponse<List<ParkingReviewDTO>> findAllReviews( long locId  ) {
       try{
    	List<ParkingReviews > parkingReviewsVos  = this.parkingReviewsRepository.findAllByLocationId(locId);
    	                                                                         
    	
    	   List<ParkingReviewDTO> ParkingReviewDtos = parkingReviewsVos.stream()
    			  .map((parkingReviewsVo) -> {
    				  ParkingReviewDTO  parkReviewDto =  modelMapper.map(parkingReviewsVo , ParkingReviewDTO.class);
    				  parkReviewDto.setReviewId(parkingReviewsVo.getId());
    				  parkReviewDto.setReviewerName(parkingReviewsVo.getUser().getUserProfile().getFirstName());
    				  parkReviewDto.setCreateDate(parkingReviewsVo.getCreatedDate());
    				  parkReviewDto.setReply(parkingReviewsVo.getReply());
    				  
    				return parkReviewDto ;
    			  })
    			  .collect(Collectors.toList());
       
        return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, ParkingReviewDtos , this.ENV.getProperty("parking.review.search.success"));
       }catch (Exception e) {
           e.printStackTrace();
           return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.review.search.failed"));
       }
    }
    
    
    
    

}
