package com.vpark.vparkservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vpark.vparkservice.entity.AgentTransHistory;



@Repository
public interface IAgentTransHistoryRepository extends JpaRepository<AgentTransHistory, Long>{

}
