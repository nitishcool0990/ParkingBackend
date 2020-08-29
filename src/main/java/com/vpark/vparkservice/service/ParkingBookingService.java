package com.vpark.vparkservice.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.dto.CancelBookingDTO;
import com.vpark.vparkservice.dto.CashFreeDTO;
import com.vpark.vparkservice.dto.DoneBookingDTO;
import com.vpark.vparkservice.dto.InitBookingDTO;
import com.vpark.vparkservice.dto.MyParkingHistoryDTO;
import com.vpark.vparkservice.dto.ParkingLocationDTO;
import com.vpark.vparkservice.dto.PaymentDTO;
import com.vpark.vparkservice.dto.PaymetGateWayDTO;
import com.vpark.vparkservice.dto.VehicleDTO;
import com.vpark.vparkservice.entity.AgentTransHistory;
import com.vpark.vparkservice.entity.BonusCodeUsers;
import com.vpark.vparkservice.entity.BonusCodes;
import com.vpark.vparkservice.entity.CashFreeTransHistory;
import com.vpark.vparkservice.entity.FavouriteParking;
import com.vpark.vparkservice.entity.ParkBookingHistory;
import com.vpark.vparkservice.entity.ParkTransHistory;
import com.vpark.vparkservice.entity.ParkedVehicleCount;
import com.vpark.vparkservice.entity.ParkingDetails;
import com.vpark.vparkservice.entity.ParkingLocation;
import com.vpark.vparkservice.entity.UserWallet;
import com.vpark.vparkservice.entity.Vehicle;
import com.vpark.vparkservice.mapper.ParkBookingMapper;
import com.vpark.vparkservice.model.EsResponse;
import com.vpark.vparkservice.repository.IAgentTransHistoryRepository;
import com.vpark.vparkservice.repository.IBonusCodeRepository;
import com.vpark.vparkservice.repository.IBonusCodeUserRepository;
import com.vpark.vparkservice.repository.ICashFreeTransHistory;
import com.vpark.vparkservice.repository.IFavouriteParkingRepository;
import com.vpark.vparkservice.repository.IParkBookingHistoryRepository;
import com.vpark.vparkservice.repository.IParkTransHistoryRepository;
import com.vpark.vparkservice.repository.IParkedVehicleCountRepository;
import com.vpark.vparkservice.repository.IParkingDetailsRepository;
import com.vpark.vparkservice.repository.IParkingLocationRepository;
import com.vpark.vparkservice.repository.IUserWalletRepository;
import com.vpark.vparkservice.repository.IVehicleRepository;
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
	   private IVehicleRepository vehicleRepository;
	 
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
	 
	 @Autowired
	  private IParkedVehicleCountRepository parkedVehicleCountRepository ;
	 
	 @Autowired
	 private IBonusCodeRepository bonusCodeRepo;
	 
	 @Autowired
	 private IBonusCodeUserRepository bonusCodeUserRepo;
	 
	 @Autowired
	 private IFavouriteParkingRepository   favouriteParkingRepository  ;
	 
	 @Autowired
	 private ModelMapper modelMapper;
	    
	
	 
	public EsResponse<ParkingLocationDTO> getParkingInfo(long parkingId, long vehicleTypeId , long userId) {
		try {
			List<Object[]> objList = parkingLocationRepository.getParkingInfo(parkingId, vehicleTypeId) ;
			Optional<FavouriteParking>  favouParking  = favouriteParkingRepository.findByLocationIdAndUserId(parkingId, userId) ;
			  List<Vehicle> vehicleVos   = vehicleRepository.findActiveUserVehiclesByVehicleType(vehicleTypeId , userId);
		
			
			if (!objList.isEmpty()) {
				
				ParkingLocationDTO parkingLocDto = this.parkBookingMapper.convertToParkingLocationDTO(objList);
			    
				if(null !=vehicleVos  && vehicleVos.size() >0){
					
					List<VehicleDTO> vehicleDtos = vehicleVos .stream()
	      			  .map((vehicleVo) -> {
	      				VehicleDTO  vehicleDto =  modelMapper.map(vehicleVo , VehicleDTO.class);
	      				return vehicleDto ;
	      			  })
	      			  .collect(Collectors.toList());
					parkingLocDto.setVehicleList(vehicleDtos);
				}
				
				if(favouParking.isPresent()){
					parkingLocDto.setFavParking(true);
				}
				if(parkingLocDto.getRemainingParking() >0) {
					return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, parkingLocDto , this.ENV.getProperty("booking.parking.details"));
				}else{
					return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, parkingLocDto , this.ENV.getProperty("booking.parking.notavaible"));
				}
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
	
	
	
	public EsResponse<PaymentDTO> initBooking( InitBookingDTO  initBookingDto , long userId ){
		try {
			
			UserWallet userWallet = this.userWalletRepo.findByUserId(userId).orElse(null);
			 Optional<Vehicle>  vehicleVo   = this.vehicleRepository.findById(initBookingDto.getVehicleId());
			 
			 if(vehicleVo.isPresent()   &&  userWallet!=null ){
				 
			 ParkedVehicleCount parkedVehicleCountVo  = this.parkedVehicleCountRepository.findByParkingLocationIdAndVehicleTypeId(  initBookingDto.getParkLocId() ,
					 vehicleVo.get().getVehicleType().getId() ) ;
			 
			 if(parkedVehicleCountVo.getRemainingSpace() == 0)
			        return new EsResponse<>(IConstants.RESPONSE_STATUS_OK , this.ENV.getProperty("booking.parking.notavaible"));
			 }
			 else {
				  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.not.found"));
			}
				 
		
			   List<Object[]> objList = this.parkingLocationRepository.getParkingInfo(initBookingDto.getParkLocId() ,vehicleVo.get().getVehicleType().getId());
		        ParkingLocationDTO location = this.parkBookingMapper.convertToParkingLocationDTO(objList);
		        
				LocalTime fromLOCDateTime = LocalTime.of(Integer.parseInt(location.getOpenTime().split(":")[0]), Integer.parseInt(location.getOpenTime().split(":")[1]));
				LocalTime toLOCDateTime =  LocalTime.of(Integer.parseInt(location.getCloseTime().split(":")[0]),Integer.parseInt(location.getCloseTime().split(":")[1]));
				
				long hours = fromLOCDateTime.until( initBookingDto.getFromDate(), ChronoUnit.HOURS );
				fromLOCDateTime = fromLOCDateTime.plusHours( hours );
				long minutes = fromLOCDateTime.until( initBookingDto.getToDate(), ChronoUnit.MINUTES );
				
				if(hours<0 || minutes<0) {
					 return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("booking.parking.open"));
				}
				
				hours = toLOCDateTime.until( initBookingDto.getToDate(), ChronoUnit.HOURS );
				toLOCDateTime = toLOCDateTime.plusHours( hours );
				minutes = toLOCDateTime.until( initBookingDto.getToDate(), ChronoUnit.MINUTES );
				if(hours>0|| minutes >0) {
					return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("booking.parking.close"));
				}
			
				LocalTime currentTime =LocalTime.now();
				
				hours = currentTime.until( initBookingDto.getFromDate(), ChronoUnit.HOURS );
				fromLOCDateTime = currentTime.plusHours( hours );
				minutes = currentTime.until( initBookingDto.getFromDate(), ChronoUnit.MINUTES );
				
				if(hours<0|| minutes <0) {
					  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("booking.time.old"));
				}else {
					if((hours>= location.getAdvanceBookingHr() && minutes>0)  ) {
						 return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("booking.not.allowed"));
					}
				}
				
              
                double mins = initBookingDto.getFromDate().until( initBookingDto.getToDate(), ChronoUnit.MINUTES );
                long hourBooking = (long) (mins/60);
                long minsBooking = (long) (mins%60);
                if(minsBooking >0) {
					hourBooking = hourBooking+1;
				}
				if(IConstants.ChargesType.PERHOUR.toString().equalsIgnoreCase(location.getChargesType())) {
					double preHourVal =0;
					for(double slotTimeValue : location.getHourlyTimeSlot().values() ) {
						preHourVal = slotTimeValue;
					}
					if(preHourVal*hourBooking >= location.getMaxLimit()) {
						initBookingDto.setAmt( location.getMaxLimit());
					}else {
						initBookingDto.setAmt(preHourVal*hourBooking);
					}
				}else {
					
					for(Map.Entry<Double, Double> slotTime : location.getHourlyTimeSlot().entrySet() ) {
						if(slotTime.getKey() >=hourBooking ) {
							initBookingDto.setAmt( slotTime.getValue());
							break;
						}
					}
				}
				
				//bonus check
				double bonusAmt =0;
				boolean bonusUseFlag =false;
				BonusCodes bonus = this.bonusCodeRepo.findByBonusCode(initBookingDto.getBonusCode()).orElse(null);
				if(null != bonus) {
					List<Object[]> userBonusList = this.bonusCodeUserRepo.findByBonusCodeByuserId(userId, bonus.getId());
					if(null !=userBonusList) {
						Object[] userBonusUses = userBonusList.get(0);
						if(bonus.getMaxUsedCount() > (long)userBonusUses[2]) {
							bonusUseFlag =true;
						}
						
					}else {
						bonusUseFlag =true;
					}
					if(bonusUseFlag) {
						if(bonus.getDiscountAmt()>0) {
							bonusAmt = bonus.getDiscountAmt();
						}else {
							bonusAmt = bonusAmt*(bonus.getDiscountPerc()/100);
						}
					}
					initBookingDto.setAmt(initBookingDto.getAmt()-bonusAmt );
				}
				
				PaymentDTO paymentDto = getWalletInfo(userWallet , initBookingDto.getAmt()  , initBookingDto.getParkLocId());
				
				  return new EsResponse<>(IConstants.RESPONSE_STATUS_OK ,  paymentDto , this.ENV.getProperty("booking.parking.init"));
			
		}catch(Exception e) {
			e.printStackTrace();
			  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internal.error"));
		}
	}
	
	
	
	private PaymentDTO getWalletInfo(UserWallet userWallet  , double amount  , long  parkingLocId ){
		
		PaymentDTO paymentDto = new PaymentDTO();
		PaymetGateWayDTO paymentGateWayDTO=null;
		
		paymentGateWayDTO=new PaymetGateWayDTO("Wallet", userWallet.getDeposit()+userWallet.getReal()+userWallet.getBonus());
		paymentDto.setParkingLocId(parkingLocId );
		paymentDto.setPayableAmt(amount);
		paymentDto.getPaymentGateWay().add(paymentGateWayDTO);
		
		if(userWallet.getDeposit()+userWallet.getReal()+userWallet.getBonus() <  amount) {
		
			 paymentGateWayDTO=new PaymetGateWayDTO("CashFree",amount- (userWallet.getDeposit()+userWallet.getReal()+userWallet.getBonus()));
			paymentDto.getPaymentGateWay().add(paymentGateWayDTO);
		}
		
		return paymentDto;
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
	@Transactional(readOnly = false,   propagation = Propagation.REQUIRED)
	public EsResponse<ParkingLocationDTO> doneBooking(DoneBookingDTO  doneBookingDto , long userId ) {
		try {
		Optional<Vehicle>  vehicleVo   = this.vehicleRepository.findById(doneBookingDto.getVehicleId());
	    UserWallet userWallet = this.userWalletRepo.findByUserId(userId).orElse(null);
			 
		if(vehicleVo.isPresent() && userWallet != null && doneBookingDto.getAmount() > 0 ){
				 
		 ParkedVehicleCount parkedVehicleCountVo  = this.parkedVehicleCountRepository.findByParkingLocationIdAndVehicleTypeId(  doneBookingDto.getParkingLocId() ,  
				                                                                          vehicleVo.get().getVehicleType().getId()) ;
		 
			if (parkedVehicleCountVo.getRemainingSpace() > 0) {
				double depositAmt=0,realAmt=0,bonusAmt=0;
				if (userWallet.getDeposit() + userWallet.getReal() + userWallet.getBonus() >= doneBookingDto.getAmount()) {
					boolean saveFlag = true;
					if (userWallet.getDeposit() >= doneBookingDto.getAmount()) {
						depositAmt =  doneBookingDto.getAmount();
						userWallet.setDeposit(userWallet.getDeposit() - doneBookingDto.getAmount());
					} 
					else {
						depositAmt = userWallet.getDeposit();
						double remainingAmount = doneBookingDto.getAmount() - userWallet.getDeposit();
						userWallet.setDeposit(0);
						if (userWallet.getReal() >= remainingAmount) {
							realAmt =  remainingAmount;
							userWallet.setReal(userWallet.getReal() - remainingAmount);
						} else {
							realAmt = userWallet.getReal();
							remainingAmount = remainingAmount - userWallet.getReal();
							userWallet.setReal(0);
							if (userWallet.getBonus() >= remainingAmount) {
								bonusAmt =  remainingAmount;
								userWallet.setBonus(userWallet.getBonus() - remainingAmount);
							} 
						}
					}
		       if (saveFlag) {

		        ParkingDetails parkingDetails = this.parkingDetailsRepository.findBylocationIdAndVehicleTypeId(doneBookingDto.getParkingLocId(), vehicleVo.get().getVehicleType().getId()).orElse(null);
						if (parkingDetails != null) {
							
							String remarks = "Parking Booked" ;
							
							if(doneBookingDto.isMonthlyBookingFlag())
								 remarks = "Monthly Parking Booked";
							
							ParkTransHistory parkTransVo = parkBookingMapper.createParkingHitsoryVo(doneBookingDto.getAmount(), userId,"DR", remarks , "real");
							this.parkingTransRepo.save(parkTransVo);
							
							ParkBookingHistory bookingHistoryVo = parkBookingMapper .createParkingBookingHitsoryVo(doneBookingDto, parkingDetails, depositAmt,
                                    realAmt, bonusAmt, userId, remarks);
                             ParkBookingHistory savedParkBookingHistoryVo = this.parkBookingHistoryRepository.save(bookingHistoryVo);
							
								if (doneBookingDto.isMonthlyBookingFlag()) {

								UserWallet agentWallet = this.userWalletRepo.findByUserId(parkingDetails.getParkingLocation().getUser().getId()).orElse(null);
								// double percentageAmt = doneBookingDto.getAmount() /  parkingDetails.getAgentPercentage();
								agentWallet.setReal(agentWallet.getReal() + doneBookingDto.getAmount());
									
								// Monthly booking cut amount and provide to Agent
								AgentTransHistory agentHistoryVo = parkBookingMapper.createAgentHitsoryVo( doneBookingDto.getAmount(), userId ,  parkingDetails.getParkingLocation().getUser().getId(),
											                                                         doneBookingDto.getParkingLocId(), savedParkBookingHistoryVo.getId(), remarks);
								
								parkedVehicleCountVo.setRemainingSpace(parkedVehicleCountVo.getRemainingSpace()-1);
								parkedVehicleCountVo.setTotalOccupied(parkedVehicleCountVo.getTotalOccupied() +1 );
								
								this.agentTransactionRepo.save(agentHistoryVo);
								this.userWalletRepo.save(agentWallet);
								this.parkedVehicleCountRepository.save(parkedVehicleCountVo);
								
								}
								
								BonusCodes bonusCode = this.bonusCodeRepo.findByBonusCode(doneBookingDto.getBonusCode()).orElse(null);
								if(null != bonusCode) {
									BonusCodeUsers userBonusCode = new BonusCodeUsers();
									userBonusCode.setBonusCodeId(bonusCode.getId());
									userBonusCode.setUserId(userId);
									userBonusCode.setStatus(IConstants.Status.ACTIVE);
									userBonusCode.setParkedBookId(savedParkBookingHistoryVo.getId());
									this.bonusCodeUserRepo.save(userBonusCode);
								}
								this.userWalletRepo.save(userWallet);
							ParkingLocationDTO sendLoc = new ParkingLocationDTO(savedParkBookingHistoryVo.getId(), parkingDetails.getParkingLocation().getLatitude(),
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
			else{
				 return  new EsResponse<>(IConstants.RESPONSE_FULL_PARKING,  this.ENV.getProperty("parking.full"));
			      }
			 }
			 else {
				  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.not.found"));
			     }
		} catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("exception.internal.error"));
		}

	}
	
	
	private void debitAmtFromUserWallet(UserWallet userWallet, double amt) {

		double depositAmt = 0, realAmt = 0, bonusAmt = 0;

		if (userWallet.getDeposit() >= amt) {
			depositAmt = amt;
			userWallet.setDeposit(userWallet.getDeposit() - amt);
		} else {
			depositAmt = userWallet.getDeposit();
			double remainingAmount = amt - userWallet.getDeposit();
			userWallet.setDeposit(0);
			if (userWallet.getReal() >= remainingAmount) {
				realAmt = remainingAmount;
				userWallet.setReal(userWallet.getReal() - remainingAmount);
			} else {
				realAmt = userWallet.getReal();
				remainingAmount = remainingAmount - userWallet.getReal();
				userWallet.setReal(0);
				if (userWallet.getBonus() >= remainingAmount) {
					bonusAmt = remainingAmount;
					userWallet.setBonus(userWallet.getBonus() - remainingAmount);
				}
			}
		}
	}
	
	
	
	@Synchronized
	@Transactional(readOnly = false,   propagation = Propagation.REQUIRED)
	public EsResponse<PaymentDTO> cancelBookingAmount( CancelBookingDTO  cancelBookingDto , long userId){
		try {
			boolean cancelFlag = false ;
			double amtDeduct = 0;
			ParkBookingHistory parkingBookingHistory =  parkBookingHistoryRepository.findByParkingBookingIdAndUserId(cancelBookingDto.getBookingId() ,userId).orElse(null);
		
			if(parkingBookingHistory!=null) {
				
				if(parkingBookingHistory.getRemarks().equals("Monthly Parking Booked")){
					
					if(parkingBookingHistory.getFromDate().isAfter(LocalDate.now())){
						
						cancelFlag = true ;
						monthlyBookingCancel(parkingBookingHistory.getId() , 0);
					}
					
					else if(parkingBookingHistory.getFromDate().plusDays(1).isEqual(LocalDate.now())  || 
							                                                 parkingBookingHistory.getFromDate().isEqual(LocalDate.now())){
						
						 cancelFlag = true ;
						Optional<ParkingDetails> parkDetailsVo  =      this.parkingDetailsRepository.findById(parkingBookingHistory.getParkingDetailsId()) ;
						
					    if(parkDetailsVo.isPresent()){
					    	
						if(parkingBookingHistory.getFromDate().plusDays(1).isEqual(LocalDate.now()))
						     amtDeduct = parkDetailsVo.get().getMaxLimit()* 2;
						else
						   amtDeduct = parkDetailsVo.get().getMaxLimit();
					    }	
					    
					    monthlyBookingCancel(parkingBookingHistory.getId()   , amtDeduct);
					}
					else{
						return new EsResponse<>(IConstants.RESPONSE_STATUS_OK, this.ENV.getProperty("booking.cancel.notpossible"));
					}
				}	
				else {
					ParkingLocation location = this.parkingLocationRepository.findByParkingLocId(parkingBookingHistory.getParkingLocationId()).orElse(null);

					double distance = GFGTest.distance(Double.parseDouble(location.getLatitude()),cancelBookingDto.getLatitude(), 
							                        Double.parseDouble(location.getLongitude()), cancelBookingDto.getLongitude());
					
					System.out.println("distance " + distance);
					if (distance > 0.100)
						cancelFlag = true;
				}
				
				if(cancelFlag) {
					
					UserWallet userWallet = this.userWalletRepo.findByUserId(userId).orElse(null);
					 Optional<Vehicle>  vehicleVo   = this.vehicleRepository.findById(parkingBookingHistory.getVehicleId());
			
					if(userWallet!=null  &&  vehicleVo.isPresent()) {
						
						ParkTransHistory parkTransVo = parkBookingMapper.createParkingHitsoryVo(parkingBookingHistory.getBonusAmt()+parkingBookingHistory.getDepositAmt()+parkingBookingHistory.getRealAmt(), userId,"CR","Parking Cancelled" , "Real");
						
						ParkedVehicleCount parkedVehicleCountVo  = this.parkedVehicleCountRepository.findByParkingLocationIdAndVehicleTypeId(  parkingBookingHistory.getParkingLocationId() ,
		                        vehicleVo.get().getVehicleType().getId() ) ;
						
                 	
						userWallet.setBonus(userWallet.getBonus()+parkingBookingHistory.getBonusAmt());
						userWallet.setDeposit(userWallet.getDeposit()+parkingBookingHistory.getDepositAmt());
						userWallet.setReal(userWallet.getReal()+parkingBookingHistory.getRealAmt());
						
						parkingBookingHistory.setStatus(IConstants.ParkingStatus.CANCEL);
						
						parkedVehicleCountVo.setTotalOccupied(parkedVehicleCountVo.getTotalOccupied()-1);
						parkedVehicleCountVo.setRemainingSpace(parkedVehicleCountVo.getRemainingSpace()+1);
						
						BonusCodeUsers userBonus = this.bonusCodeUserRepo.findByParkedBookId(parkingBookingHistory.getId()).orElse(null);
						if(null !=userBonus) {
							userBonus.setStatus( IConstants.Status.INACTIVE);
							userBonus.setModifiedDate( LocalDateTime.now());
							this.bonusCodeUserRepo.save(userBonus);
						}
						
						this.parkBookingHistoryRepository.save(parkingBookingHistory);
						this.userWalletRepo.save(userWallet);
						this.parkingTransRepo.save(parkTransVo);
						this.parkedVehicleCountRepository.save(parkedVehicleCountVo);
						
						if(amtDeduct > 0 ){
							debitAmtFromUserWallet(userWallet  , amtDeduct);
							ParkTransHistory parkTransVoDR = parkBookingMapper.createParkingHitsoryVo(amtDeduct , userId,  "DR","Monthly Booking Amt Paid" , "Real");
							this.parkingTransRepo.save(parkTransVoDR);
							this.userWalletRepo.save(userWallet);
						}
						
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
	
	
public  void monthlyBookingCancel( long bookingId , double amtDeduct){
		
		AgentTransHistory agentTransHitoryVo  = this.agentTransactionRepo.findByBookingId(bookingId);
		
		 UserWallet agentWallet = this.userWalletRepo.findByUserId(agentTransHitoryVo.getAgentId()).orElse(null);
		 
		 agentWallet.setReal(agentWallet.getReal() - agentTransHitoryVo.getAmt());
		 agentWallet.setReal(agentWallet.getReal() +amtDeduct );
		 this.userWalletRepo.save(agentWallet);
		 
		 AgentTransHistory agentTranxHistoryEntity = new AgentTransHistory() ;
		 
		 BeanUtils.copyProperties(agentTransHitoryVo, agentTranxHistoryEntity);
		 agentTranxHistoryEntity.setId(0);
		 agentTranxHistoryEntity.setCrdr("DR");
		 agentTranxHistoryEntity.setRemarks("Monthly booking Canceled");
		 this.agentTransactionRepo.save(agentTranxHistoryEntity);
		 
		 if(amtDeduct > 0){
	    AgentTransHistory agentTranxHistoryCr = new AgentTransHistory() ;
		 BeanUtils.copyProperties(agentTransHitoryVo , agentTranxHistoryCr);
		 agentTranxHistoryCr.setId(0);
		 agentTranxHistoryCr.setAmt(amtDeduct);
		 agentTranxHistoryCr.setCrdr("CR");
		 agentTranxHistoryCr.setRemarks("Monthly Booking Amt Paid");
		 this.agentTransactionRepo.save(agentTranxHistoryCr);
		 }
		  
	}
	
	
	
	public EsResponse<PaymentDTO> initMonthlyBooking(InitBookingDTO   monthlyBookingDto  , long userId ){
		try{
	    Optional<Vehicle>  vehicleVo   = this.vehicleRepository.findById(monthlyBookingDto.getVehicleId());
	    
	    UserWallet userWallet = this.userWalletRepo.findByUserId(userId).orElse(null);
	    
	    if(vehicleVo.isPresent()  && null != userWallet){
	    	
	     ParkedVehicleCount parkedVehicleCountVo  = this.parkedVehicleCountRepository.findByParkingLocationIdAndVehicleTypeId(  monthlyBookingDto.getParkLocId() ,
			                                                                              vehicleVo.get().getVehicleType().getId() ) ;
	  
	     if(null != parkedVehicleCountVo  && parkedVehicleCountVo.getRemainingSpace() >0 ){
	    	 
	    	 PaymentDTO paymentDto = getWalletInfo(userWallet , monthlyBookingDto.getAmt() ,  monthlyBookingDto.getParkLocId());
	    	 
	    	  return new EsResponse<>(IConstants.RESPONSE_STATUS_OK ,  paymentDto , this.ENV.getProperty("booking.parking.init"));
	    	
	     }
	     else {
	    	 return  new EsResponse<>(IConstants.RESPONSE_FULL_PARKING,  this.ENV.getProperty("parking.full"));
	         } 
	    }
	    else {
			  return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR, this.ENV.getProperty("user.not.found"));
		     }
		} 
		catch (Exception e) {
			e.printStackTrace();
			return new EsResponse<>(IConstants.RESPONSE_STATUS_ERROR ,  this.ENV.getProperty("exception.internal.error"));
		}
	
		
	}
	
	
	

}
