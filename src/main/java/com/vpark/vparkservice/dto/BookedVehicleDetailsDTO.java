package com.vpark.vparkservice.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookedVehicleDetailsDTO {

	public String vehicleName ;
	public String userName ;
	public String OutTime ;
	public String arrivalTime;
	public String isArrived;
	
	
}
