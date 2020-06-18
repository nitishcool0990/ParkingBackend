package com.vpark.vparkservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import com.vpark.vparkservice.constants.IConstants;
import lombok.Data;


@Data
@Entity
@DynamicUpdate
@Table(name = "PARKING_TRANSACTION_HISTORY")
public class ParkTransHistory extends Savable{


	@Column(name = "USER_ID")
	private long user;
	
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
