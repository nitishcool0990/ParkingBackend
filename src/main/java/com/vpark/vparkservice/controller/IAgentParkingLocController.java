package com.vpark.vparkservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.AgentParkingLocationDTO;
import com.vpark.vparkservice.dto.BookedVehicleDetailsDTO;
import com.vpark.vparkservice.dto.CheckInAndCheckOutDTO;
import com.vpark.vparkservice.dto.ParkingChargesDTO;
import com.vpark.vparkservice.dto.ParkingDetailsDTO;
import com.vpark.vparkservice.dto.ParkingTypeDTO;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.RequestAttribute;

@RequestMapping("agent/")
public interface IAgentParkingLocController {

	 @PostMapping( IConstants.VERSION_1 + "/create"  )
	  ResponseEntity<EsResponse<?>> createNewParkingLocation(@RequestParam(value="images") MultipartFile[]  images ,   @RequestParam(value="parkingLoc")String parkingLoc , @RequestAttribute("Id")  long userId);
	
	 @GetMapping(IConstants.VERSION_1 )
	 ResponseEntity<EsResponse<List<AgentParkingLocationDTO>>> findAllParkingLocationById(@RequestAttribute("Id")  long userId);
	 
	 @GetMapping(IConstants.VERSION_1 +"/details/{locId}")
	 ResponseEntity<EsResponse<List<ParkingDetailsDTO>>> findParkingDetailsById(@PathVariable long locId  );
	 
	 @GetMapping(IConstants.VERSION_1 +"/parkingstatus/{id}")
	 ResponseEntity<EsResponse<List<ParkingDetailsDTO>>> findParkingStatusById(@PathVariable long locId  );
	 
	 @GetMapping(IConstants.VERSION_1 +"/upcomingVehicles/{locId}")
	 ResponseEntity<EsResponse<List<BookedVehicleDetailsDTO>>> findUpcomingVehicles(@PathVariable long locId  );
	 
	 @GetMapping(IConstants.VERSION_1 +"/ParkedVehicles/{locId}")
	 ResponseEntity<EsResponse<List<BookedVehicleDetailsDTO>>> findParkedVehicles(@PathVariable long locId  );
	 
	 @PostMapping(IConstants.VERSION_1 +"/checkIn")
	 ResponseEntity<EsResponse<?>> checkInVehicle(@RequestBody CheckInAndCheckOutDTO checkInDto );
	 
	 @PostMapping(IConstants.VERSION_1 +"/checkOut")
	 ResponseEntity<EsResponse<?>> checkOutVehicle(@RequestBody CheckInAndCheckOutDTO checkOutDto  );
	 
	 @DeleteMapping(IConstants.VERSION_1 + "/delete/{id}")
	 ResponseEntity<EsResponse<?>> deleteLocation(@PathVariable long id);
	 
	  @GetMapping(IConstants.VERSION_1 + "/parkType")
	  ResponseEntity<EsResponse<List<ParkingTypeDTO>>> findAllParkingType();
	  
	  @PutMapping(IConstants.VERSION_1 + "/update")
	  ResponseEntity<EsResponse<?>> updateParkingLocation(@RequestParam(value="images") MultipartFile[]  images ,   @RequestParam(value="parkingLoc")String parkingLoc , @RequestAttribute("Id")  long userId);

}
