package com.vpark.vparkservice.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PaymentDTO {
	
	private long  parkingId;
	private Double payableAmt;
	private List<PaymetGateWayDTO> paymentGateWay = new ArrayList<PaymetGateWayDTO>();
	
	
	

}
