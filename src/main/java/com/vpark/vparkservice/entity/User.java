package com.vpark.vparkservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vpark.vparkservice.constants.IConstants;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by kalana.w on 5/17/2020.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "USERS")
@JsonIgnoreProperties(value = { "password" }, allowSetters = true)
public class User extends Savable
{
	@Column(name = "MOBILE_NO", unique = true, nullable = false)
	private String username;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "LAST_LOGIN_DATE")
	private LocalDateTime lastLoginDate;

	@Column(name = "USER_STATUS", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private IConstants.UserStatus userStatus = IConstants.UserStatus.INACTIVE;

	@Column(name = "USER_TYPE", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private IConstants.UserType userType;
}
