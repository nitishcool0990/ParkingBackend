package com.vpark.vparkservice.service;

import com.vpark.vparkservice.dto.SpringSecurityUserDetails;
import com.vpark.vparkservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by kalana.w on 5/8/2020.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.vpark.vparkservice.entity.User user = this.userRepository.findUserByMobileNo(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getUserType().name()));

        return new User(username, user.getPassword(), authorities);
    }
    
    
    public SpringSecurityUserDetails getUserDetails(String username) throws UsernameNotFoundException{
    	
    	 com.vpark.vparkservice.entity.User user = this.userRepository.findUserByMobileNo(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    	 
         List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getUserType().name()));

         return new SpringSecurityUserDetails( user.getId() ,  username, user.getPassword()  , authorities,user.getVehicles() ) ;
       
    }
}
