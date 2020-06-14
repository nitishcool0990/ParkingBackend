package com.vpark.vparkservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.FavouriteParkingDTO;
import com.vpark.vparkservice.dto.ParkingReviewDTO;
import com.vpark.vparkservice.entity.FavouriteParking;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IFavouriteParkingRepository;
import com.vpark.vparkservice.repository.IParkingLocationRepository;

@Service
public class FavouriteParkingLocService {

	 @Autowired
	 private Environment ENV;
	 
	 @Autowired
	 private ModelMapper modelMapper;
	 
	 @Autowired
	 private  IFavouriteParkingRepository  favParkingRepository ;
	 
	 @Autowired
	 private IParkingLocationRepository parkingLocationRepository;
	 
	 
	 public  EsResponse<?> addFavouriteParkingLocation(long locId , long userId ){
		 try{
			 //Optional<FavouriteParking> getFavParkingVo    =  this.favParkingRepository.findByLocationIdAndUserId(locId , userId) ;
		   //  if(!getFavParkingVo.isPresent()){
		    	 
		    	 FavouriteParking favParkingVo = new FavouriteParking() ;
		    	 ParkingLocation  parkLocVo = new ParkingLocation();
		    	 User user = new User() ;
		    	 
		    	 parkLocVo.setId(locId);
		         user.setId(userId);
		    	 
		         favParkingVo.setUser(user);
		    	 favParkingVo.setParkingLocation(parkLocVo);
		    	
		    	 this.favParkingRepository.save(favParkingVo);
		    	 return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("parking.fav.save.success"));
		     //}
		     //else
		    	 //return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.fav.exist")); 
			 
		  } catch (Exception e) {
	            e.printStackTrace();
	            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.fav.save.failed"));
	        }
		
	 }
	 
	 
	 
	 public  EsResponse<List<FavouriteParkingDTO>> findAllFavouriteParkingLocation (long userId ){
		try {
			List<FavouriteParking> favouriteParkingVos = this.favParkingRepository.findAllByUserId(userId);

			List<FavouriteParkingDTO> FavoriteParkingDtos = favouriteParkingVos.stream()
					.map((favParkingVo) -> {
				FavouriteParkingDTO FavoriteParkingDto = modelMapper.map(favParkingVo, FavouriteParkingDTO.class);
				FavoriteParkingDto.setCloseTime(favParkingVo.getParkingLocation().getCloseTime());
				FavoriteParkingDto.setOpentime(favParkingVo.getParkingLocation().getOpenTime());
				FavoriteParkingDto.setParkingName(favParkingVo.getParkingLocation().getParkName());
				FavoriteParkingDto.setLocId(favParkingVo.getParkingLocation().getId());

				return FavoriteParkingDto;
			}).collect(Collectors.toList());

			return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, FavoriteParkingDtos,this.ENV.getProperty("parking.review.search.success"));
		}catch (Exception e) {
		    e.printStackTrace();
		    return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.review.search.failed"));
		       }
	 }
	 
	 
	 
	public EsResponse<?> deleteFavouriteLocation(long favId) {
		try {
			Optional<FavouriteParking> favParkingVo = this.favParkingRepository.findById(favId);

			 if(favParkingVo.isPresent())
				this.favParkingRepository.deleteById(favId);

			return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("parking.fav.delete.success"));
		} catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.fav.delete.failed"));
		}
	}
	 
}
