package com.vpark.vparkservice.dto;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by kalana.w on 5/23/2020.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private long id;
    private String mobileNo;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private LocalDateTime lastLoginDate;
    private IConstants.UserStatus userStatus;
    private IConstants.UserType userType;
    private List<Vehicle> vehicles;
}
