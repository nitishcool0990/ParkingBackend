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
@Table(name = "BONUS_CODES_USERS")
public class BonusCodeUsers extends Savable{

	@Column(name = "BONUS_CODE_ID"  , nullable = false)
	private long bonusCodeId ;
	
	@Column(name = "USER_ID"  , nullable = false)
	private long userId ;
	
	@Column(name = "PARKED_BOOK_ID"  , nullable = false)
	private long parkedBookId ;
	
	 @Column(name = "STATUS", nullable = false, length = 10)
	    @Enumerated(EnumType.STRING)
	    private IConstants.Status status = IConstants.Status.INACTIVE;
	
	
	
}
