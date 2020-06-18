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
@Table(name = "BONUS_CODES")
public class BonusCodes extends Savable{

	@Column(name = "CODE"  , nullable = false)
	private double code ;
	
	@Column(name = "DISCOUNT_PERC")
	private String discountPerc ;
	
	@Column(name = "DISCOUNT_AMT")
	private String discountAmt ;
	
	@Column(name = "IS_BOOKING_PAYMENT_CODE")
	private boolean isbookingPaymentCode ;
	
	@Column(name = "IS_PARKING_PAYMENT_CODE")
	private boolean isparkingPaymentCode ;
	
	@Column(name = "MAX_USED_COUNT"  , nullable = false)
	private Integer maxUsedCount ;
	
	@Column(name = "REMARKS")
	private String remarks ;
	
	@Column(name = "STATUS"  ,nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private IConstants.Status status = IConstants.Status.INACTIVE ;


}
