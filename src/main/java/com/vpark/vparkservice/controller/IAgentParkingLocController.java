package com.vpark.vparkservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.AgentparkingLocationDTO;
import com.vpark.vparkservice.dto.ParkingTypeDTO;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.RequestAttribute;

@RequestMapping("agent/")
public interface IAgentParkingLocController {

	
	 @GetMapping(IConstants.VERSION_1 )
	 ResponseEntity<EsResponse<?>> findAllParkingLocationById(@RequestAttribute("Id")  long userId);

	  @PostMapping(IConstants.VERSION_1 + "/create")
	  ResponseEntity<EsResponse<?>> createNewParkingLocation(@RequestBody AgentparkingLocationDTO parkingLocationDto  ,@RequestAttribute("Id")  long userId);
	    
	  @GetMapping(IConstants.VERSION_1 + "/parkType")
	  ResponseEntity<EsResponse<List<ParkingTypeDTO>>> findAllParkingType();

}
