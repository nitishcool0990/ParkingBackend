package com.vpark.vparkservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PaymetGateWayDTO {

	private String paymentGW;
	private Double totalAmt;
	
	public PaymetGateWayDTO(String paymentGateWayName, Double totalAmount) {
		super();
		this.paymentGW = paymentGateWayName;
		this.totalAmt = totalAmount;
	}
	
}
