package microService.jwtManagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import microService.jwtManagement.models.AuthenticationRequest;
import microService.jwtManagement.models.AuthenticationResponse;
import microService.jwtManagement.models.CropDetails;
import microService.jwtManagement.models.DealerDetails;
import microService.jwtManagement.models.FarmerDetails;
import microService.jwtManagement.models.UserData;
import microService.jwtManagement.security.MyUserDetailsService;
import microService.jwtManagement.services.CropsService;
import microService.jwtManagement.services.DealerService;
import microService.jwtManagement.services.FarmerService;
import microService.jwtManagement.services.UsersService;
import microService.jwtManagement.util.JwtUtil;

@RestController
@CrossOrigin
@RequestMapping("/jwtsecurity")
public class SecurityController {
	
	@Autowired
	private PasswordEncoder getPasswordEncoder;
	
	@Autowired
	private CropsService cropService;
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private FarmerService farmerService;
	
	
	@GetMapping("/crops/findById/{userId}")
    public ResponseEntity<Object> getCropDeatils(@PathVariable("userId") int userId){
    	List<CropDetails> ls= cropService.getCrops(userId);
			return new ResponseEntity<Object>(ls, HttpStatus.OK);
	}
	
	@GetMapping("/getAllFarmers")
	public ResponseEntity<Object> getAll(){
		try {
		List<UserData> list=userService.findAllFarmers();
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
	
	 @PostMapping("/createFarmer")
		public ResponseEntity<String> createFarmer(@RequestBody FarmerDetails farmer) {
			try {	
				String pass=getPasswordEncoder.encode(farmer.getPassword());
				farmer.setPassword(pass);
				System.out.println(farmer);
			String s=farmerService.createFarmers(farmer);
			return new ResponseEntity<String>(s, HttpStatus.CREATED);
			}catch(Exception e) {
				return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @PutMapping("/farmer/updateFarmer/{fid}")
		public ResponseEntity<String> updateFarmer(@RequestBody FarmerDetails farmer, @PathVariable("fid") int fid ) {
			try {	
				farmer.setPassword(getPasswordEncoder.encode(farmer.getPassword()));
			String s=farmerService.updateFarmers(farmer, fid);
			return new ResponseEntity<String>(s, HttpStatus.CREATED);
			}catch(Exception e) {
				return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @GetMapping("/farmer/findByIdOfFarmer/{fid}")
		public ResponseEntity<Object> findByIdOfFarmer(@PathVariable("fid") int fid ) {
			try {	
			FarmerDetails farmer=farmerService.findByIdOfFarmers(fid);
			return new ResponseEntity<Object>(farmer, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @PutMapping("/farmer/deactivateFarmer/{fid}")
		public ResponseEntity<String> deactivateFarmer(@PathVariable("fid") int fid ) {
			try {	
			String s=farmerService.deactivateByIdOfFarmers(fid);
			return new ResponseEntity<String>(s, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	 
	 //Admin
	 
	 @GetMapping("/getAllDealers")
		public ResponseEntity<Object> getAllDealers(){
			try {
			List<DealerDetails> list=dealerService.findAllDealers();
			if(!list.isEmpty()) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Object>("There is no Dealers", HttpStatus.NO_CONTENT);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			
		}
		
		 @PostMapping("/createDealer")
			public ResponseEntity<String> createDealer(@RequestBody DealerDetails dealer) {
				try {	
					String pass=getPasswordEncoder.encode(dealer.getPassword());
					dealer.setPassword(pass);
					System.out.println(dealer);
				String s=dealerService.createDealers(dealer);
				return new ResponseEntity<String>(s, HttpStatus.CREATED);
				}catch(Exception e) {
					return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		 
		 @PutMapping("/dealer/updateDealer/{did}")
			public ResponseEntity<String> updateDealer(@RequestBody DealerDetails dealer, @PathVariable("did") int did ) {
				try {	
					dealer.setPassword(getPasswordEncoder.encode(dealer.getPassword()));
				String s=dealerService.updateDealers(dealer, did);
				return new ResponseEntity<String>(s, HttpStatus.CREATED);
				}catch(Exception e) {
					return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		 
		 @GetMapping("/dealer/findByIdOfDealer/{did}")
			public ResponseEntity<Object> findByIdOfDealer(@PathVariable("did") int did ) {
				try {	
				DealerDetails dealer=dealerService.findByIdOfDealers(did);
				return new ResponseEntity<Object>(dealer, HttpStatus.OK);
				}catch(Exception e) {
					return new ResponseEntity<Object>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		 
		 @PutMapping("/dealer/deactivateDealer/{did}")
			public ResponseEntity<String> deactivateDealer(@PathVariable("did") int did ) {
				try {	
				String s=dealerService.deactivateByIdOfDealers(did);
				return new ResponseEntity<String>(s, HttpStatus.OK);
				}catch(Exception e) {
					return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}

	
	//JWT
	
			@Autowired
			private AuthenticationManager authenticationManager;

			@Autowired
			private JwtUtil jwtTokenUtil;

			@Autowired
			private MyUserDetailsService userDetailsService;

			@GetMapping({ "/hello" })
			public String firstPage() {
				return "Hello World";
			}

			@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
			public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

				
				try {
					authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
					);
					
				}
				catch (BadCredentialsException e) {
					throw new Exception("Incorrect username or password", e);
				}


				final UserDetails userDetails = userDetailsService
						.loadUserByUsername(authenticationRequest.getUsername());

				final String jwt = jwtTokenUtil.generateToken(userDetails);
			

				return ResponseEntity.ok(new AuthenticationResponse(jwt));
			}



}
