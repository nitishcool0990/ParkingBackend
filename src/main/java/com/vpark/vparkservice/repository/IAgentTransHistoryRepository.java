package com.vpark.vparkservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vpark.vparkservice.entity.AgentTransHistory;



@Repository
public interface IAgentTransHistoryRepository extends JpaRepository<AgentTransHistory, Long>{

	@Query("Select ah From AgentTransHistory  ah  where ah.locId = ?1")
	List<AgentTransHistory>  findByLocationId(long locId);
		
	
}
