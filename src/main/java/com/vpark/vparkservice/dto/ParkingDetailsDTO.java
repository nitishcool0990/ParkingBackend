package com.vpark.vparkservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingDetailsDTO {
	
	private long vehicleTypeId ;
	
	private double hourlyRate ; 
	
	private double monthlyRate ;
	
	private int capacity ;
	
    private String vehicleName ;

}
