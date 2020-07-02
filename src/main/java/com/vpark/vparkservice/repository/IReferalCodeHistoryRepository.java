package com.vpark.vparkservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vpark.vparkservice.entity.ReferalCodeHistory;


@Repository
public interface IReferalCodeHistoryRepository extends JpaRepository<ReferalCodeHistory, Long>{

	@Query("Select rch from ReferalCodeHistory rch where refereeUserId = ?1 and status = 'PENDING'")
	ReferalCodeHistory  findByRefereeUserIdAndStatus(long id);
}
