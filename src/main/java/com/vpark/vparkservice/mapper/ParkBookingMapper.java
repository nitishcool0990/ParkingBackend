package com.vpark.vparkservice.mapper;

import org.springframework.stereotype.Component;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.entity.AgentTransHistory;
import com.vpark.vparkservice.entity.ParkBookingHistory;
import com.vpark.vparkservice.entity.ParkTransHistory;
import com.vpark.vparkservice.entity.ParkingDetails;

@Component("ParkBookingMapper")
public class ParkBookingMapper {

	
	public AgentTransHistory createAgentHitsoryVo( double percentageAmt  , long userId ){
		
		AgentTransHistory agentHistory = new AgentTransHistory();
		agentHistory.setAmt(percentageAmt);
		agentHistory.setChipType("real");
		agentHistory.setCrdr("cr");
		agentHistory.setRemarks("User Book Parking spot");
		agentHistory.setStatus(IConstants.TransStatus.APPROVED);
		agentHistory.setUser(userId);
		
		return agentHistory ;
		
	}
	
    public ParkTransHistory createParkingHitsoryVo( double amount  , long userId ){
		
    	ParkTransHistory parkTrans = new ParkTransHistory();
		parkTrans.setAmt(amount);
		parkTrans.setChipType("real");
		parkTrans.setCrdr("dr");
		parkTrans.setRemarks("Parking booking");
		parkTrans.setStatus(IConstants.TransStatus.APPROVED);
		parkTrans.setUser(userId);
		
		return parkTrans ;
		
	}
    
    
	public ParkBookingHistory createParkingBookingHitsoryVo(ParkingDetails parkingDetails , double amount, long userId  , String inTime,String outTime) {

		ParkBookingHistory bookingHistory = new ParkBookingHistory();
		bookingHistory.setAmt(amount);
		bookingHistory.setBookingType(parkingDetails.getParkingLocation().getParkingType().getParkingType());
		bookingHistory.setCr_dr("DR");
		bookingHistory.setInTime(inTime);
		bookingHistory.setOutTime(outTime);
		bookingHistory.setParkingDetailsId(parkingDetails.getId());
		bookingHistory.setParkingLocationId(parkingDetails.getParkingLocation().getId());
		bookingHistory.setRemarks("Booking for Parking");
		bookingHistory.setStatus(IConstants.ParkingStatus.RUNNING);
		bookingHistory.setUserId(userId);

		return bookingHistory;

	}
}
