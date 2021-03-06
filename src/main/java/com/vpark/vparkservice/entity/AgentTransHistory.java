package com.vpark.vparkservice.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import com.vpark.vparkservice.constants.IConstants;

import lombok.Data;

@Data
@Entity
@DynamicUpdate
@Table(name = "AGENT_TRANSACTION_HISTORY")
public class AgentTransHistory extends Savable{


	@Column(name = "AGENT_ID")
	private long agentId;
	
	@Column(name = "USER_ID")
	private long userId;
	
	@Column(name = "LOCATION_ID")
	private long  locId ;
	
	@Column(name = "BOOKING_ID")
	private long  bookingId ;

	@Column(name = "AMT")
	private double amt ;
	
	@Column(name = "CR_DR")
	private String crdr ;
	
	@Column(name = "CHIP_TYPE")
	private String chipType ;
	
	@Column(name = "REMARKS")
     private String remarks ;
	
	@Column(name = "status", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private IConstants.TransStatus status = IConstants.TransStatus.APPROVED;
	
}
