package com.vpark.vparkservice.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InitBookingDTO {

	private LocalTime   fromDate ;
	
	private LocalTime   toDate ;
	
	private long parkLocId ;
	
	private long vehicleId ;
	
	private double amt ;
	
	private String   fromTime ;
	
	private String  toTime ;
	
	private String bonusCode ;
	

	
}
