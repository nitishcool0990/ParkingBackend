package com.vpark.vparkservice.controller;


import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vpark.vparkservice.dto.CashFreeDTO;
import com.vpark.vparkservice.dto.MyParkingHistoryDTO;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.dto.PaymentDTO;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.RequestAttribute;
import com.vpark.vparkservice.service.ParkingBookingService;


@RestController
public class ParkingBookingController implements IParkingBookingController {
	
	@Autowired
	private ParkingBookingService parkingBookingService;

	@Override
	public ResponseEntity<EsResponse<ParkingLocationDto>> getParkingInfo(long parkingId, long vehicleTypeId) {
		return ResponseEntity.ok(this.parkingBookingService.getParkingInfo(parkingId,vehicleTypeId));
	}
	
	public  ResponseEntity<EsResponse<PaymentDTO>> initBooking( long  parkingLocId, long userId,double amount,String  fromDate,String  toDate){
		LocalTime fromTime = LocalTime.of(Integer.parseInt(fromDate.split(":")[0]),Integer.parseInt(fromDate.split(":")[1]));
		LocalTime toTime =  LocalTime.of(Integer.parseInt(toDate.split(":")[0]),Integer.parseInt(toDate.split(":")[1]));
	
		return ResponseEntity.ok(this.parkingBookingService.initBooking(parkingLocId, userId ,  amount , fromTime , toTime));
	}

	@Override
	public ResponseEntity<EsResponse<PaymentDTO>> addBookingAmount(CashFreeDTO cashFreeDto, long userId) {
		// TODO Auto-generated method stub
		return  ResponseEntity.ok(this.parkingBookingService.addBookingAmount(cashFreeDto,userId));

	}

	@Override
	public ResponseEntity<EsResponse<ParkingLocationDto>> doneBooking(long parkingLocId, long userId, double amount, long  vehicleTypeId , String  inTime,String  outTime) {
	
		return ResponseEntity.ok(this.parkingBookingService.doneBooking(parkingLocId , userId , amount , vehicleTypeId , inTime , outTime));
	}
	
	
	@Override
	public ResponseEntity<EsResponse<List<MyParkingHistoryDTO>>> getUserParkingHistory(@RequestAttribute("Id")  long userId){
		return ResponseEntity.ok(this.parkingBookingService.getUserParkingHistory(userId));
	}
	
	@Override
	public ResponseEntity<EsResponse<PaymentDTO>> cancelBookingAmount(@RequestParam("bookingParkId") long  parkBookId,@RequestAttribute("Id")  long userId,double  latitude, double  longitude){
		return ResponseEntity.ok(this.parkingBookingService.cancelBookingAmount(parkBookId, userId,latitude,longitude));
		
	}


}
