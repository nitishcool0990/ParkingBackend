package com.vpark.vparkservice.dto;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by kalana.w on 5/22/2020.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
	
    private long id;
    
    private String vehicleNo;
    
    private long vehicleTypeId ;
    
    private String vehicleType ;
 
    private String  isDefault ;
}
