package com.vpark.vparkservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vpark.vparkservice.entity.BonusCodeUsers;

@Repository
public interface IBonusCodeUserRepository extends JpaRepository<BonusCodeUsers, Long> {

	
	@Query("SELECT bcu.userId,bcu.bonusCodeId,COUNT(bonusCodeId) FROM BonusCodeUsers bcu where userId= ?1 and status='ACTIVE' GROUP BY userId,bonusCodeId HAVING COUNT(bonusCodeId)>0 ")
    List<Object[]> findAllBonusCodeByuserId(long userId);
    
    @Query("SELECT bcu.userId,bcu.bonusCodeId,COUNT(bonusCodeId) FROM BonusCodeUsers bcu where userId= ?1 and status='ACTIVE' and bonusCodeId =?2 GROUP BY userId HAVING COUNT(bonusCodeId)>0 ")
    List<Object[]> findByBonusCodeByuserId(long userId,long bonusCode);
    
    @Query("SELECT bcu from BonusCodeUsers bcu where parkedBookId = ?1")
   Optional<BonusCodeUsers> findByParkedBookId(long parkedBookId);
}
