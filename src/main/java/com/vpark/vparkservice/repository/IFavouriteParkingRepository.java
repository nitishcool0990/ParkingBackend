package com.vpark.vparkservice.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vpark.vparkservice.entity.FavouriteParking;


@Repository
public interface IFavouriteParkingRepository  extends JpaRepository<FavouriteParking, Long>{

	@Query(value = "select * from favourite_parking  where park_loc_id = ?1 and user_id = ?2" , nativeQuery = true)
	Optional<FavouriteParking> findByLocationIdAndUserId(long locId , long userId ) ;
	
	@Query("select fp from FavouriteParking fp  where  fp.user.id = ?1")
	List<FavouriteParking> findAllByUserId(long userId);
}
