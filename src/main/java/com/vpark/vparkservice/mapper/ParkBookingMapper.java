package com.vpark.vparkservice.mapper;

import org.springframework.stereotype.Component;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.DoneBookingDTO;
import com.vpark.vparkservice.entity.AgentTransHistory;
import com.vpark.vparkservice.entity.ParkBookingHistory;
import com.vpark.vparkservice.entity.ParkTransHistory;
import com.vpark.vparkservice.entity.ParkingDetails;

@Component("ParkBookingMapper")
public class ParkBookingMapper {

	
	public AgentTransHistory createAgentHitsoryVo( double percentageAmt  , long userId  , long ParkLocId , long bookingId , String remarks  ){
		
		AgentTransHistory agentHistory = new AgentTransHistory();
		agentHistory.setAmt(percentageAmt);
		agentHistory.setLocId(ParkLocId);
		agentHistory.setBookingId(bookingId);
		agentHistory.setChipType("real");
		agentHistory.setCrdr("CR");
		agentHistory.setRemarks(remarks);
		agentHistory.setStatus(IConstants.TransStatus.APPROVED);
		agentHistory.setUser(userId);
		
		return agentHistory ;
		
	}
	
    public ParkTransHistory createParkingHitsoryVo( double amount  , long userId, String crDR, String remarks , String chipType ){
		
    	ParkTransHistory parkTrans = new ParkTransHistory();
		parkTrans.setAmt(amount);
		parkTrans.setChipType(chipType);
		parkTrans.setCrdr(crDR);
		parkTrans.setRemarks(remarks);
		parkTrans.setStatus(IConstants.TransStatus.APPROVED);
		parkTrans.setUser(userId);
		
		return parkTrans ;
		
	}
    
    
	public ParkBookingHistory createParkingBookingHitsoryVo(DoneBookingDTO doneBookingDto  , ParkingDetails parkingDetails , double depositAmt , double realAmt , double bonusAmt, long userId , String remark ) {

		ParkBookingHistory bookingHistory = new ParkBookingHistory();
		bookingHistory.setDepositAmt(depositAmt);
		bookingHistory.setRealAmt(realAmt);
		bookingHistory.setBonusAmt(bonusAmt);
		bookingHistory.setVehicleId(doneBookingDto.getVehicleId());
		bookingHistory.setBookingType(parkingDetails.getParkingLocation().getParkingType().getParkingType());
		bookingHistory.setCr_dr("DR");
		bookingHistory.setInTime(doneBookingDto.getInTime());
		bookingHistory.setOutTime(doneBookingDto.getOutTime());
		bookingHistory.setParkingDetailsId(parkingDetails.getId());
		bookingHistory.setParkingLocationId(doneBookingDto.getParkingId());
		bookingHistory.setRemarks(remark);
		bookingHistory.setStatus(IConstants.ParkingStatus.RUNNING);
		bookingHistory.setUserId(userId);

		return bookingHistory;

	}
}
