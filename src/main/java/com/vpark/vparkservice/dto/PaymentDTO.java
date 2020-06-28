package com.vpark.vparkservice.dto;


import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PaymentDTO {
	
	private long  parkingLocId;
	private Double payableAmt;
	private List<PaymetGateWayDTO> paymentGateWay = new ArrayList<PaymetGateWayDTO>();
	
	
	

}
