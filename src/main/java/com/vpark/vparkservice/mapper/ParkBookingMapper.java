package com.vpark.vparkservice.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TreeMap;
import org.springframework.stereotype.Component;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.CheckInAndCheckOutDTO;
import com.vpark.vparkservice.dto.DoneBookingDTO;
import com.vpark.vparkservice.dto.ParkingLocationDTO;
import com.vpark.vparkservice.entity.AgentTransHistory;
import com.vpark.vparkservice.entity.ParkBookingHistory;
import com.vpark.vparkservice.entity.ParkTransHistory;
import com.vpark.vparkservice.entity.ParkingDetails;

@Component("ParkBookingMapper")
public class ParkBookingMapper {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    
	public AgentTransHistory createAgentHitsoryVo( double amt  , long userId  ,  long agentId ,long ParkLocId , long bookingId , String remarks  ){
		
		AgentTransHistory agentHistory = new AgentTransHistory();
		agentHistory.setAmt(amt);
		agentHistory.setLocId(ParkLocId);
		agentHistory.setBookingId(bookingId);
		agentHistory.setChipType("Real");
		agentHistory.setCrdr("CR");
		agentHistory.setRemarks(remarks);
		agentHistory.setStatus(IConstants.TransStatus.APPROVED);
		agentHistory.setUserId(userId);
		agentHistory.setAgentId(agentId);
		
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
		

        if(doneBookingDto.isMonthlyBookingFlag()){
            
            LocalDate   fromDate = LocalDate.parse(doneBookingDto.getFromDate(), formatter);
            LocalDate  endDate  = fromDate.plusDays(30);
            bookingHistory.setFromDate(fromDate);
            bookingHistory.setEndDate(endDate);
        }

		bookingHistory.setParkingDetailsId(parkingDetails.getId());
		bookingHistory.setParkingLocationId(doneBookingDto.getParkingLocId());
		bookingHistory.setRemarks(remark);
		bookingHistory.setStatus(IConstants.ParkingStatus.RUNNING);
		bookingHistory.setUserId(userId);

		return bookingHistory;

	}
	
	
	public ParkingLocationDTO convertToParkingLocationDTO(List<Object[]> objList) {
		ParkingLocationDTO parkingLocDto = null;
		if (!objList.isEmpty()) {
			Object[] obj = objList.get(0);
			
			TreeMap<Double, Double> hourlyTimeSlot =new TreeMap<Double,Double>();
			
			for(Object[] objArray :objList ) {
				
				hourlyTimeSlot.put((double)objArray[12], (double)objArray[6]);
			}
			parkingLocDto = new ParkingLocationDTO((Long) obj[0], (double) obj[7],  (double) obj[5],
					obj[4].toString(), obj[1].toString(), obj[2].toString(), obj[3].toString(), obj[8].toString()  ,  (double)  obj[10] , (double)  obj[9] , (byte[]) obj[11],hourlyTimeSlot,(int)obj[13]);
			parkingLocDto.setChargesType(obj[14].toString());
			parkingLocDto.setMaxLimit((double) obj[15]);
		}
		
		return parkingLocDto;
	}
	
	
	public ParkBookingHistory createManualParkingBookingHitsoryVo( CheckInAndCheckOutDTO  checkInDto) {

		ParkBookingHistory bookingHistory = new ParkBookingHistory();
		bookingHistory.setDepositAmt(0);
		bookingHistory.setRealAmt(0);
		bookingHistory.setBonusAmt(0);
		bookingHistory.setVehicleId(checkInDto.getVehicleTypeId());  // on manual checking ,putting here vehicle type id instead of vehicle id
		bookingHistory.setBookingType("Manual");
		bookingHistory.setCr_dr("DR");
		bookingHistory.setInTime(null);
		bookingHistory.setOutTime(null);
		bookingHistory.setParkingDetailsId(-1);
		bookingHistory.setParkingLocationId(checkInDto.getLocationId());
		bookingHistory.setRemarks(checkInDto.getVehicleNum());
		bookingHistory.setStatus(IConstants.ParkingStatus.PARKED);
		bookingHistory.setUserId(-1);

		return bookingHistory;

	}
	
}
