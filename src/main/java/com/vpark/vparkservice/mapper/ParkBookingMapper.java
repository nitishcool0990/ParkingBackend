package com.vpark.vparkservice.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TreeMap;
import org.springframework.stereotype.Component;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.DoneBookingDTO;
import com.vpark.vparkservice.dto.ParkingLocationDto;
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
		bookingHistory.setParkingLocationId(doneBookingDto.getParkingId());
		bookingHistory.setRemarks(remark);
		bookingHistory.setStatus(IConstants.ParkingStatus.RUNNING);
		bookingHistory.setUserId(userId);

		return bookingHistory;

	}
	
	
	public ParkingLocationDto convertToParkingLocationDTO(List<Object[]> objList) {
		ParkingLocationDto parkingLocDTO = null;
		if (!objList.isEmpty()) {
			Object[] obj = objList.get(0);
			
			TreeMap<Double, Double> hourlyTimeSlot =new TreeMap<Double,Double>();
			
			for(Object[] objArray :objList ) {
				
				hourlyTimeSlot.put((double)objArray[12], (double)objArray[6]);
			}
			parkingLocDTO = new ParkingLocationDto((Long) obj[0], (double) obj[7],  (double) obj[5],
					obj[4].toString(), obj[1].toString(), obj[2].toString(), obj[3].toString(), obj[8].toString()  ,  (double)  obj[10] , (double)  obj[9] , (byte[]) obj[11],hourlyTimeSlot,(int)obj[13]);
			parkingLocDTO.setChargesType(obj[14].toString());
			parkingLocDTO.setMaxLimit((double) obj[15]);
		}
		return parkingLocDTO;
	}
}
