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
@Table(name = "SMS_TEXT")
public class SmsText  extends Savable{

	
	@Column(name = "CODE"  , nullable = false)
	private String code ;
	
	@Column(name = "SMS_BODY"  , nullable = false)
	private String smsBody ;
	
	@Column(name = "STATUS", nullable = false )
    @Enumerated(EnumType.STRING)
    private IConstants.Status status = IConstants.Status.ACTIVE;
	
	
	
	
	
	
}
