package com.vpark.vparkservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vpark.vparkservice.entity.BonusCodes;
import com.vpark.vparkservice.entity.ParkingLocation;

@Repository
public interface IBonusCodeRepository extends JpaRepository<BonusCodes, Long> {
	
	@Query("select  bonusCode from  BonusCodes bonusCode where bonusCode.status = 'ACTIVE'")
    List<BonusCodes> fetchAllBonusCode();
	
	@Query("select  bonusCode from  BonusCodes bonusCode where bonusCode.status = 'ACTIVE' and code =?1")
    Optional<BonusCodes> findByBonusCode(String bonusCode);

}
