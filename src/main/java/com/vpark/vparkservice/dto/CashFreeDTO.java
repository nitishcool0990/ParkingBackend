package com.vpark.vparkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashFreeDTO {
	 
      private long orderId; 
      private long parkingId;
      private double orderAmount ;
      private String orderNote ;
      private String source ;
      private String customerName;
      private String customerEmail ;
      private String customerPhone ;
      private String notifyUrl ;
      private String paymentModes ;
      private String card_number ; 
      private String  card_holder ;
      private String card_cvv ;
      private String card_expiryMonth ;
      private String  card_expiryYear ;
     
      
      
}
