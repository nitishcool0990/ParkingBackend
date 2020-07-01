package com.vpark.vparkservice.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookedVehicleDetailsDTO {

	public long bookingId ;
	
	public long vehicleTypeId ;
	
	public String vehicleName ;
	
	public String vehicleNo ;
	
	public String mobileNo ;
	
	public String OutTime ;
	
	public String arrivalTime;
	
	public String isArrived;
	
	
}
