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
@Table(name = "CASHFREE_TRANSACTION_HISTORY")
public class CashFreeTransHistory extends Savable {

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private User user;
	
	@Column(name = "ORDER_ID")
	private long orderId; 
	
	@Column(name = "ORDER_AMT")
    private double orderAmount ;
	
	@Column(name = "ORDER_NOTE")
    private String orderNote ;
	
	@Column(name = "SOURCE")
    private String source ;
	
	@Column(name = "CUSTOMER_NAME")
    private String customerName;
	
	@Column(name = "CUSTOMER_EMAIL")
    private String customerEmail ;
	
	@Column(name = "CUSTOMER_PHONE")
    private String customerPhone ;
	
	@Column(name = "NOTIFY_URL")
    private String notifyUrl ;
	
	@Column(name = "PAYMENT_MODES")
    private String paymentModes ;
	
	@Column(name = "CARD_NUMBER")
    private String card_number ; 
	
	@Column(name = "CARD_HOLDER")
    private String  card_holder ;
	
	@Column(name = "CARD_CVV")
    private String card_cvv ;
	
	@Column(name = "CARD_EXPIRY_MONTH")
    private String card_expiryMonth ;
    
	@Column(name = "CARD_EXPIRY_YEAR")
    private String  card_expiryYear ;
	
}
