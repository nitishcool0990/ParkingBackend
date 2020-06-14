package com.vpark.vparkservice.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentParkingLocationDTO {
	
	 private long  parkingLocid ;
	
	private String parkName ;
	
	private String parkAddress ;
	
	private String latitude ;
	
	private String longitude ;
	
	private String parkRegion ;
	
	private String openTime ;
	
	private String closeTime ;
	
	private long parkingTypeId ;
	
	private List<ParkingDetailsDTO> parkingDetailsDtos ;

}
