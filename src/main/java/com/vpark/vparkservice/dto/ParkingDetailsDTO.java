package com.vpark.vparkservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vpark.vparkservice.constants.IConstants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingDetailsDTO {

	private int capacity ;
	
    private String vehicleName ;
    
	private long vehicleTypeId ;
	
	private  IConstants.ChargesType chargesType ;
	
	private double monthlyRate ;
	
	private double nightCharges ;
	
	private double maxLimit ;
	
	private List<ParkingChargesDTO> parkingChargesDtos ; 
}
