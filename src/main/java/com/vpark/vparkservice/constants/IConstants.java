package com.vpark.vparkservice.constants;

/**
 * Created by kalana.w on 5/17/2020.
 */
public interface IConstants {
    int RESPONSE_STATUS_OK = 1;
    int RESPONSE_STATUS_ERROR = -1;
    int RESPONSE_OPEN_PROFILE=2;
    int RESPONSE_ADD_VEHICLE=3;

    String VERSION_1 = "v1";
    public static final int OTP_LEN=4;
    public static final String CONTENT = "content";
	public static final String LINK = "link";
	public static final String UTF_8 = "UTF-8";
	public static final String USER_AGENT = "User-Agent";
	public static final String POST = "POST";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_VALUE = "application/json";
	public static final String SHA_256 = "SHA-256";
	public static final String HEADER_VALUE = "Accept=application/json";

	


    enum Status {
        ACTIVE, INACTIVE, BLOCKED
    }

    enum UserType {
        USER, AGENT, ADMIN
    }
    
    enum Default {
        TRUE, FALSE 
    }
    
    

    
}
