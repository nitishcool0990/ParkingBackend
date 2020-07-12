package com.vpark.vparkservice.controller;


import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.CancelBookingDTO;
import com.vpark.vparkservice.dto.CashFreeDTO;
import com.vpark.vparkservice.dto.DoneBookingDTO;
import com.vpark.vparkservice.dto.MonthlyBookingDTO;
import com.vpark.vparkservice.dto.MyParkingHistoryDTO;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.dto.PaymentDTO;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.RequestAttribute;


@RequestMapping("booking/")
public interface IParkingBookingController {
	
	 @GetMapping(value=IConstants.VERSION_1 + "/parkingInfo/{parkingId}/{vehicleTypeId}")
	    ResponseEntity<EsResponse<ParkingLocationDto>> getParkingInfo(@PathVariable long  parkingId,@PathVariable long vehicleTypeId);
	 
	 @PostMapping(value=IConstants.VERSION_1 + "/initBooking")
	    ResponseEntity<EsResponse<PaymentDTO>> initBooking(@RequestParam("parkingLocId") long  parkingId,@RequestAttribute("Id") long id,@RequestParam("amount") double amount,@RequestParam("fromTime")  String  fromDate ,@RequestParam("toTime") String  toDate,@RequestParam("vehicleTypeId") long vehicleTypeId,@RequestParam("bonusCode") String bonusCode );
	 
	 @PostMapping(value=IConstants.VERSION_1 + "/addAmount")
	 ResponseEntity<EsResponse<PaymentDTO>> addBookingAmount(@RequestBody CashFreeDTO cashFreeDto,@RequestAttribute("Id")  long id);

	 @PostMapping(value=IConstants.VERSION_1 + "/doneBooking")
	 ResponseEntity<EsResponse<ParkingLocationDto>> doneBooking( @RequestBody DoneBookingDTO  doneBookingDto  , @RequestAttribute("Id") long id );

	 @GetMapping(value=IConstants.VERSION_1+"/myBooking")
	    ResponseEntity<EsResponse<List<MyParkingHistoryDTO>>> getUserParkingHistory(@RequestAttribute("Id")  long userId);
	 
	 @PostMapping(value=IConstants.VERSION_1 + "/monthlyBooking")
	 ResponseEntity<EsResponse<PaymentDTO>> initMonthlyBooking(@RequestBody  MonthlyBookingDTO  monthlyBookingDto ,  @RequestAttribute("Id")  long id);
	 
	 @PostMapping(value=IConstants.VERSION_1 + "/cancelBooking")
	 ResponseEntity<EsResponse<PaymentDTO>> cancelBookingAmount(@RequestBody  CancelBookingDTO  cancelBookingDto ,  @RequestAttribute("Id")  long id );


}
