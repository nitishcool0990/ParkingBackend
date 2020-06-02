package com.vpark.vparkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by kalana.w on 5/23/2020.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
}
