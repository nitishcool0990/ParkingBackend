package com.vpark.vparkservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by kalana.w on 5/22/2020.
 */
@Data
@AllArgsConstructor
public class VehicleDto {
    private long id;
    private String vehicleNo;
    private String vehicleType;
}
