package com.vpark.vparkservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vpark.vparkservice.entity.UserWallet;



@Repository
public interface IUserWalletRepository extends JpaRepository<UserWallet , Long>{
	
	public Optional<UserWallet> findByUserId(long userId);

}
