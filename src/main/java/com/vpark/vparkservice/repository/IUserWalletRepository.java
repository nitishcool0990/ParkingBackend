package com.vpark.vparkservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vpark.vparkservice.entity.UserWallet;



@Repository
public interface IUserWalletRepository extends JpaRepository<UserWallet , Long>{

	@Query("Select wallet From UserWallet wallet where wallet.user.id = ?1")
	Optional<UserWallet>  findByUserId(long userId) ;
}
