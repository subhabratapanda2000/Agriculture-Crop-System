package microService.farmerManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import microService.farmerManagement.models.CropDetails;
import microService.farmerManagement.models.FarmerDetails;
import microService.farmerManagement.models.FarmerDetailsWithCrops;
import microService.farmerManagement.service.CropService;
import microService.farmerManagement.service.FarmerService;

@RestController
@CrossOrigin
@RequestMapping("/farmer")
public class FarmerController {
	Logger logger=LoggerFactory.getLogger(FarmerController.class);
	
	
	@Autowired
	private FarmerService service;
	
	@Autowired
	private CropService cropService;
	
	

	    
	    @GetMapping("/farmerdetails/{userId}")
	    public ResponseEntity<Object> getFarmerDeatils(@PathVariable("userId") int userId){
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
	
	    @PostMapping("/addcrops/{fid}")
		public ResponseEntity<String> addCrops(@RequestBody CropDetails crop, @PathVariable("fid") int fid ) {
			try {
				
			String s=cropService.addCrops(crop, fid);	
			return new ResponseEntity<String>(s, HttpStatus.CREATED);
			}catch(Exception e) {
				logger.trace(e.getMessage());
				return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	    @PostMapping("/create")
		public ResponseEntity<String> insertFarmer(@RequestBody FarmerDetails farmer) {
			try {
				System.out.println("hoii");
				//Auto Id generate
//				String pass=farmer.getPassword();
//				farmer.setPassword(getPasswordEncoder.encode(farmer.getPassword()));
//				System.out.println(getPasswordEncoder.encode(pass)+ " "+pass);
				Optional<FarmerDetails> op1=service.findByMobile(farmer.getMobileNo());
				Optional<FarmerDetails> op2=service.findByUserName(farmer.getUserName());
				if(op1.isPresent()) {
					return new ResponseEntity<String>("This Mobile Number Has been used already", HttpStatus.BAD_REQUEST);
				}
				if(op2.isPresent()) {
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
				farmer.setRole("ROLE_FARMER");
				farmer.setFid(id);
				farmer.setActive(true);
				System.out.println(farmer);
				service.save(farmer);
			System.out.println("Save...");
			System.out.println(farmer);
			return new ResponseEntity<String>("farmer add successfully", HttpStatus.CREATED);
				}
			}catch(Exception e) {
				logger.trace(e.getMessage());
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
				FarmerDetails fr=op.get();
				if(fr.getRole().equals("ROLE_FARMER")) {
					farmer.setFid(fr.getFid());
					farmer.setRole("ROLE_FARMER");
					farmer.setPassword(fr.getPassword());
					service.save(farmer);
					return new ResponseEntity<Object>("The Data is update successfully of "+farmer.getName(), HttpStatus.OK);
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
				if(fr.getRole().equals("ROLE_FARMER")) {
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
		
		@PutMapping("/activate/{id}")
		public ResponseEntity<Object> activateFarmerById(@PathVariable("id") int id)
		{
			try {
			Optional<FarmerDetails> op=service.findById(id);
			if(op.isPresent())
			{
				FarmerDetails fr=op.get();
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
