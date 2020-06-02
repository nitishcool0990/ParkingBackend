package com.vpark.vparkservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vpark.vparkservice.constants.IConstants;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by kalana.w on 5/17/2020.
 */
@Data
@Entity
@DynamicUpdate
@Table(name = "USERS")
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)
public class User extends Savable {

    @Column(name = "MOBILE_NO", unique = true, nullable = false)
    private String mobileNo;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "LAST_LOGIN_DATE")
    private LocalDateTime lastLoginDate;

    @Column(name = "USER_STATUS", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private IConstants.Status status = IConstants.Status.INACTIVE;

    @Column(name = "USER_TYPE", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private IConstants.UserType userType;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_PROFILE", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_USER_USER_PROFILE"))
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Vehicle> vehicles;
}
