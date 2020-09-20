package com.vpark.vparkservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonusDTO {
	
	private String bonusCode;
	private String desc;
	
	@JsonInclude(Include.NON_NULL)
	private Double bonusAmt;
	
  @JsonInclude(Include.NON_NULL)
	private Double bonusPer;
	

}
