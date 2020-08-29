package com.vpark.vparkservice.controller;


import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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


@RequestMapping("booking/")
public interface IParkingBookingController {
	
	 @GetMapping(value=IConstants.VERSION_1 + "/parkingInfo/{parkingId}/{vehicleTypeId}")
	    ResponseEntity<EsResponse<ParkingLocationDTO>> getParkingInfo(@PathVariable long  parkingId,@PathVariable long vehicleTypeId  , @RequestAttribute("Id") long id );
	 
	 @PostMapping(value=IConstants.VERSION_1 + "/initBooking")
	    ResponseEntity<EsResponse<PaymentDTO>> initBooking( @RequestBody  InitBookingDTO  initBookingDto ,@RequestAttribute("Id") long id );
	 
	 @PostMapping(value=IConstants.VERSION_1 + "/addAmount")
	 ResponseEntity<EsResponse<PaymentDTO>> addBookingAmount(@RequestBody CashFreeDTO cashFreeDto,@RequestAttribute("Id")  long id);

	 @PostMapping(value=IConstants.VERSION_1 + "/doneBooking")
	 ResponseEntity<EsResponse<ParkingLocationDTO>> doneBooking( @RequestBody DoneBookingDTO  doneBookingDto  , @RequestAttribute("Id") long id );

	 @GetMapping(value=IConstants.VERSION_1+"/myBooking")
	    ResponseEntity<EsResponse<List<MyParkingHistoryDTO>>> getUserParkingHistory(@RequestAttribute("Id")  long userId);
	 
	 @PostMapping(value=IConstants.VERSION_1 + "/monthlyBooking")
	 ResponseEntity<EsResponse<PaymentDTO>> initMonthlyBooking(@RequestBody  InitBookingDTO  monthlyBookingDto ,  @RequestAttribute("Id")  long id);
	 
	 @PostMapping(value=IConstants.VERSION_1 + "/cancelBooking")
	 ResponseEntity<EsResponse<PaymentDTO>> cancelBookingAmount(@RequestBody  CancelBookingDTO  cancelBookingDto ,  @RequestAttribute("Id")  long id );


}
