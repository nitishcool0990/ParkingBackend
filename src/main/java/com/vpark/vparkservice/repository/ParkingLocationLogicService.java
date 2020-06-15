package com.vpark.vparkservice.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ParkingLocationLogicService {
	/*
	 * @Autowired private SessionFactory sessionFactory;
	 * 
	 * public List<Object[]> getClosestParkingArea(long latitude,long longitude, int
	 * offSetLimit ) {
	 * 
	 * try { Query q = sessionFactory.getCurrentSession().
	 * createSQLQuery("CALL `vPark`.`closest_parking`(:units,:latitude, :longitude, :distance, :limit)"
	 * ) .setParameter("units", "KM") .setParameter("latitude", latitude)
	 * .setParameter("latitude", latitude) .setParameter("distance", 2)
	 * .setParameter("limit", offSetLimit);
	 * 
	 * List<Object[]> parkingList = q.getResultList();
	 * 
	 * return parkingList;
	 * 
	 * }catch(Exception e) { e.printStackTrace(); }
	 * 
	 * return null;
	 * 
	 * }
	 */
}
