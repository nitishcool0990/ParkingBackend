package com.vpark.vparkservice.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingReviewDTO {

	private long reviewId ;
	private int rating ;
	private String comment ;
	private long parkingLocId ;
	private String reviewrName ;
	private LocalDateTime createDate;
	
}
