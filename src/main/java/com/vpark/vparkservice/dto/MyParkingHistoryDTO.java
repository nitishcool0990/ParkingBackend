package com.vpark.vparkservice.dto;

import java.time.LocalDate;
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
	
	private long bookingId;
	
	private String parkName ;
	
	private String status;
	
	private String amt ;
	
	private String inTime ;
	
	private String outTime ;
	
	private String vehicleType ;
	
	private String vehicleNo ;
	
	private LocalDateTime bookingDate; 
	
	private LocalDate StartDate; 
	
	private LocalDate endDate; 
	
	

}
