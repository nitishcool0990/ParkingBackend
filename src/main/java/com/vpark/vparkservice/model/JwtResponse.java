package com.vpark.vparkservice.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;
    private final String userRole;

    public JwtResponse(String jwtToken,String userRole) {
        this.jwtToken = jwtToken;
        this.userRole=userRole;
    }

    public String getToken() {
        return this.jwtToken;
    }
    
    
    
    
}