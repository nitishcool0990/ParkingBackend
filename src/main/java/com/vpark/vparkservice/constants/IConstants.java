package com.vpark.vparkservice.constants;

/**
 * Created by kalana.w on 5/17/2020.
 */
public interface IConstants {
    int RESPONSE_STATUS_OK = 1;
    int RESPONSE_STATUS_ERROR = -1;

    String VERSION_1 = "v1";

    enum UserStatus {
        ACTIVE, INACTIVE, BLOCKED
    }

    enum UserType {
        USER, SUPER_USER, ADMIN
    }
}
