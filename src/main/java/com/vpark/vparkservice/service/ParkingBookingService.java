package com.vpark.vparkservice.service;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.CashFreeDTO;
import com.vpark.vparkservice.dto.MonthlyBookingDTO;
import com.vpark.vparkservice.dto.MyParkingHistoryDTO;
import com.vpark.vparkservice.dto.ParkingLocationDto;
import com.vpark.vparkservice.dto.PaymentDTO;
import com.vpark.vparkservice.dto.PaymetGateWayDTO;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.entity.UserWallet;
import com.vpark.vparkservice.mapper.ParkBookingMapper;
import com.vpark.vparkservice.entity.AgentTransHistory;
import com.vpark.vparkservice.entity.CashFreeTransHistory;
import com.vpark.vparkservice.entity.ParkBookingHistory;
import com.vpark.vparkservice.entity.ParkTransHistory;
import com.vpark.vparkservice.entity.ParkingDetails;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.model.RequestAttribute;
import com.vpark.vparkservice.repository.IAgentTransHistoryRepository;
import com.vpark.vparkservice.repository.ICashFreeTransHistory;
import com.vpark.vparkservice.repository.IParkBookingHistoryRepository;
import com.vpark.vparkservice.repository.IParkTransHistoryRepository;
import com.vpark.vparkservice.repository.IParkingDetailsRepository;
import com.vpark.vparkservice.repository.IParkingLocationRepository;
import com.vpark.vparkservice.repository.IUserRepository;
import com.vpark.vparkservice.repository.IUserWalletRepository;
import com.vpark.vparkservice.test.GFGTest;

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
	 private IParkBookingHistoryRepository   parkBookingHistoryRepository ;
	
	 
	 @Autowired
	 private ICashFreeTransHistory cashFreeRepo;
	 
	 @Autowired
	 private IParkingDetailsRepository parkingDetailsRepository;
	 
	 @Autowired
	 private IAgentTransHistoryRepository agentTransactionRepo;
	 
	 @Autowired
	 private IParkTransHistoryRepository parkingTransRepo;
	 
	 @Autowired
	 private ParkBookingMapper  parkBookingMapper ;
	    
	
	 
	public EsResponse<ParkingLocationDto> getParkingInfo(long parkingId, long vehicleTypeId) {
		try {
			List<Object[]> objList = parkingLocationRepository.getParkingInfo(parkingId, vehicleTypeId);
			ParkingLocationDto parkingLocDTO = null;
			
			if (!objList.isEmpty()) {
				Object[] obj = objList.get(0);
				
				HashMap<String,String> hourlyTimeSlot =new HashMap<String,String>();
				
				for(Object[] objArray :objList ) {
					
					hourlyTimeSlot.put(objArray[12].toString(), objArray[6].toString());
				}
				/*
				 * parkingloc0_.id AS col_0_0_, parkingloc0_.park_name AS col_1_0_,
				 * parkingloc0_.open_time AS col_2_0_, parkingloc0_.close_time AS col_3_0_,
				 * parkingloc0_.description AS col_4_0_, parkingloc0_.rating AS col_5_0_,
				 * parkingcha2_.hours AS col_6_0_, parkingcha2_.charges AS col_7_0_,
				 * parkingdet1_.monthly_rate AS col_8_0_, parkingdet1_.booking_rate AS col_9_0_,
				 * parkingloc0_.advance_booking_hr AS col_10_0_, parkingloc0_.booking_cancel_hr
				 * AS col_11_0_, parkingloc0_.photo AS col_12_0_
				 * 
				 * Long bookingParkId, double monthlyRate, Double rating, String describe,String
				 * parkingName, String openTime,String closeTime,String bookingRate , double
				 * cancelBookingHr , double advanceBookingHr , byte[] image,HashMap
				 * hourlyMoneyWithRate
				 */
				
				parkingLocDTO = new ParkingLocationDto((Long) obj[0], (double) obj[7],  (double) obj[5],
						obj[4].toString(), obj[1].toString(), obj[2].toString(), obj[3].toString(), obj[8].toString()  ,  (double)  obj[9] , (double)  obj[10] , (byte[]) obj[11],hourlyTimeSlot);
				
				return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, parkingLocDTO , this.ENV.getProperty("booking.parking.details"));
			} 
			else {
				return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR , this.ENV.getProperty("exception.internal.error"));
			 }

		} catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR , this.ENV.getProperty("exception.internal.error"));
		   }
	 }
	
	
	
	public EsResponse<	List<MyParkingHistoryDTO> > getUserParkingHistory(long userId){
		try{
			List<Object[ ]>  parkBookingHistoryVos = this.parkBookingHistoryRepository.findParkingHistoryByUserId(userId);
			
			List<MyParkingHistoryDTO> myParkingHistoryDtos    = parkBookingHistoryVos.stream().map(Object  ->{
						 
				MyParkingHistoryDTO  myParkingHistoryDto  = new MyParkingHistoryDTO() ;
				 myParkingHistoryDto.setBookingId((Long)Object[0]);
				myParkingHistoryDto.setInTime(Object[1].toString());
				myParkingHistoryDto.setOutTime(Object[2].toString());
				myParkingHistoryDto.setStatus(Object[3].toString());
				myParkingHistoryDto.setAmt(Object[4].toString());
				myParkingHistoryDto.setParkName(Object[5].toString());
				myParkingHistoryDto.setVehicleNo(Object[6].toString());
				myParkingHistoryDto.setVehicleType(Object[7].toString());
				myParkingHistoryDto.setBookingDate((LocalDateTime) Object[8]) ;
				                                        
				return myParkingHistoryDto;
				
			}).collect(Collectors.toList());
			
			return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, myParkingHistoryDtos , this.ENV.getProperty("booking.parking.history.found"));
		}
		catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR , this.ENV.getProperty("exception.internal.error"));
		   }
	}
	
	
	
	public EsResponse<PaymentDTO> initBooking( long  parkingLocId, long userId, double amount , LocalTime  fromDate  , LocalTime  toDate){
		try {
			
			UserWallet userWallet = this.userWalletRepo.findByUserId(userId).orElse(null);
			if(userWallet!=null) {
				ParkingLocation location = this.parkingLocationRepository.findById(parkingLocId).orElse(null);
				
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
					if((hours>= location.getAdvanceBookingHr() && minutes>0)  ) {
						 return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("booking.not.allowed"));
					}
				}
				
				PaymentDTO paymentDto = new PaymentDTO();
				PaymetGateWayDTO paymentGateWayDTO=null;
				if(userWallet.getDeposit()+userWallet.getReal()+userWallet.getBonus()>=amount) {
					paymentGateWayDTO=new PaymetGateWayDTO("Wallet", userWallet.getDeposit()+userWallet.getReal()+userWallet.getBonus());
					paymentDto.setParkingLocId(parkingLocId );
					paymentDto.setPayableAmt(amount);
					paymentDto.getPaymentGateWay().add(paymentGateWayDTO);
				}else {
					 paymentGateWayDTO=new PaymetGateWayDTO("Wallet", userWallet.getDeposit()+userWallet.getReal()+userWallet.getBonus());
					paymentDto.setParkingLocId(parkingLocId );
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
			  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internal.error"));
		}
	}
	
	
	
	@Synchronized
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public EsResponse<PaymentDTO> addBookingAmount(CashFreeDTO cashFreeDto, long userId) {
		try {
		UserWallet userWallet = this.userWalletRepo.findByUserId(userId).orElse(null);
		CashFreeTransHistory history = new CashFreeTransHistory();
		BeanUtils.copyProperties(cashFreeDto, history);
			history.setUserId(userId); //if it is -1 than user kill the app before cashfree give response to client
		this.cashFreeRepo.save(history);

		if(userWallet!=null && cashFreeDto.getOrderAmount()>0) {
			userWallet.setDeposit(userWallet.getDeposit()+cashFreeDto.getOrderAmount());
			this.userWalletRepo.save(userWallet);
			 return new EsResponse<>(IConstants.RESPONSE_ADD_PAYMENT, this.ENV.getProperty("payment.booking.added"));
		}else {
			System.out.println("either userWallet is null or amount is equal or less zero "+cashFreeDto.getOrderAmount());
			  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internal.error"));
		}
		}catch(Exception e) {
			e.printStackTrace();
			  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internal.error"));
		}
		
	}
	
	
	@Synchronized
	@Transactional(readOnly = false,   propagation = Propagation.REQUIRES_NEW)
	public EsResponse<ParkingLocationDto> doneBooking(long parkingLocId , long userId , double amount, long vehicleTypeId , 	String inTime, String outTime) {
		try {
			UserWallet userWallet = this.userWalletRepo.findByUserId(userId).orElse(null);
			if (userWallet != null && amount > 0) {
				double depositAmt=0,realAmt=0,bonusAmt=0;
				if (userWallet.getDeposit() + userWallet.getReal() + userWallet.getBonus() >= amount) {
					boolean saveFlag = true;
					if (userWallet.getDeposit() >= amount) {
						depositAmt = userWallet.getDeposit() - amount;
						userWallet.setDeposit(depositAmt);
					} 
					else {
						depositAmt = userWallet.getDeposit();
						double remainingAmount = amount - userWallet.getDeposit();
						userWallet.setDeposit(0);
						if (userWallet.getReal() >= remainingAmount) {
							realAmt = userWallet.getReal() - remainingAmount;
							userWallet.setReal(realAmt);
						} else {
							realAmt = userWallet.getReal();
							remainingAmount = remainingAmount - userWallet.getReal();
							userWallet.setReal(0);
							if (userWallet.getBonus() >= remainingAmount) {
								bonusAmt = userWallet.getBonus() - remainingAmount;
								userWallet.setBonus(bonusAmt);
							} 
						}
					}
		       if (saveFlag) {

		        ParkingDetails parkingDetails = this.parkingDetailsRepository.findBylocationIdAndVehicleId(parkingLocId, vehicleTypeId).orElse(null);
						if (parkingDetails != null) {
							this.userWalletRepo.save(userWallet);
							UserWallet agentWallet = this.userWalletRepo.findByUserId(parkingDetails.getParkingLocation().getUser().getId()).orElse(null);
							double percentageAmt = amount / parkingDetails.getAgentPercentage();
							agentWallet.setReal(agentWallet.getReal() + percentageAmt);
							this.userWalletRepo.save(agentWallet);

							// to cut amount and provide to Agent
							AgentTransHistory agentHistoryVo = parkBookingMapper.createAgentHitsoryVo(percentageAmt , userId);
							this.agentTransactionRepo.save(agentHistoryVo);

							ParkTransHistory parkTransVo = parkBookingMapper.createParkingHitsoryVo(amount, userId,"DR","Parking Booked" , "real");
							this.parkingTransRepo.save(parkTransVo);

							ParkBookingHistory bookingHistoryVo = parkBookingMapper.createParkingBookingHitsoryVo(parkingDetails, depositAmt,realAmt,bonusAmt, userId, inTime, outTime);
							this.parkBookingHistoryRepository.save(bookingHistoryVo);

							ParkingLocationDto sendLoc = new ParkingLocationDto(parkingLocId, parkingDetails.getParkingLocation().getLatitude(),
									                                  parkingDetails.getParkingLocation().getLongitude());

							return new EsResponse<>(IConstants.RESPONSE_ADD_PAYMENT, sendLoc , this.ENV.getProperty("bookins.success"));
						} else {
							System.out.println("Location is null or save flag is false " + saveFlag);
							//throw new Exception("Location is null or save  flag is false");
							return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR , this.ENV.getProperty("exception.internal.error"));
						}
					} else {
						System.out.println("User doesn't have sufficient amount");
						return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR , this.ENV.getProperty("booking.insufficient.amount"));
					}
				} else {
					System.out.println("user doesn't have sufficient amount to book ");
					System.out.println("User have deposit amount " + userWallet.getDeposit() + " bonus amount "
							                           + userWallet.getBonus() + " real amount " + userWallet.getReal());
					return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR , this.ENV.getProperty("booking.insufficient.amount"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internal.error"));
		}

		return null;

	}
	
	
	
	@Transactional(readOnly = false,   propagation = Propagation.REQUIRES_NEW)
	public EsResponse<PaymentDTO> cancelBookingAmount( long  parkBookId, long userId,double  latitude, double  longitude){
		try {
			ParkBookingHistory parkingHistory =  parkBookingHistoryRepository.findByParkingBookingIdAndUserId(parkBookId,userId).orElse(null);
			
			
			if(parkingHistory!=null) {
				
				ParkingLocation location =this.parkingLocationRepository.findByParkingLocId(parkingHistory.getParkingLocationId()).orElse(null);
				
				double distance =GFGTest.distance(Double.parseDouble(location.getLatitude()), latitude, Double.parseDouble(location.getLongitude()), longitude);
				
				System.out.println("distance "+distance);
				
				if(distance>0.100) {
					UserWallet userWallet = this.userWalletRepo.findByUserId(userId).orElse(null);
					if(userWallet!=null) {
						ParkTransHistory parkTransVo = parkBookingMapper.createParkingHitsoryVo(parkingHistory.getBonusAmt()+parkingHistory.getDepositAmt()+parkingHistory.getRealAmt(), userId,"CR","Parking Cancelled" , "real");
						
						userWallet.setBonus(userWallet.getBonus()+parkingHistory.getBonusAmt());
						userWallet.setDeposit(userWallet.getDeposit()+parkingHistory.getDepositAmt());
						userWallet.setReal(userWallet.getReal()+parkingHistory.getRealAmt());
						parkingHistory.setStatus(IConstants.ParkingStatus.CANCEL);
						this.parkBookingHistoryRepository.save(parkingHistory);
						this.userWalletRepo.save(userWallet);
						this.parkingTransRepo.save(parkTransVo);
						return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("booking.cancel.success"));
					}
				}else {
					return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("booking.cancel.notpossible"));
				}
				
			}else{
				return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("booking.cancel.noexists"));
			}
		}catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internal.error"));
		}
		return null;
		
	}
	
	
	
	
	public void initMonthlyBooking(MonthlyBookingDTO   monthlyBookingDto){
		
		
		
	}
	
	
	

}
