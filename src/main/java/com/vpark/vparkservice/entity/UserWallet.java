package com.vpark.vparkservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

@Data
@Entity
@DynamicUpdate
@Table(name = "USER_WALLET")
public class UserWallet extends Savable {
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private User user;
	
	@Column(name = "DEPOSIT_AMT")
	private double deposit =0;
	
	@Column(name = "REAL_AMT")
	private double real =0;
	
	@Column(name = "BONUS_AMT")
	private double bonus =0;
	

	


	

}
