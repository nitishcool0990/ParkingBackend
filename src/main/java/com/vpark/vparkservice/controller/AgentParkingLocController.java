package com.vpark.vparkservice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.Gson;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.AgentParkingLocationDTO;
import com.vpark.vparkservice.dto.BookedVehicleDetailsDTO;
import com.vpark.vparkservice.dto.CheckInAndCheckOutDTO;
import com.vpark.vparkservice.dto.ParkingDetailsDTO;
import com.vpark.vparkservice.dto.ParkingTypeDTO;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.service.AgentParkingLocService;

@RestController
public class AgentParkingLocController implements  IAgentParkingLocController {

	@Autowired
	private AgentParkingLocService   agentParkingService ;
	
	@Autowired
	 private Environment ENV;
	
	
	    public ResponseEntity<EsResponse<?>> createNewParkingLocation( @RequestParam(value="images") MultipartFile[]  images ,  @RequestParam(value="parkingLoc") String parkingLoc  ,long userId){
	    	AgentParkingLocationDTO  parkingLocationDto = null;
	    	if(null != parkingLoc){
				Gson gson=new Gson();
				parkingLocationDto = gson.fromJson(parkingLoc, AgentParkingLocationDTO.class);
			}
	    	return ResponseEntity.ok(agentParkingService.createNewLocation(images , parkingLocationDto , userId ));	    	
	    }


		@Override
		public ResponseEntity<EsResponse<List<AgentParkingLocationDTO>>> findAllParkingLocationById(long userId) {
		    if (userId <= 0) {
	            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
	        }
	        return ResponseEntity.ok(this.agentParkingService.findAllParkingLocationByUserId(userId));
		}
   
		
		@Override
		public ResponseEntity<EsResponse<List<ParkingDetailsDTO>>> findParkingStatusById(long locId) {
			 if ( locId < 0) {
		            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
		        }
		        return ResponseEntity.ok(this.agentParkingService.findParkingDetailsById( locId ));
			
		}
		
	public ResponseEntity<EsResponse<List<ParkingDetailsDTO>>> findParkingDetailsById(@PathVariable long locId ){
		 if ( locId < 0) {
	            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
	        }
	        return ResponseEntity.ok(this.agentParkingService.findParkingDetailsById( locId ));
	}
		
		
	 @Override
	    public ResponseEntity<EsResponse<?>> deleteLocation(@PathVariable long id) {
	        if (id <= 0) {
	            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
	        }
	        return ResponseEntity.ok(this.agentParkingService.deleteParkingLocation(id));
	    }
		
		
		 public ResponseEntity<EsResponse<List<ParkingTypeDTO>>> findAllParkingType(){
	    	   return ResponseEntity.ok(this.agentParkingService.findAllParkingType());
	    }


		@Override
		public ResponseEntity<EsResponse<?>> updateParkingLocation(@RequestParam(value="images") MultipartFile[]  images ,  @RequestParam(value="parkingLoc") String parkingLoc  ,long userId) {
			  if (userId<= 0) {
		            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
		        }
			  AgentParkingLocationDTO  parkingLocationDto = null;
		    	if(null != parkingLoc){
					Gson gson=new Gson();
					parkingLocationDto = gson.fromJson(parkingLoc, AgentParkingLocationDTO.class);
				}
		        return ResponseEntity.ok(this.agentParkingService.updateParkingLocation(images , parkingLocationDto , userId ));
			
		}


		@Override
		public ResponseEntity<EsResponse<List<BookedVehicleDetailsDTO>>> findUpcomingVehicles(long locId) {
			 if ( locId < 0) {
		            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
		        }
		        return ResponseEntity.ok(this.agentParkingService.findUpcomingVehicleDetails( locId ));
		}


		@Override
		public ResponseEntity<EsResponse<?>> checkInVehicle(CheckInAndCheckOutDTO checkInDto) {
			 if ( checkInDto.getLocationId() < 0) {
		            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
		        }
		        return ResponseEntity.ok(this.agentParkingService.checkInVehicle(checkInDto));
		
		}


		@Override
		public ResponseEntity<EsResponse<?>> checkOutVehicle(CheckInAndCheckOutDTO checkOutDto) {
			 if ( checkOutDto.getLocationId() < 0) {
		            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
		        }
		        return ResponseEntity.ok(this.agentParkingService.checkOutVehicle(checkOutDto));
		}


		@Override
		public ResponseEntity<EsResponse<List<BookedVehicleDetailsDTO>>> findParkedVehicles(long locId) {
			 if ( locId < 0) {
		            return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
		        }
		        return ResponseEntity.ok(this.agentParkingService.findParkedVehicleDetails( locId ));
		}


		

		
}
