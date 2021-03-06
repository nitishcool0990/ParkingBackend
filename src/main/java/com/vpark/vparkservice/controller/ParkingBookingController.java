package com.vpark.vparkservice.controller;


import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.CancelBookingDTO;
import com.vpark.vparkservice.dto.CashFreeDTO;
import com.vpark.vparkservice.dto.DoneBookingDTO;
import com.vpark.vparkservice.dto.InitBookingDTO;
import com.vpark.vparkservice.dto.MyParkingHistoryDTO;
import com.vpark.vparkservice.dto.ParkingLocationDTO;
import com.vpark.vparkservice.dto.PaymentDTO;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.RequestAttribute;
import com.vpark.vparkservice.service.ParkingBookingService;


@RestController
public class ParkingBookingController implements IParkingBookingController {
	
	@Autowired
	private ParkingBookingService parkingBookingService;

	 @Autowired
	 private Environment ENV;
	 
	@Override
	public ResponseEntity<EsResponse<ParkingLocationDTO>> getParkingInfo(long parkingId, long vehicleTypeId  , long userId) {
		return ResponseEntity.ok(this.parkingBookingService.getParkingInfo(parkingId,vehicleTypeId  , userId));
	}
	
	public  ResponseEntity<EsResponse<PaymentDTO>> initBooking( InitBookingDTO  initBookingDto , long userId ){
		if(initBookingDto.getParkLocId() <= 0 ){
		   return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
		}
		LocalTime fromTime = LocalTime.of(Integer.parseInt(initBookingDto.getFromTime().split(":")[0]),Integer.parseInt(initBookingDto.getFromTime().split(":")[1]));
		LocalTime toTime =  LocalTime.of(Integer.parseInt(initBookingDto.getToTime().split(":")[0]),Integer.parseInt(initBookingDto.getToTime().split(":")[1]));
		
		initBookingDto.setFromDate(fromTime);
		initBookingDto.setToDate(toTime);
	
		return ResponseEntity.ok(this.parkingBookingService.initBooking( initBookingDto , userId ));
	}

	@Override
	public ResponseEntity<EsResponse<PaymentDTO>> addBookingAmount(CashFreeDTO cashFreeDto, long userId) {
		return  ResponseEntity.ok(this.parkingBookingService.addBookingAmount(cashFreeDto,userId));

	}

	@Override
	public ResponseEntity<EsResponse<ParkingLocationDTO>> doneBooking(DoneBookingDTO  doneBookingDto , long userId ) {

		if(doneBookingDto.getParkingLocId() <= 0 ){
			   return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
			}
		return ResponseEntity.ok(this.parkingBookingService.doneBooking(doneBookingDto , userId ));
	}
	
	
	
	@Override
	public ResponseEntity<EsResponse<List<MyParkingHistoryDTO>>> getUserParkingHistory(@RequestAttribute("Id")  long userId){
		return ResponseEntity.ok(this.parkingBookingService.getUserParkingHistory(userId));
	}
	
	
	
	@Override
	public ResponseEntity<EsResponse<PaymentDTO>> cancelBookingAmount(CancelBookingDTO  cancelBookingDto , @RequestAttribute("Id")  long userId ){
		

		if(cancelBookingDto.getBookingId() <= 0 ){
			   return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
			}
		return ResponseEntity.ok(this.parkingBookingService.cancelBookingAmount(cancelBookingDto , userId ));
		
	}

	@Override
	public ResponseEntity<EsResponse<PaymentDTO>> initMonthlyBooking(InitBookingDTO monthlyBookingDto, @RequestAttribute("Id")  long userId) {
		
		if(monthlyBookingDto.getParkLocId() <= 0){
			  return ResponseEntity.badRequest().body(new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("invalid.id")));
		}
		return ResponseEntity.ok(this.parkingBookingService.initMonthlyBooking(monthlyBookingDto, userId));
		
		
		
	}

	


}
