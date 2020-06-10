package com.vpark.vparkservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.vpark.vparkservice.dto.SpringSecurityUserDetails;

@Component
public class ServiceInterceptor  implements HandlerInterceptor{

	@Override
	   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//Long userId = getUserId() ;
		 /// if(null !=userId){
			 // request.setAttribute("userId", userId);
			 return true;
			  
		  //}
		 // else
			 // return reject(response);
	   }
	
	
	private boolean reject(HttpServletResponse response) {
		return false;
	}


	
}
