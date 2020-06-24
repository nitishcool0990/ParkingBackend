package com.vpark.vparkservice.config;

import com.vpark.vparkservice.dto.SpringSecurityUserDetails;
import com.vpark.vparkservice.service.JwtUserDetailsService;
import com.vpark.vparkservice.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }
        
        if(jwtToken==null) {
        	String referrer = request.getHeader("referer");
        	if(referrer!=null && referrer.contains("cashfree")) {
        		Map<String, String[]> map =request.getParameterMap();
                for (Map.Entry<String,String[]> entry : map.entrySet())  
                    System.out.println("Key = " + entry.getKey() + 
                                     ", Value = " + entry.getValue()); 

        		request.getInputStream();
        		request.getRequestURI();
        		request.getAttributeNames();
        		
        		Enumeration<?> requestParamNames = request.getParameterNames();
                while (requestParamNames.hasMoreElements()) {
                    String requestParamName = (String) requestParamNames.nextElement();
                    String requestParamValue = request.getParameter(requestParamName);
                    System.out.println("requestParamName "+requestParamName +"  requestParamValue "+requestParamValue);
                    //typesafeRequestMap.put(requestParamName, requestParamValue);
                }
        	}
        	
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            SpringSecurityUserDetails userDetails= this.jwtUserDetailsService.getUserDetails(username);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}