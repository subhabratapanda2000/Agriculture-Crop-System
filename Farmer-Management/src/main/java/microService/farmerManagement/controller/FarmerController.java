package microService.farmerManagement.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import microService.farmerManagement.models.CropDetails;
import microService.farmerManagement.models.FarmerContactDetails;
import microService.farmerManagement.models.FarmerDetails;
import microService.farmerManagement.models.FarmerDetailsWithCrops;
import microService.farmerManagement.models.PasswordChange;
import microService.farmerManagement.models.SendOfferDetails;
import microService.farmerManagement.rabbitmq.ReceiverMsg;
import microService.farmerManagement.service.CropService;
import microService.farmerManagement.service.FarmerService;

@RestController
@CrossOrigin
@Component
@RequestMapping("/farmer")
public class FarmerController {
	//Logger logger=LoggerFactory.getLogger(FarmerController.class);
	
	
	@Autowired
	private FarmerService service;
	
	@Autowired
	private CropService cropService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	 @GetMapping("/getOffers")
	    public ResponseEntity<Object> getOffers(){
		 List<SendOfferDetails> offer=ReceiverMsg.offer;
		 if(offer!=null) {
			 //System.out.println(ReceiverMsg.s);
			 return new ResponseEntity<Object>(offer, HttpStatus.OK);
		 }
		 else {
			 return new ResponseEntity<Object>("No Offers!", HttpStatus.NOT_FOUND);
		 }
		 
	 }

	    
	    @GetMapping("/farmerDetailsWithCrops/{userId}")
	    public ResponseEntity<Object> getFarmerDetailsWithCrops(@PathVariable("userId") int userId){
	    	List<CropDetails> ls= cropService.getCrops(userId);
	    	Optional<FarmerDetails> op = service.findById(userId);
			if(op.isPresent()) {
			   FarmerDetails farmer=op.get();
			   FarmerDetailsWithCrops fdc=new  FarmerDetailsWithCrops(farmer.getFid(), farmer.getName(), farmer.getAddress(), farmer.getMobileNo(), ls);
				return new ResponseEntity<Object>(fdc, HttpStatus.OK);
			}else {
				return new ResponseEntity<Object>("This id not exist", HttpStatus.NOT_FOUND);
			}
		}
	
	    @PostMapping("/create")
		public ResponseEntity<String> insertFarmer(@RequestBody FarmerDetails farmer) {
			try {
				Optional<FarmerDetails> op1=service.findByMobile(farmer.getMobileNo());
				Optional<FarmerDetails> op2=service.findByUserName(farmer.getUserName());
				if(op1.isPresent()) {
					FarmerDetails fr=op1.get();
					if(fr.isActive()) {
					return new ResponseEntity<String>("This Mobile Number Has been used already", HttpStatus.BAD_REQUEST);
				   }
					else
					{
					fr.setActive(true);
					service.save(fr);
					return new ResponseEntity<String>("Welcome Back! "+fr.getName()+"Your Data is activated successfully.", HttpStatus.OK);
					}
				}
				else if(op2.isPresent()) {
					return new ResponseEntity<String>("This user name Has been used already", HttpStatus.BAD_REQUEST);
				}
				else {
				int id=(service.count())+10;
				boolean flag=true;
				while(flag) {
					Optional<FarmerDetails> op = service.findById(id);
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
				farmer.setJoinDate(date);
				farmer.setRole("ROLE_FARMER");
				farmer.setFid(id);
				farmer.setActive(true);
				farmer.setPrimeMember(false);
				System.out.println(farmer);
				service.save(farmer);
			System.out.println("Save...");
			System.out.println(farmer);
			return new ResponseEntity<String>("farmer add successfully", HttpStatus.CREATED);
				}
			}catch(Exception e) {
				//logger.trace(e.getMessage());
				return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@GetMapping("/getById/{id}")
		public ResponseEntity<Object> findById(@PathVariable("id") int id) {
			Optional<FarmerDetails> op = service.findById(id);
			if(op.isPresent()) {
			   FarmerDetails farmer=op.get();
			   if(farmer.getRole().equals("ROLE_FARMER")) { 
				   return new ResponseEntity<Object>(farmer, HttpStatus.OK);
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
			Optional<FarmerDetails> op = service.findByUserName(userName);
			if(op.isPresent()) {
			   FarmerDetails farmer=op.get();
				return new ResponseEntity<Object>(farmer, HttpStatus.OK);
			}else {
				return new ResponseEntity<Object>("This id not exist", HttpStatus.NOT_FOUND);
			}
		}
		

		
		@GetMapping("/allfarmer")
		public ResponseEntity<Object> getAll(){
			try {
			List<FarmerDetails> list=new ArrayList<>();
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
		
		@GetMapping("/allPrimeFarmer")
		public ResponseEntity<Object> allPrimeFarmer(){
			try {
			List<FarmerDetails> list=new ArrayList<>();
			service.findAllPrimeFarmers().forEach(list::add);
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
		
		
		@GetMapping("/findallactivefarmers")
		public ResponseEntity<Object> getAllActiveFarmer(){
			try {
			List<FarmerDetails> list=new ArrayList<>();
			service.findActiveFarmer(true).forEach(list::add);
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
		
		@DeleteMapping("deletefarmer/{id}")
		public ResponseEntity<String> deleteFarmerByID(@PathVariable("id") int id) {
			try {
			Optional<FarmerDetails> op = service.findById(id);
			if(op.isPresent()) {
				service.deleteById(id);
			    return new ResponseEntity<String>("Delete Successfully", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("There is no Farmer Data", HttpStatus.NO_CONTENT);
			}
			}catch(Exception e) {
				return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		
		@PutMapping("/update/{id}")
		public ResponseEntity<Object> updateFarmerById(@PathVariable("id") int id, @RequestBody FarmerDetails farmer)
		{
			try {
			Optional<FarmerDetails> op=service.findById(id);
			if(op.isPresent())
			{
//				String pass=farmer.getPassword();
//				farmer.setPassword(getPasswordEncoder.encode(pass));
				if(op.get().isActive() && op.get().getRole().equals("ROLE_FARMER")) {
					Optional<FarmerDetails> op1=service.findByMobile(farmer.getMobileNo());
					Optional<FarmerDetails> op2=service.findByUserName(farmer.getUserName());
					if(op1.isPresent() && !op.get().getMobileNo().equals(farmer.getMobileNo())) {
						return new ResponseEntity<Object>("This Mobile Number Has been used already", HttpStatus.BAD_REQUEST);
					}
					else if(op2.isPresent() && !op.get().getUserName().equals(farmer.getUserName())) {
						return new ResponseEntity<Object>("This user name Has been used already", HttpStatus.BAD_REQUEST);
					}
					else {
						FarmerDetails fr=op.get();
						farmer.setActive(true);
						farmer.setFid(fr.getFid());
						farmer.setRole("ROLE_FARMER");
						farmer.setPassword(fr.getPassword());
						farmer.setJoinDate(fr.getJoinDate());
						farmer.setPrimeMember(fr.isPrimeMember());
						service.save(farmer);
						return new ResponseEntity<Object>("The Data is update successfully of "+farmer.getName(), HttpStatus.OK);
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
		public ResponseEntity<Object> deactivateFarmerById(@PathVariable("id") int id)
		{
			try {
			Optional<FarmerDetails> op=service.findById(id);
			if(op.isPresent())
			{
				FarmerDetails fr=op.get();
				if(fr.getRole().equals("ROLE_FARMER") && fr.isActive()) {
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
				return new ResponseEntity<Object>(e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
//		@PutMapping("/activate/{id}")
//		public ResponseEntity<Object> activateFarmerById(@PathVariable("id") int id)
//		{
//			try {
//			Optional<FarmerDetails> op=service.findById(id);
//			if(op.isPresent())
//			{
//				FarmerDetails fr=op.get();
//				fr.setActive(true);
//				service.save(fr);
//				return new ResponseEntity<Object>("The Data is activate successfully of "+fr.getName(), HttpStatus.OK);
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
		
		@GetMapping("/primeUser/{id}")
		public ResponseEntity<Object> primeUser(@PathVariable("id") int id)
		{
			try {
			Optional<FarmerDetails> op=service.findById(id);
			if(op.isPresent())
			{
				FarmerDetails fr=op.get();
				if(fr.isActive()) {
					fr.setPrimeMember(true);
					service.save(fr);
					return new ResponseEntity<Object>("Congratulations! "+fr.getName()+", Now you are a prime customer.", HttpStatus.OK);
				}
				else {
					return new ResponseEntity<Object>("This Customer is not Active", HttpStatus.NOT_FOUND);
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
		
		@PutMapping("/changePassword/{id}")
		public ResponseEntity<Object> changePassword(@PathVariable("id") int id, @RequestBody PasswordChange pass)
		{
			try {
			Optional<FarmerDetails> op=service.findById(id);
			if(op.isPresent())
			{
				FarmerDetails fr=op.get();
				if(fr.getRole().equals("ROLE_FARMER") && fr.isActive()) {
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
		
		
		@GetMapping("/validFarmer/{id}")
		public boolean validFarmer(@PathVariable("id") int id) {
			try {
				Optional<FarmerDetails> op=service.findById(id);
				if(op.isPresent())
				{
					FarmerDetails fr=op.get();
					if(fr.getRole().equals("ROLE_FARMER") && fr.isActive()) {
						return true;
					   }
					 else {
					   return false;
					 }
				}
				else
				{
					return false;
				}
				}catch(Exception e) {
					return false;
				}
		}
	  
		
		@GetMapping("/payForPrime/{id}")
		public ResponseEntity<Object> payForPrime(@PathVariable("id") int id) {
			try {
				Optional<FarmerDetails> op=service.findById(id);
				if(op.isPresent())
				{
					FarmerDetails fr=op.get();
					if(fr.getRole().equals("ROLE_FARMER") && fr.isActive()) {
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
		
		@GetMapping("/findFarmerContact/{id}")
		public ResponseEntity<Object> findFarmerContact(@PathVariable("id") int id) {
			Optional<FarmerDetails> op = service.findById(id);
			if(op.isPresent()) {
			   FarmerDetails farmer=op.get();
			   if(farmer.getRole().equals("ROLE_FARMER") && farmer.isActive()) 
			   { 
				   FarmerContactDetails farmerContact= new FarmerContactDetails(farmer.getName(), farmer.getMobileNo(), farmer.getAddress());
				   return new ResponseEntity<Object>(farmerContact, HttpStatus.OK);
			   }
			   else {
				   return new ResponseEntity<Object>("This id not exist", HttpStatus.NOT_FOUND);
			   }
			}else {
				return new ResponseEntity<Object>("This id not exist", HttpStatus.NOT_FOUND);
			}
		}
	  
		
//		//JWT
//		
//		@Autowired
//		private AuthenticationManager authenticationManager;
//
//		@Autowired
//		private JwtUtil jwtTokenUtil;
//
//		@Autowired
//		private MyUserDetailsService userDetailsService;
//
//		@RequestMapping({ "/hello" })
//		public String firstPage() {
//			return "Hello World";
//		}
//
//		@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//		public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
//
//			try {
//				authenticationManager.authenticate(
//						new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
//				);
//			}
//			catch (BadCredentialsException e) {
//				throw new Exception("Incorrect username or password", e);
//			}
//
//
//			final UserDetails userDetails = userDetailsService
//					.loadUserByUsername(authenticationRequest.getUsername());
//
//			final String jwt = jwtTokenUtil.generateToken(userDetails);
//
//			return ResponseEntity.ok(new AuthenticationResponse(jwt));
//		}

}
