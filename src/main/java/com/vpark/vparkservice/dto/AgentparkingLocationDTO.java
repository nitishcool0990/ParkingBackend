package com.vpark.vparkservice.dto;

import java.util.List;

import com.vpark.vparkservice.entity.ParkingDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentparkingLocationDTO {
	
	private String parkName ;
	
	private String parkAddress ;
	
	private String latitude ;
	
	private String longitude ;
	
	private String parkRegion ;
	
	private String openTime ;
	
	private String closeTime ;
	
	private long parkingTypeId ;
	
	private List<ParkingDetails> parkingDetails ;

}
