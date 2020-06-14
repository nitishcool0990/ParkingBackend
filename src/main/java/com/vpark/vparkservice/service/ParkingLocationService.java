package com.vpark.vparkservice.service;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.dto.ParkingReviewDTO;
import com.vpark.vparkservice.entity.ParkingDetails;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.entity.ParkingReviews;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IParkingLocationRepository;
import com.vpark.vparkservice.repository.IParkingReviewsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    
   
    public EsResponse<?> patchLocationDetails(long id, ParkingDetails parkingDetail) {
        EsResponse<ParkingLocation> locationById = this.findLocationDetailsById(id);
        if (locationById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return locationById;
        }
        try {
            ParkingLocation location = locationById.getData();
            //Set<ParkingDetails> parkingDetails = location.getParkingDetails();
           // Optional<ParkingDetails> pDetails = parkingDetails.stream().filter(pd -> pd.getId() == parkingDetail.getId()).findFirst();
           // pDetails.ifPresent(parkingDetails1 -> {
               // parkingDetails.remove(parkingDetails1);
                //parkingDetails.add(parkingDetail);
           // });
           // location.setParkingDetails(parkingDetails);
            ParkingLocation save = this.parkingLocationRepository.save(location);
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, save, this.ENV.getProperty("parking.location.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.update.failed"));
        }
    }

   

    public EsResponse<?> patchParkReviews(long id, ParkingReviews parkingReview) {
        EsResponse<ParkingLocation> locationById = this.findLocationDetailsById(id);
        if (locationById.getStatus() == IConstants.RESPONSE_STATUS_ERROR) {
            return locationById;
        }
        try {
            ParkingLocation location = locationById.getData();
            //Set<ParkingReviews> parkingReviews = location.getParkingReviews();
           // parkingReviews.add(parkingReview);
            //location.setParkingReviews(parkingReviews);
            ParkingLocation save = this.parkingLocationRepository.save(location);
            return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, save, this.ENV.getProperty("parking.review.save.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.review.save.failed"));
        }
    }

    
    
    public EsResponse<?> addParkReview(  long userId ,  ParkingReviewDTO parkingReviewDto) {
        try {
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

    
    public EsResponse<List<ParkingLocationDto>> findLocationByCooridates(double  latitude,double  longitude,int  vehicleTypeId) {
    	try {
    		 List<Object[]> closestParkingList = parkingLocationRepository.getClosestParkingArea("KM",latitude, longitude,2, 20);
    		 if(closestParkingList!=null && closestParkingList.size()>0) {
	    		 List<ParkingLocationDto> list = closestParkingList.stream()
	    				 .map(objectArray->new ParkingLocationDto(objectArray[0],objectArray[1],objectArray[2].toString(),objectArray[3].toString())).collect(Collectors.toList());
	    		  return new EsResponse<>(IConstants.RESPONSE_STATUS_OK,list,  this.ENV.getProperty("parking.location.found"));
    		 }else {
    			 return new EsResponse<>(IConstants.RESPONSE_STATUS_OK,  this.ENV.getProperty("parking.location.not.found"));
    		 }
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internalerror"));
        }
}
    public EsResponse<List<ParkingReviewDTO>> findAllReviews( long locId) {
       try{
    	List<ParkingReviews > parkingReviewsVos  = this.parkingReviewsRepository.findAllByLocationId(locId);
    	
    	   List<ParkingReviewDTO> ParkingReviewDtos = parkingReviewsVos.stream()
    			  .map((parkingReviewsVo) -> {
    				  ParkingReviewDTO  parkReviewDto =  modelMapper.map(parkingReviewsVo , ParkingReviewDTO.class);
    				  parkReviewDto.setReviewId(parkingReviewsVo.getId());
    				  parkReviewDto.setReviewrName(parkingReviewsVo.getUser().getUserProfile().getFirstName());
    
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
