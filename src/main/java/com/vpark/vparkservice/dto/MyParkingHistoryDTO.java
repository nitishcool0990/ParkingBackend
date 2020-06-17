package com.vpark.vparkservice.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyParkingHistoryDTO {
	
	private long id;
	
	private String parkName ;
	
	private String Status;
	
	private String amt ;
	
	private String InTime ;
	
	private String OutTime ;
	
	private String totalHours;
	
	private String vehicleType ;
	
	private LocalDateTime bookingDate; 
	
	

}
