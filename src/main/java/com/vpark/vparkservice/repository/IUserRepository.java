package com.vpark.vparkservice.repository;

import com.vpark.vparkservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by kalana.w on 5/17/2020.
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long>
{
	Optional<User> findByMobileNo( String mobileNo );
}
