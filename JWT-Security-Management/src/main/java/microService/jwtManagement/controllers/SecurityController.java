package microService.jwtManagement.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import microService.jwtManagement.models.CropRequirements;
import microService.jwtManagement.models.DealerDetails;
import microService.jwtManagement.models.FarmerContactDetails;
import microService.jwtManagement.models.FarmerDetails;
import microService.jwtManagement.models.FarmerDetailsWithCrops;
import microService.jwtManagement.models.PasswordChange;
import microService.jwtManagement.models.PaymentAck;
import microService.jwtManagement.models.PaymentDetails;
import microService.jwtManagement.models.SendOfferDetails;
import microService.jwtManagement.models.UserData;
import microService.jwtManagement.security.MyUserDetailsService;
import microService.jwtManagement.services.CropsService;
import microService.jwtManagement.services.DealerService;
import microService.jwtManagement.services.FarmerService;
import microService.jwtManagement.services.PaymentService;
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
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@RequestMapping({ "/hello" })
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
		int id=0;
		String role="";
		String name="";
		Optional<UserData> op=userService.findByUserNameOfUser(authenticationRequest.getUsername());
		if(op.isPresent()) {
			id=op.get().getFid();
			role=op.get().getRole();
			name=op.get().getName();
		}
		System.out.println(id+" "+op.get());

		return ResponseEntity.ok(new AuthenticationResponse(jwt, id, role, name));
	}

	
	//Crops Management
	@PostMapping("/farmer/addCrop/{fid}")
	public ResponseEntity<String> addCrop(@RequestBody CropDetails crop, @PathVariable("fid") int fid ) {
		try {	
		String s=cropService.addCrops(crop, fid);
		return new ResponseEntity<String>(s, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	 @GetMapping("/anyRole/findCropById/{cid}")
	public ResponseEntity<CropDetails> findCropById(@PathVariable("cid") int cid ) throws Exception {
		
			CropDetails crop=cropService.findCropById(cid);
			return new ResponseEntity<CropDetails>(crop, HttpStatus.OK);
		
	 }
	 
	 @GetMapping("/anyRole/findAllCrops")
	public ResponseEntity<List<CropDetails>> findAllCrops(){
		
		 List<CropDetails> list=new ArrayList<>();
			try{
				list=cropService.findAllCrops();
			
				return new ResponseEntity<List<CropDetails>>(list, HttpStatus.OK);
			}
		
			catch(Exception e) {
				return new ResponseEntity<List<CropDetails>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
			}		
	}
	 
	 @PutMapping("/farmer/updateCrop/{cid}/{fid}")
	public ResponseEntity<String> deleteCrop(@PathVariable("cid") int cid, @PathVariable("fid") int fid, @RequestBody CropDetails crop ) {
		try {	
			String s=cropService.updateCropByIdAndFid(crop, cid, fid);
			return new ResponseEntity<String>(s, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	 }
	 
	 @DeleteMapping("/farmer/deleteCrop/{cid}/{fid}")
		public ResponseEntity<String> deleteCrop(@PathVariable("cid") int cid, @PathVariable("fid") int fid) {
			try {	
				String s=cropService.deleteCropByIdAndFid(cid, fid);
				return new ResponseEntity<String>(s, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		 }

	 @GetMapping("/anyRole/findAllCropsByFid/{fid}")
	public ResponseEntity<List<CropDetails>> findAllCropsByFid(@PathVariable("fid") int fid){
		 List<CropDetails> list=new ArrayList<>();
		 try {
			list=cropService.findCropsByFarmerId(fid);
				return new ResponseEntity<List<CropDetails>>(list, HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<List<CropDetails>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
			
	}
	 
	 @GetMapping("/anyRole/findCropsByCropName/{name}")
		public ResponseEntity<List<CropDetails>> findCropsByCropName(@PathVariable("name") String name){
		 List<CropDetails> list=new ArrayList<>();
		 try {
				list=cropService.findByCropName(name);
				
					return new ResponseEntity<List<CropDetails>>(list, HttpStatus.OK);
				
			}catch(Exception e) {
				return new ResponseEntity<List<CropDetails>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
				
		}

	 @GetMapping("/anyRole/findCropsByCropNameAndQuantity/{name}/{qty}")
		public ResponseEntity<List<CropDetails>> findCropsByCropNameAndQuantity(@PathVariable("name") String name, @PathVariable("qty") double qty){
		 List<CropDetails> list=new ArrayList<>();	
		 try {
			list=cropService.findByCropNameAndQuantity(name, qty);
				
					return new ResponseEntity<List<CropDetails>>(list, HttpStatus.OK);
				
			}catch(Exception e) {
				return new ResponseEntity<List<CropDetails>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
				
		}
	 
	 @GetMapping("/anyRole/findCropsByCropNameAndPrice/{name}/{price}")
		public ResponseEntity<List<CropDetails>> findCropsByCropNameAndPrice(@PathVariable("name") String name, @PathVariable("price") double price){
		 List<CropDetails> list=new ArrayList<>();	
		 try {
				list=cropService.findByCropNameAndPrice(name, price);
				
					return new ResponseEntity<List<CropDetails>>(list, HttpStatus.OK);
				
			}catch(Exception e) {
				return new ResponseEntity<List<CropDetails>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
				
		}
	 
	 @GetMapping("/anyRole/findCropsByCropNameAndPriceAndQuantity/{name}/{price}/{qty}")
		public ResponseEntity<List<CropDetails>> findCropsByCropNameAndPriceAndQuantity(@PathVariable("name") String name, @PathVariable("price") double price, @PathVariable("qty") double qty){
		 List<CropDetails> list=new ArrayList<>();
		 try {
			list=cropService.findByCropNameAndPriceAndQuantity(name, price, qty);
	
					return new ResponseEntity<List<CropDetails>>(list, HttpStatus.OK);
				
			}catch(Exception e) {
				return new ResponseEntity<List<CropDetails>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
				
		}
	 
	 //Farmer Management
	 
	@GetMapping("/admin/findAllFarmers")
	public ResponseEntity<List<FarmerDetails>> getAllFarmers(){
		List<FarmerDetails> list=new ArrayList<>();
		try {
		list=farmerService.findAllFarmers();
			return new ResponseEntity<List<FarmerDetails>>(list, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<FarmerDetails>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
	}
	
	
	@GetMapping("/farmer/getOffers")
	public ResponseEntity<List<SendOfferDetails>> getOffers(){
		List<SendOfferDetails> list=new ArrayList<>();
		try {
		list=farmerService.getOffers();
			return new ResponseEntity<List<SendOfferDetails>>(list, HttpStatus.OK);
		
		}catch(Exception e) {
			return new ResponseEntity<List<SendOfferDetails>>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
	}
	
	@GetMapping("/anyRole/findFarmerDetailsWithCrops/{fid}")
	public ResponseEntity<FarmerDetailsWithCrops> findFarmerDetailsWithCrops(@PathVariable("fid") int fid ) {
		FarmerDetailsWithCrops farmer=new FarmerDetailsWithCrops();
		try {	
		farmer=farmerService.getFarmerDetailsWithCrops(fid);
		return new ResponseEntity<FarmerDetailsWithCrops>(farmer, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<FarmerDetailsWithCrops>(farmer, HttpStatus.INTERNAL_SERVER_ERROR);
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
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @GetMapping("/admin/findAllPrimeFarmers")
		public ResponseEntity<Object> findAllPrimeFarmers(){
			List<FarmerDetails> list=new ArrayList<>();
			try {
			list=farmerService.findAllPrimeFarmers();
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<Object>(list, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			
		}
	 
	 @GetMapping("/admin/findAllActiveFarmers")
		public ResponseEntity<Object> findAllActiveFarmers(){
			List<FarmerDetails> list=new ArrayList<>();
			try {
			list=farmerService.findAllActiveFarmers();
				return new ResponseEntity<Object>(list, HttpStatus.OK);
		
			}catch(Exception e) {
				return new ResponseEntity<Object>(list, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			
		}
	 
	 @PutMapping("/farmer/updateFarmer/{fid}")
		public ResponseEntity<String> updateFarmer(@RequestBody FarmerDetails farmer, @PathVariable("fid") int fid ) {
			try {	
				farmer.setPassword(getPasswordEncoder.encode(farmer.getPassword()));
			String s=farmerService.updateFarmers(farmer, fid);
			return new ResponseEntity<String>(s, HttpStatus.CREATED);
			}catch(Exception e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @GetMapping("/findByIdOfFarmer/{fid}")
		public ResponseEntity<Object> findByIdOfFarmer(@PathVariable("fid") int fid ) {
		 FarmerDetails farmer=new FarmerDetails();
		 try {	
			farmer=farmerService.findByIdOfFarmers(fid);
			return new ResponseEntity<Object>(farmer, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<Object>(farmer, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @GetMapping("/farmer/deactivateFarmer/{fid}")
		public ResponseEntity<String> deactivateFarmer(@PathVariable("fid") int fid ) {
			try {	
			String s=farmerService.deactivateFarmerById(fid);
			return new ResponseEntity<String>(s, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @PutMapping("/farmer/changePasswordOfFarmer/{fid}")
		public ResponseEntity<Object> changePasswordOfFarmer(@RequestBody PasswordChange pass, @PathVariable("fid") int fid ) {
			try {
				FarmerDetails fr=farmerService.findByIdOfFarmers(fid);
					if(fr.getRole().equals("ROLE_FARMER") && fr.isActive()) {
						if(getPasswordEncoder.matches(pass.getOldPassword(), fr.getPassword()))
						{
							pass.setNewPassword(getPasswordEncoder.encode(pass.getNewPassword()));
						   String s=farmerService.changePasswordOfFarmer(fid, pass);
							return new ResponseEntity<Object>(s, HttpStatus.OK);						
						}
						else {
							return new ResponseEntity<Object>("Give Correct Old Password", HttpStatus.BAD_REQUEST);	
						}
					   }
					 else {
					   return new ResponseEntity<Object>(fid+ " not exist", HttpStatus.NOT_FOUND);
					 }
			}catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @GetMapping("/farmer/payForPrimeAc/{fid}")
		public ResponseEntity<Object> payForPrimeAcOfFarmer(@PathVariable("fid") int fid ) {
			try {	
			//String s=farmerService.payForPrime(fid);
			return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8052/farmer/payForPrime/"+fid)).build();
			}catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @GetMapping("/anyRole/findFarmerContactDetails/{fid}")
		public ResponseEntity<Object> findFarmerContactDetails(@PathVariable("fid") int fid ) {
		 FarmerContactDetails farmer=new FarmerContactDetails ();	
		 try {	
			farmer=farmerService.findFarmerContact(fid);
			return new ResponseEntity<Object>(farmer, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<Object>(farmer, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	 
	 //Dealer Management
	 
	 @GetMapping("/admin/findAllDealers")
		public ResponseEntity<Object> getAllDealers(){
		 List<DealerDetails> list=new ArrayList<>();	
		 try {
			list=dealerService.findAllDealers();
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<Object>(list, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			
		}
	 
	 @PostMapping("/dealer/sendOfferToFarmers/{did}")
		public ResponseEntity<Object> sendOffertoFarmers(@RequestBody CropRequirements req, @PathVariable("did") int did ) {
			try {	
			String s=dealerService.sendOffer(req, did);
			return new ResponseEntity<Object>(s, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
					return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		 
		 @PutMapping("/dealer/updateDealer/{did}")
			public ResponseEntity<String> updateDealer(@RequestBody DealerDetails dealer, @PathVariable("did") int did ) {
				try {	
					dealer.setPassword(getPasswordEncoder.encode(dealer.getPassword()));
				String s=dealerService.updateDealers(dealer, did);
				return new ResponseEntity<String>(s, HttpStatus.CREATED);
				}catch(Exception e) {
					return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		 
		 @GetMapping("/findByIdOfDealer/{did}")
			public ResponseEntity<Object> findByIdOfDealer(@PathVariable("did") int did ) {
			 DealerDetails dealer=new DealerDetails();
			 try {	
				dealer=dealerService.findByIdOfDealers(did);
				return new ResponseEntity<Object>(dealer, HttpStatus.OK);
				}catch(Exception e) {
					return new ResponseEntity<Object>(dealer, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		 
		 @GetMapping("/admin/findAllPrimeDealers")
			public ResponseEntity<Object> findAllPrimeDealers(){
			 List<DealerDetails> list=new ArrayList<>();	
			 try {
				list=dealerService.findAllPrimeDealers();
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				}catch(Exception e) {
					return new ResponseEntity<Object>(list, HttpStatus.INTERNAL_SERVER_ERROR);
				}	
				
			}
		 
		 @GetMapping("/admin/findAllActiveDealers")
			public ResponseEntity<Object> findAllActiveDealers(){
			 List<DealerDetails> list=new ArrayList<>();	
			 try {
				list=dealerService.findAllActiveDealers();
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				}catch(Exception e) {
					return new ResponseEntity<Object>(list, HttpStatus.INTERNAL_SERVER_ERROR);
				}	
				
			}
		 
		 
		 @GetMapping("/dealer/deactivateDealer/{did}")
			public ResponseEntity<String> deactivateDealer(@PathVariable("did") int did ) {
				try {	
				String s=dealerService.deactivateByIdOfDealers(did);
				return new ResponseEntity<String>(s, HttpStatus.OK);
				}catch(Exception e) {
					return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		 
		 @PutMapping("/dealer/changePasswordOfDealer/{did}")
			public ResponseEntity<Object> changePasswordOfDealer(@RequestBody PasswordChange pass, @PathVariable("did") int did ) {
				try {	
					DealerDetails dr=dealerService.findByIdOfDealers(did);
					if(dr.getRole().equals("ROLE_DEALER") && dr.isActive()) {
						if(getPasswordEncoder.matches(pass.getOldPassword(), dr.getPassword()))
						{
							pass.setNewPassword(getPasswordEncoder.encode(pass.getNewPassword()));
						   String s=dealerService.changePasswordOfDealer(did, pass);
							return new ResponseEntity<Object>(s, HttpStatus.OK);						
						}
						else {
							return new ResponseEntity<Object>("Give Correct Old Password", HttpStatus.BAD_REQUEST);	
						}
					   }
					 else {
					   return new ResponseEntity<Object>(did+ " not exist", HttpStatus.NOT_FOUND);
					 }
				}catch(Exception e) {
					return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}

		 @GetMapping("/dealer/payForPrimeAc/{fid}")
			public ResponseEntity<Object> payForPrimeAcOfDealer(@PathVariable("did") int did ) {
				try {	
				String s=dealerService.payForPrime(did);
				return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8053/dealer/payForPrime/"+did)).build();
				}catch(Exception e) {
					return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			
		 
		 //Payment Management
		 
		 @GetMapping("/admin/findAllPayments")
			public ResponseEntity<Object> getAllPayments(){
			 List<PaymentDetails> list=new ArrayList<>();	
			 try {
				list=paymentService.findAllPayments();
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				}catch(Exception e) {
					return new ResponseEntity<Object>(list, HttpStatus.INTERNAL_SERVER_ERROR);
				}	
				
			}
		 
		 @GetMapping("/anyRole/findPaymentsByCustomerId/{cid}")
			public ResponseEntity<Object> getAllPaymentsByCustomerId(@PathVariable("cid") int cid ){
			 PaymentDetails payment=new PaymentDetails();	
			 try {
				payment=paymentService.findPaymentByCustomerId(cid);
					return new ResponseEntity<Object>(payment, HttpStatus.OK);
				}catch(Exception e) {
					return new ResponseEntity<Object>(payment, HttpStatus.INTERNAL_SERVER_ERROR);
				}	
				
			}
		 
		 @GetMapping("/anyRole/findLastPaymentsByCustomerId/{cid}")
			public ResponseEntity<Object> getLastPaymentsByCustomerId(@PathVariable("cid") int cid ){
				try {
				PaymentDetails payment=paymentService.findPaymentByCustomerId(cid);
				List<TreeMap<String, String>> txn=payment.getTransactionDeatils();
				PaymentAck ack=new PaymentAck(payment.getOrderId(), payment.getCustomerId(), payment.getResult(), txn.get(txn.size()-1));
					return new ResponseEntity<Object>(ack, HttpStatus.OK);
				}catch(Exception e) {
					return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}	
				
			}
		 
		 
		 
		 


}
