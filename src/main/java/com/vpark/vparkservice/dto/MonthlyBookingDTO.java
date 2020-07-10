package com.vpark.vparkservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonthlyBookingDTO {

	private String  fromDate ;
	private long parkLocId ;
	private long vehicleId ;
	private double amt ;
}
