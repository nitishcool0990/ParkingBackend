package com.vpark.vparkservice.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.MyParkingHistoryDTO;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.dto.PaymentDTO;
import com.vpark.vparkservice.dto.PaymetGateWayDTO;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.entity.UserWallet;
import com.vpark.vparkservice.entity.ParkBookingHistory;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IParkBookingHistoryRepository;
import com.vpark.vparkservice.repository.IParkingLocationRepository;
import com.vpark.vparkservice.repository.IUserWalletRepository;

import lombok.Synchronized;


@Service
public class ParkingBookingService {
	
	 @Autowired
	 private IParkingLocationRepository parkingLocationRepository;
	    
	 @Autowired
	 private Environment ENV;
	 
	 @Autowired
	 private IUserWalletRepository userWalletRepo;
	 
	 @Autowired
	 private ModelMapper modelMapper;
	 
	 @Autowired
	 private IParkBookingHistoryRepository   ParkBookingHistoryRepository ;
	    
	
	public EsResponse<ParkingLocationDto> getParkingInfo(long parkingId, long vehicleTypeId) {
		try {
			List<Object[]> objList = parkingLocationRepository.getParkingInfo(parkingId, vehicleTypeId);
			ParkingLocationDto parkingLocDTO = null;
			if (!objList.isEmpty()) {
				Object[] obj = objList.get(0);
				parkingLocDTO = new ParkingLocationDto((Long) obj[0], (double) obj[6], (double) obj[7], (double) obj[5],
						obj[4].toString(), obj[1].toString(), obj[2].toString(), obj[3].toString(), obj[8].toString()  ,  (double)  obj[9] , (double)  obj[10] , (byte[]) obj[11]);
				return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, parkingLocDTO , this.ENV.getProperty("booking.parking.details"));
			} else {
				return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR , this.ENV.getProperty("exception.internalerror"));
			 }

		} catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR , this.ENV.getProperty("exception.internal.error"));
		   }
	 }
	
	
	
	public EsResponse<	List<MyParkingHistoryDTO> > getUserParkingHistory(long userId){
		try{
			List<ParkBookingHistory>  parkBookingHistVos = ParkBookingHistoryRepository.findByUserId(userId);
			
			List<MyParkingHistoryDTO> myParkingHistoryDtos    = parkBookingHistVos.stream()
					 .map((parkHistoryVo) ->{
				MyParkingHistoryDTO  myParkingHistoryDto  = modelMapper.map(parkHistoryVo , MyParkingHistoryDTO.class) ;
				                                        myParkingHistoryDto.setBookingDate(parkHistoryVo.getCreatedDate()) ;
				                                        
				return myParkingHistoryDto;
				
			}).collect(Collectors.toList());
			
			return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, myParkingHistoryDtos , this.ENV.getProperty("booking.parking.history.found"));
		}
		catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR , this.ENV.getProperty("exception.internal.error"));
		   }
	
	}
	
	public EsResponse<PaymentDTO> initBooking( long  parkingId, long userId,double amount,LocalTime  fromDate,LocalTime  toDate){
		try {
			
			UserWallet userWallet = this.userWalletRepo.findByUserId(userId).orElse(null);
			if(userWallet!=null) {
				ParkingLocation location = this.parkingLocationRepository.findById(parkingId).orElse(null);
				
				LocalTime fromLOCDateTime = LocalTime.of(Integer.parseInt(location.getOpenTime().split(":")[0]),Integer.parseInt(location.getOpenTime().split(":")[1]));
				LocalTime toLOCDateTime =  LocalTime.of(Integer.parseInt(location.getCloseTime().split(":")[0]),Integer.parseInt(location.getCloseTime().split(":")[1]));
				
				long hours = fromLOCDateTime.until( fromDate, ChronoUnit.HOURS );
				fromLOCDateTime = fromLOCDateTime.plusHours( hours );
				long minutes = fromLOCDateTime.until( fromDate, ChronoUnit.MINUTES );
				
				if(hours<0 || minutes<0) {
					 return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("booking.parking.open"));
				}
				
				hours = toLOCDateTime.until( toDate, ChronoUnit.HOURS );
				toLOCDateTime = toLOCDateTime.plusHours( hours );
				minutes = toLOCDateTime.until( toDate, ChronoUnit.MINUTES );
				if(hours>0|| minutes >0) {
					return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("booking.parking.close"));
				}
				
				
				LocalTime currentTime =LocalTime.now();
				
				hours = currentTime.until( fromDate, ChronoUnit.HOURS );
				fromLOCDateTime = currentTime.plusHours( hours );
				minutes = currentTime.until( fromDate, ChronoUnit.MINUTES );
				
				if(hours<0|| minutes <0) {
					  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("booking.time.old"));
				}else {
					if((hours>=1 && minutes>0)  ) {
						 return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("booking.not.allowed"));
					}
				}
				
				PaymentDTO paymentDto = new PaymentDTO();
				PaymetGateWayDTO paymentGateWayDTO=null;
				if(userWallet.getDeposit()+userWallet.getReal()+userWallet.getBonus()>=amount) {
					paymentGateWayDTO=new PaymetGateWayDTO("Wallet", userWallet.getDeposit()+userWallet.getReal()+userWallet.getBonus());
					paymentDto.setParkingId(parkingId);
					paymentDto.setPayableAmt(amount);
					paymentDto.getPaymentGateWay().add(paymentGateWayDTO);
				}else {
					 paymentGateWayDTO=new PaymetGateWayDTO("Wallet", userWallet.getDeposit()+userWallet.getReal()+userWallet.getBonus());
					paymentDto.setParkingId(parkingId);
					paymentDto.setPayableAmt(amount);
					paymentDto.getPaymentGateWay().add(paymentGateWayDTO);
					
					 paymentGateWayDTO=new PaymetGateWayDTO("CashFree",amount- (userWallet.getDeposit()+userWallet.getReal()+userWallet.getBonus()));
					paymentDto.getPaymentGateWay().add(paymentGateWayDTO);
				}
			
				  return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, paymentDto,this.ENV.getProperty("booking.parking.init"));
			}else {
				  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.not.found"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internalerror"));
		}
	}
	
	@Synchronized
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public EsResponse<PaymentDTO> addBookingAmount(long parkingId, long userId, double amount,long transactionId,String details) {
		try {
		UserWallet userWallet = this.userWalletRepo.findByUserId(userId).orElse(null);
		if(userWallet!=null && amount>0) {
			userWallet.setDeposit(userWallet.getDeposit()+amount);
			this.userWalletRepo.save(userWallet);
			 return new EsResponse<>(IConstants.RESPONSE_ADD_PAYMENT, this.ENV.getProperty("payment.booking.added"));
		}else {
			System.out.println("either userWallet is null or amount is equal or less zero "+amount);
			  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internalerror"));
		}
		}catch(Exception e) {
			e.printStackTrace();
			  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internalerror"));
		}
		
	}
	
	@Synchronized
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public EsResponse<ParkingLocationDto> doneBooking(long parkingId, long userId, double amount,boolean forBook) {
		try{
			UserWallet userWallet = this.userWalletRepo.findByUserId(userId).orElse(null);
			if(userWallet!=null && amount>0) {
				if(userWallet.getDeposit()+userWallet.getReal()+userWallet.getBonus()>=amount) {
					boolean saveFlag=true;
					if(userWallet.getDeposit()>=amount) {
						userWallet.setDeposit(userWallet.getDeposit()-amount);
					}else {
						double remainingAmount = amount-userWallet.getDeposit();
						userWallet.setDeposit(0);
						if(userWallet.getReal()>=remainingAmount) {
							userWallet.setReal(userWallet.getReal()-remainingAmount);
						}else {
							remainingAmount = remainingAmount-userWallet.getReal();
							userWallet.setReal(0);
							if(userWallet.getBonus()>=remainingAmount) {
								userWallet.setBonus(userWallet.getBonus()-remainingAmount);
							}else {//this code never be executed because value is already check on top 
								saveFlag=false;
								System.out.println("User doesn't have sufficient amount");
								return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("booking.insufficient.amount"));
							}
						}
					}
					if(saveFlag) {
					
						ParkingLocation location = this.parkingLocationRepository.findById(parkingId).orElse(null);
						if(location!=null) {
							userWallet.setModifiedDate( LocalDateTime.now());
							this.userWalletRepo.save(userWallet);
							if(forBook) { //to cut amount and provide to Agent 
								
							}else {
								
							}
							
							
							ParkingLocationDto sendLoc= new ParkingLocationDto(parkingId, location.getLatitude(), location.getLongitude());
							 return new EsResponse<>(IConstants.RESPONSE_ADD_PAYMENT,sendLoc, this.ENV.getProperty("bookins.success"));
						}else {
							System.out.println("Location is null or save flaf is false "+saveFlag);
							  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internalerror"));
						}
					}else {
						System.out.println("User doesn't have sufficient amount");
						return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("booking.insufficient.amount"));
					}
				}else {
					System.out.println("user doesn't have sufficient amount to book ");
					System.out.println("User have deposit amount "+userWallet.getDeposit()+" bonus amount "+userWallet.getBonus() +" real amount "+userWallet.getReal());
					return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("booking.insufficient.amount"));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internalerror"));
		}
		
		return null;
		
	}
	
	public static void main(String[] args) {
		LocalTime fromDateTime = LocalTime.of(18, 00);
		LocalTime toDateTime = LocalTime.now();
		long hours = fromDateTime.until( toDateTime, ChronoUnit.HOURS );
		fromDateTime = fromDateTime.plusHours( hours );

		long minutes = fromDateTime.until( toDateTime, ChronoUnit.MINUTES );
		
		System.out.println("hours" +hours+"  mins " +minutes);
		System.out.println(fromDateTime);
		// = tempDateTime.plusMinutes( minutes );
	}
	

}
