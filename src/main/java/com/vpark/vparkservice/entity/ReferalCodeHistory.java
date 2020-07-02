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
@Table(name = "REFERAL_CODE_HISTORY")
public class ReferalCodeHistory extends Savable{

	 @Column(name = "REFERAL_USER_ID")
	 private long referalUserId;
	 
	 @Column(name = "REFERAL_CODE"  , nullable = false)
	 private String referalCode ;
	 
	 @Column(name = "EARN_AMT")
	 private Double earnAmt = 0.0;
	 
	
	 @Column(name = "REFEREE_USER_ID" , nullable = false , unique = true)
	 private long refereeUserId  = -1;
	 
	 @Column(name = "STATUS"  ,nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private IConstants.ReferalStatus status = IConstants.ReferalStatus.PENDING ;
	 
	
}
