package com.vpark.vparkservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by kalana.w on 5/23/2020.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto {
	
    private long id;
    
    private String firstName;
    
    private String lastName;
    
    private String mobileNo;
    
    private String email;
    
    private String userRole ;

}
