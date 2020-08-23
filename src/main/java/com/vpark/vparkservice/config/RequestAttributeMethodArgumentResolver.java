package com.vpark.vparkservice.config;


import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.vpark.vparkservice.dto.SpringSecurityUserDetails;
import com.vpark.vparkservice.model.RequestAttribute;

@Component
public class RequestAttributeMethodArgumentResolver  implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		 return parameter.getParameterAnnotation(RequestAttribute.class) != null;
		
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		RequestAttribute requestAttributeAnnotation = parameter.getParameterAnnotation(RequestAttribute.class);

	      if (requestAttributeAnnotation == null)
	         return null;

	     // HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
	     //  request.getAttribute(requestAttributeAnnotation.value());
	     return getUserId() ;
		
	}
	
	public static Long getUserId() {
		Long userId = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (null != auth && null != auth.getPrincipal() && !(auth instanceof AnonymousAuthenticationToken)) {
			SpringSecurityUserDetails userDetails = (SpringSecurityUserDetails) auth.getPrincipal();
			if (null !=  userDetails) 
				userId = userDetails.getUserId();
			
		}
		return userId;
	}

}
