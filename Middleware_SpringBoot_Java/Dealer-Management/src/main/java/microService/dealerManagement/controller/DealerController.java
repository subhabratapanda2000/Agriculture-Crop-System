package microService.dealerManagement.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microService.dealerManagement.models.CropRequirements;
import microService.dealerManagement.models.DealerBasicInfo;
import microService.dealerManagement.models.DealerDetails;
import microService.dealerManagement.models.PasswordChange;
import microService.dealerManagement.models.SendOfferDetails;
import microService.dealerManagement.rabbitmq.MessagingConfig;
import microService.dealerManagement.service.DealerService;

@RestController
@CrossOrigin
@RequestMapping("/dealer")
public class DealerController {
//	Logger logger=LoggerFactory.getLogger(DealerController.class);
//	
	DealerController ob;
	@Autowired
	private DealerService service;
	
	 @Autowired
	    private RabbitTemplate template;

	    @PostMapping("/sendOffer/{did}")
	    public ResponseEntity<Object> bookOrder(@RequestBody CropRequirements req, @PathVariable int did) {
	    	Optional<DealerDetails> op = service.findById(did);
			if(op.isPresent()) {
				DealerDetails dealer=op.get();
				if(dealer.getRole().equals("ROLE_DEALER")) { 
					 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
					   LocalDateTime now = LocalDateTime.now(); 
					   String dt=dtf.format(now);  
					   String date=dt.substring(0, 10);
					   String time=dt.substring(10);
					DealerBasicInfo dealerInfo=new DealerBasicInfo(dealer.getName(), dealer.getMobileNo(), dealer.getAddress());
					req.setId(UUID.randomUUID().toString());
					SendOfferDetails offer = new SendOfferDetails(date, time, req, dealerInfo);
			        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, offer);
					return new ResponseEntity<Object>("Your offer has been send to the farmers", HttpStatus.OK);
				   }
				   else {
					   return new ResponseEntity<Object>("This id not exist", HttpStatus.NOT_FOUND);
				   }
				
			}else {
				return new ResponseEntity<Object>("This id not exist", HttpStatus.NOT_FOUND);
			}
	       
	    }
	
	 
	    @PostMapping("/create")
		public ResponseEntity<String> createDealer(@RequestBody DealerDetails dealer) {
			try {
				
				Optional<DealerDetails> op1=service.findByMobile(dealer.getMobileNo());
				Optional<DealerDetails> op2=service.findByUserName(dealer.getUserName());
				if(op1.isPresent()) {
					DealerDetails dr=op1.get();
					if(dr.isActive()) {
					return new ResponseEntity<String>("This Mobile Number Has been used already", HttpStatus.BAD_REQUEST);
				   }
					else
					{
					dr.setActive(true);
					service.save(dr);
					return new ResponseEntity<String>("Welcome Back! "+dr.getName()+", Your Data is activated successfully.", HttpStatus.OK);
					}
				}
				else if(op2.isPresent()) {
					return new ResponseEntity<String>("This UserName Has been used already", HttpStatus.BAD_REQUEST);
				}
				else {
					String regex = "(0/91)?[7-9][0-9]{9}";
					if(dealer.getMobileNo().matches(regex)) {
						int id=(service.count())+10;
						boolean flag=true;
						while(flag) {
							Optional<DealerDetails> op = service.findById(id);
							if(op.isPresent()) {
								id++;
							}
							else {
								flag=false;
							}	
						}
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
						LocalDateTime now = LocalDateTime.now(); 
						String dt=dtf.format(now);  
						String date=dt.substring(0, 10);
						dealer.setJoinDate(date);
						dealer.setRole("ROLE_DEALER");
						dealer.setFid(id);
						dealer.setPrimeMember(false);
						dealer.setActive(true);
						service.save(dealer);
						System.out.println("Save...");
						System.out.println(dealer);
						return new ResponseEntity<String>("Dealer add successfully", HttpStatus.CREATED);
						}
					else {
							return new ResponseEntity<String>("Invalid Phone Number", HttpStatus.BAD_GATEWAY);
						}
				}
			}catch(Exception e) {
//				logger.trace(e.getMessage());
				return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@GetMapping("/getById/{id}")
		public ResponseEntity<Object> findById(@PathVariable("id") int id) {
			Optional<DealerDetails> op = service.findById(id);
			if(op.isPresent()) {
				DealerDetails dealer=op.get();
				if(dealer.getRole().equals("ROLE_DEALER")) { 
					return new ResponseEntity<Object>(dealer, HttpStatus.OK);
				   }
				   else {
					   return new ResponseEntity<Object>("This id not exist", HttpStatus.NOT_FOUND);
				   }
				
			}else {
				return new ResponseEntity<Object>("This id not exist", HttpStatus.NOT_FOUND);
			}
		}
		
		@GetMapping("/findByUserName/{userName}")
		public ResponseEntity<Object> findByUserName(@PathVariable("userName") String userName) {
			Optional<DealerDetails> op = service.findByUserName(userName);
			if(op.isPresent()) {
				DealerDetails dealer=op.get();
				return new ResponseEntity<Object>(dealer, HttpStatus.OK);
			}else {
				return new ResponseEntity<Object>("This id not exist", HttpStatus.NOT_FOUND);
			}
		}
		

		
		@GetMapping("/alldealer")
		public ResponseEntity<Object> getAll(){
			try {
			List<DealerDetails> list=new ArrayList<>();
			service.findAll().forEach(list::add);
			if(!list.isEmpty()) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Object>("There is no Farmers", HttpStatus.NO_CONTENT);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
		}
		
		@GetMapping("/allPrimeDealer")
		public ResponseEntity<Object> allPrimeDealer(){
			try {
			List<DealerDetails> list=new ArrayList<>();
			service.findAllPrimeDealers().forEach(list::add);
			if(!list.isEmpty()) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Object>("There is no Prime Customer", HttpStatus.NO_CONTENT);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
		}
		
		
		@GetMapping("/findallactivedealers")
		public ResponseEntity<Object> getAllActiveFarmer(){
			try {
			List<DealerDetails> list=new ArrayList<>();
			service.findActiveDealer(true).forEach(list::add);
			if(!list.isEmpty()) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Object>("There is no active Farmers", HttpStatus.NO_CONTENT);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
		}
		
		@DeleteMapping("deletedealer/{id}")
		public ResponseEntity<String> deleteDealerByID(@PathVariable("id") int id) {
			try {
			Optional<DealerDetails> op = service.findById(id);
			if(op.isPresent()) {
				service.deleteById(id);
			    return new ResponseEntity<String>("Delete Successfully", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("There is no Dealer Data", HttpStatus.NO_CONTENT);
			}
			}catch(Exception e) {
				return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		
		@PutMapping("/update/{id}")
		public ResponseEntity<Object> updateFarmerById(@PathVariable("id") int id, @RequestBody DealerDetails dealer)
		{
			try {
			Optional<DealerDetails> op=service.findById(id);
			if(op.isPresent())
			{
//				String pass=dealer.getPassword();
//				farmer.setPassword(getPasswordEncoder.encode(pass));
				if(op.get().isActive() && op.get().getRole().equals("ROLE_DEALER")) {
					Optional<DealerDetails> op1=service.findByMobile(dealer.getMobileNo());
					Optional<DealerDetails> op2=service.findByUserName(dealer.getUserName());
					if(op1.isPresent() && !op.get().getMobileNo().equals(dealer.getMobileNo())) {
						return new ResponseEntity<Object>("This Mobile Number Has been used already", HttpStatus.BAD_REQUEST);
					}
					else if(op2.isPresent() && !op.get().getUserName().equals(dealer.getUserName())) {
						return new ResponseEntity<Object>("This user name Has been used already", HttpStatus.BAD_REQUEST);
					}
					else {
						String regex = "(0/91)?[6-9][0-9]{9}";
						if(dealer.getMobileNo().matches(regex)) {
							DealerDetails dr=op.get();
							dealer.setActive(true);
							dealer.setPassword(dr.getPassword());
							dealer.setFid(dr.getFid());
							dealer.setRole("ROLE_DEALER");
							dealer.setJoinDate(dr.getJoinDate());
							dealer.setPrimeMember(dr.isPrimeMember());
							service.save(dealer);
							return new ResponseEntity<Object>("The Data is update successfully of "+dealer.getName(), HttpStatus.OK);
						}
						else
						{
							return new ResponseEntity<Object>("Invalid Phone Number", HttpStatus.BAD_GATEWAY);
						}
						}
				}
				else {
				    return new ResponseEntity<Object>("NOT FOUND", HttpStatus.NOT_FOUND);
				 }
		
			}
			else
			{
				return new ResponseEntity<Object>("NOT FOUND", HttpStatus.NOT_FOUND);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>(e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@PutMapping("/deactivate/{id}")
		public ResponseEntity<Object> deactivateDealerById(@PathVariable("id") int id)
		{
			try {
			Optional<DealerDetails> op=service.findById(id);
			if(op.isPresent())
			{
				DealerDetails fr=op.get();
				if(fr.getRole().equals("ROLE_DEALER") && fr.isActive()) {
					fr.setActive(false);
					service.save(fr);
					return new ResponseEntity<Object>("The Data is deactivate successfully of "+fr.getName(), HttpStatus.OK);
					
				   }
				   else {
					   return new ResponseEntity<Object>("NOT FOUND", HttpStatus.NOT_FOUND);
				   }
				
			}
			else
			{
				return new ResponseEntity<Object>("NOT FOUND", HttpStatus.NOT_FOUND);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@PutMapping("/changePassword/{id}")
		public ResponseEntity<Object> changePassword(@PathVariable("id") int id, @RequestBody PasswordChange pass)
		{
			try {
			Optional<DealerDetails> op=service.findById(id);
			if(op.isPresent())
			{
				DealerDetails fr=op.get();
				if(fr.getRole().equals("ROLE_DEALER") && fr.isActive()) {
						fr.setPassword(pass.getNewPassword());
						service.save(fr);
						return new ResponseEntity<Object>("Your Password is updated successfully ", HttpStatus.OK);						
				   }
				 else {
				   return new ResponseEntity<Object>(id+ "not exist", HttpStatus.NOT_FOUND);
				 }
			}
			else
			{
				return new ResponseEntity<Object>(id+ "not exist", HttpStatus.NOT_FOUND);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>(e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@GetMapping("/payForPrime/{id}")
		public ResponseEntity<Object> payForPrime(@PathVariable("id") int id) {
			try {
				Optional<DealerDetails> op=service.findById(id);
				if(op.isPresent())
				{
					DealerDetails fr=op.get();
					if(fr.getRole().equals("ROLE_DEALER") && fr.isActive()) {
						return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8060/payment/pgredirect/"+id)).build();
					   }
					 else {
						 return new ResponseEntity<Object>(id+ " not exist", HttpStatus.NOT_FOUND);
					 }
				}
				else
				{
					return new ResponseEntity<Object>(id+ " not exist", HttpStatus.NOT_FOUND);
				}
				}catch(Exception e) {
					return new ResponseEntity<Object>("Some Internal Problem, "+e, HttpStatus.INTERNAL_SERVER_ERROR);
				}
		}
		
		
//		@PutMapping("/activate/{id}")
//		public ResponseEntity<Object> activateDealerById(@PathVariable("id") int id)
//		{
//			try {
//			Optional<DealerDetails> op=service.findById(id);
//			if(op.isPresent())
//			{
//				DealerDetails fr=op.get();
//				fr.setActive(true);
//				service.save(fr);
//				return new ResponseEntity<Object>("The Data is deactivate successfully of "+fr.getName(), HttpStatus.OK);
//				
//			}
//			else
//			{
//				return new ResponseEntity<Object>("NOT FOUND", HttpStatus.NOT_FOUND);
//			}
//			}catch(Exception e) {
//				return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		}


}
