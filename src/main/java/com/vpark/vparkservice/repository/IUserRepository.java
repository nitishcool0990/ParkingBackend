package com.vpark.vparkservice.repository;

import com.vpark.vparkservice.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Created by kalana.w on 5/17/2020.
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
      
	User findByMobileNo(String mobileNo);
	
	
   
   Optional<User> findUserByMobileNo(String mobileNo );
   
   @Query("Select user from User user  where user.userProfile.referalCode Like ?1 ")
    User findByReferalCodeLike(String referalCode);
   
}
