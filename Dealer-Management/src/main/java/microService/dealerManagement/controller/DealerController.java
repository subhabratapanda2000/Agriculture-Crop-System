package microService.dealerManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import microService.dealerManagement.models.DealerDetails;
import microService.dealerManagement.service.DealerService;

@RestController
@CrossOrigin
@RequestMapping("/dealer")
public class DealerController {
//	Logger logger=LoggerFactory.getLogger(DealerController.class);
//	
	
	@Autowired
	private DealerService service;
	
	 
	    @PostMapping("/create")
		public ResponseEntity<String> createDealer(@RequestBody DealerDetails dealer) {
			try {
				//Auto Id generate
//				String pass=farmer.getPassword();
//				farmer.setPassword(getPasswordEncoder.encode(pass));
//				System.out.println(getPasswordEncoder.encode(pass)+ " "+pass);
				Optional<DealerDetails> op1=service.findByMobile(dealer.getMobileNo());
				Optional<DealerDetails> op2=service.findByUserName(dealer.getUserName());
				if(op1.isPresent()) {
					return new ResponseEntity<String>("This Mobile Number Has been used already", HttpStatus.BAD_REQUEST);
				}
				if(op2.isPresent()) {
					return new ResponseEntity<String>("This UserName Has been used already", HttpStatus.BAD_REQUEST);
				}
				else {
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
				dealer.setRole("ROLE_DEALER");
				dealer.setFid(id);
				dealer.setActive(true);
				service.save(dealer);
			System.out.println("Save...");
			System.out.println(dealer);
			return new ResponseEntity<String>("Dealer add successfully", HttpStatus.CREATED);
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
				DealerDetails fr=op.get();
				if(fr.getRole().equals("ROLE_DEALER")) {
					dealer.setPassword(fr.getPassword());
					dealer.setFid(fr.getFid());
					dealer.setRole("ROLE_DEALER");
					service.save(dealer);
					return new ResponseEntity<Object>("The Data is update successfully of "+dealer.getName(), HttpStatus.OK);
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
				if(fr.getRole().equals("ROLE_DEALER")) {
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
		
		@PutMapping("/activate/{id}")
		public ResponseEntity<Object> activateDealerById(@PathVariable("id") int id)
		{
			try {
			Optional<DealerDetails> op=service.findById(id);
			if(op.isPresent())
			{
				DealerDetails fr=op.get();
				fr.setActive(true);
				service.save(fr);
				return new ResponseEntity<Object>("The Data is deactivate successfully of "+fr.getName(), HttpStatus.OK);
				
			}
			else
			{
				return new ResponseEntity<Object>("NOT FOUND", HttpStatus.NOT_FOUND);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}


}
