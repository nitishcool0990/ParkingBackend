package com.vpark.vparkservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingDetailsDTO {
	
	private long vehicleTypeId ;
	
	private double hourlyRate ; 
	
	private double monthlyRate ;
	
	private int capacity ;
	
    private String vehicleName ;

}
