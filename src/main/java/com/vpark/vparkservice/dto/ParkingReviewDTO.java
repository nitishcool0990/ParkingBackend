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
public class ParkingReviewDTO {

	private long reviewId ;
	private int rating ;
	private String comment ;
	private long parkingLocId ;
	private String reviewrName ;
	private String reply ;
	private LocalDateTime createDate;
	
}
