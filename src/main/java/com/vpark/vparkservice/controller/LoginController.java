package com.vpark.vparkservice.controller;

import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.JwtResponse;
import com.vpark.vparkservice.service.JwtUserDetailsService;
import com.vpark.vparkservice.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kalana.w on 5/21/2020.
 */
@RestController
public class LoginController implements ILoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Override
    public ResponseEntity<EsResponse<JwtResponse>> login(@RequestHeader String userName, @RequestHeader String password) throws Exception {
        authenticate(userName, password);
        final UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(userName);
        final String token = this.jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new EsResponse<>(IConstants.RESPONSE_STATUS_OK, new JwtResponse(token), "authentication success"));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            Authentication authenticate = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println(authenticate.isAuthenticated());
            System.out.println(authenticate.getName());
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
