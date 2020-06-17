package com.vpark.vparkservice.dto;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PaymetGateWayDTO {

	private String paymentGW;
	private Double totelAmt;
	
	public PaymetGateWayDTO(String paymentGateWayName, Double totelAmount) {
		super();
		this.paymentGW = paymentGateWayName;
		this.totelAmt = totelAmount;
	}
	
}
