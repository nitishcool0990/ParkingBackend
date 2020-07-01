package com.vpark.vparkservice.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.AgentParkingLocationDTO;
import com.vpark.vparkservice.dto.BookedVehicleDetailsDTO;
import com.vpark.vparkservice.dto.CheckInAndCheckOutDTO;
import com.vpark.vparkservice.dto.ParkingDetailsDTO;
import com.vpark.vparkservice.dto.ParkingTypeDTO;
import com.vpark.vparkservice.entity.ParkBookingHistory;
import com.vpark.vparkservice.entity.ParkedVehicleCount;
import com.vpark.vparkservice.entity.ParkingDetails;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.entity.ParkingType;
import com.vpark.vparkservice.entity.User;
import com.vpark.vparkservice.entity.Vehicle;
import com.vpark.vparkservice.mapper.ParkingLocMapper;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IParkBookingHistoryRepository;
import com.vpark.vparkservice.repository.IParkedVehicleCountRepository;
import com.vpark.vparkservice.repository.IParkingDetailsRepository;
import com.vpark.vparkservice.repository.IParkingLocationRepository;
import com.vpark.vparkservice.repository.IParkingTypeRepository;
import com.vpark.vparkservice.repository.IUserRepository;

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
	  
	  @Autowired
	  private IParkBookingHistoryRepository   parkBookingHistoryRepository ;
	  
	  @Autowired
	   private IUserRepository userRepository;
	  
	  @Autowired
	  private IParkedVehicleCountRepository parkedVehicleCountRepository ;

	  
	  
	  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	 public EsResponse<ParkingLocation> createNewLocation( MultipartFile[]  parkingImages , AgentParkingLocationDTO parkingLocationDto  ,   long userId) {
	        try {
	        	ParkingLocation parkingLocVo  = modelMapper.map(parkingLocationDto, ParkingLocation.class) ;
	        	parkingLocMapper.mapAgentParkingLocToVo(parkingLocationDto, parkingLocVo);
	        	
	        	User user = new User() ;
	        	user.setId(userId);
	        	parkingLocVo.setUser(user);
	        	
	        	for (MultipartFile image  : parkingImages ){
	        		byte[] photoBytes   = image.getBytes();
	        		parkingLocVo.setPhoto(photoBytes);
	        	}
	        	ParkingLocation savedParkingLocVo   = this.parkingLocationRepository.save(parkingLocVo);
	        	List<ParkedVehicleCount>  parkedVehicleVos = parkingLocMapper.createParkedVehicleVo(parkingLocationDto , savedParkingLocVo.getId());
	        	
	        	this.parkedVehicleCountRepository.saveAll(parkedVehicleVos);
	
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
	      				parkLocDto.setImage(parkingLocVo.getPhoto());
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
	 
	
	
	
	public EsResponse<?> updateParkingLocation( MultipartFile[]  parkingImages , AgentParkingLocationDTO parkingLocationDto  ,   long userId) {
        try {
        	
        	Optional<ParkingLocation> parkingLocVo = this.parkingLocationRepository.findById(parkingLocationDto.getParkingLocid());
        	if(parkingLocVo.isPresent()){
        	ParkingLocation updateParkingLocVo  = modelMapper.map(parkingLocationDto, ParkingLocation.class) ;
        	
        	if(null !=parkingLocationDto.getParkingDetailsDtos() && parkingLocationDto.getParkingDetailsDtos().size()>0 )
        	    parkingLocMapper.mapAgentParkingLocToVo(parkingLocationDto, updateParkingLocVo);
        	else{
        		ParkingType parkingTypeVo = new ParkingType() ;
            	parkingTypeVo.setId(parkingLocationDto.getParkingTypeId());
            	updateParkingLocVo.setParkingType(parkingTypeVo);
        	}
        	
        	User user = new User() ;
        	user.setId(userId);
        	updateParkingLocVo.setUser(user);
        	
        	for (MultipartFile image  : parkingImages ){
        		byte[] photoBytes   = image.getBytes();
        		updateParkingLocVo.setPhoto(photoBytes);
        	}
        	updateParkingLocVo.setId(parkingLocationDto.getParkingLocid());
        	
        	this.parkingLocationRepository.save(updateParkingLocVo);
        	
        	 return new EsResponse<>(IConstants.RESPONSE_STATUS_OK  , this.ENV.getProperty("parking.location.update.success"));
        	
        	}

        	 return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR  , this.ENV.getProperty("parking.location.not.found")); 
      
        } catch (Exception e) {
            e.printStackTrace();
            return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("parking.location.update.failed"));
        }
  }
 
	
	
	public EsResponse<List<BookedVehicleDetailsDTO>> findUpcomingVehicleDetails(long locId) {
		try {
			List<ParkBookingHistory> parkBookingHistoryVos = this.parkBookingHistoryRepository.findByParkingLocationId(locId);

			List<BookedVehicleDetailsDTO> bookedVehicleDetailsDTOs = parkBookingHistoryVos.stream()
					.map((parkBookingHistoryVo) -> {
						BookedVehicleDetailsDTO bookedVehicleDetailsDto = modelMapper.map(parkBookingHistoryVo,
								BookedVehicleDetailsDTO.class);
						bookedVehicleDetailsDto.setArrivalTime(parkBookingHistoryVo.getInTime());
						bookedVehicleDetailsDto.setBookingId(parkBookingHistoryVo.getId());

						Optional<User> userVo = userRepository.findById(parkBookingHistoryVo.getUserId());
						bookedVehicleDetailsDto.setMobileNo(userVo.get().getMobileNo());
						List<Vehicle> vehiclesVos = userVo.get().getVehicles();

						for (Vehicle vehicleVo : vehiclesVos) {

							if (parkBookingHistoryVo.getVehicleId() == vehicleVo.getId()) {

								bookedVehicleDetailsDto.setVehicleName(vehicleVo.getVehicleType().getVehicleName());
								bookedVehicleDetailsDto.setVehicleNo(vehicleVo.getVehicleNo());
								bookedVehicleDetailsDto.setVehicleTypeId(vehicleVo.getVehicleType().getId());
							}
						}
						return bookedVehicleDetailsDto;
					}).collect(Collectors.toList());

			return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, bookedVehicleDetailsDTOs, this.ENV.getProperty("booking.upcoming.found.success"));

		} catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("booking.upcoming.found.failed"));
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public EsResponse<?> checkInVehicle(CheckInAndCheckOutDTO  checkInDto){
		try{
			
			if(checkInDto.getBookingId() >0 ){
				Optional<ParkBookingHistory> parkBookingHistoryVo =  this.parkBookingHistoryRepository.findById(checkInDto.getBookingId() );
				if(parkBookingHistoryVo.isPresent())
				{
					parkBookingHistoryVo.get().setStatus(IConstants.ParkingStatus.PARKED);
					this.parkBookingHistoryRepository.save(parkBookingHistoryVo.get());
				 }
			}
			
			ParkedVehicleCount parkedVehicleCountVo= 	this.parkedVehicleCountRepository.findByParkingLocationIdAndVehicleTypeId(checkInDto.getLocationId() , checkInDto.getVehicleTypeId());
			if(null !=parkedVehicleCountVo){
				parkedVehicleCountVo.setTotalCount(parkedVehicleCountVo.getTotalCount()-1);
				parkedVehicleCountVo.setTotalOccupied(parkedVehicleCountVo.getTotalOccupied() + 1);
			}
			
			this.parkedVehicleCountRepository.save(parkedVehicleCountVo);
			
			 return  new EsResponse<>(IConstants.RESPONSE_STATUS_OK,  this.ENV.getProperty("vehicle.checkin.success"));
		}
		catch (Exception e) {
	        e.printStackTrace();
	        return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.checkin.failed"));
	       }
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public EsResponse<?> checkOutVehicle(CheckInAndCheckOutDTO  checkOutDto){
		try{
			
			if(checkOutDto.getBookingId() >0 ){
				Optional<ParkBookingHistory> parkBookingHistoryVo =  this.parkBookingHistoryRepository.findById(checkOutDto.getBookingId() );
				if(parkBookingHistoryVo.isPresent())
				{
					parkBookingHistoryVo.get().setStatus(IConstants.ParkingStatus.COMPLETED);
					this.parkBookingHistoryRepository.save(parkBookingHistoryVo.get());
				}
				
			}
			ParkedVehicleCount parkedVehicleCountVo= 	this.parkedVehicleCountRepository.findByParkingLocationIdAndVehicleTypeId(checkOutDto.getLocationId() , checkOutDto.getVehicleTypeId());
			if(null !=parkedVehicleCountVo){
				parkedVehicleCountVo.setTotalCount(parkedVehicleCountVo.getTotalCount()+1);
				parkedVehicleCountVo.setTotalOccupied(parkedVehicleCountVo.getTotalOccupied() - 1);
			}
			
			this.parkedVehicleCountRepository.save(parkedVehicleCountVo);
			
			 return  new EsResponse<>(IConstants.RESPONSE_STATUS_OK,  this.ENV.getProperty("vehicle.checkout.success"));
		}
		catch (Exception e) {
	        e.printStackTrace();
	        return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("vehicle.checkout.failed"));
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
