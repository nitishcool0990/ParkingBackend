package com.vpark.vparkservice.repository;

import com.vpark.vparkservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kalana.w on 5/17/2020.
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long>
{
}
