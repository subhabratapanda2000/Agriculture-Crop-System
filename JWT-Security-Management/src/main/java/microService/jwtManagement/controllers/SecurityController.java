package microService.jwtManagement.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microService.jwtManagement.models.CropDetails;
import microService.jwtManagement.models.CropRequirements;
import microService.jwtManagement.models.DealerDetails;
import microService.jwtManagement.models.FarmerContactDetails;
import microService.jwtManagement.models.FarmerDetails;
import microService.jwtManagement.models.FarmerDetailsWithCrops;
import microService.jwtManagement.models.PasswordChange;
import microService.jwtManagement.models.PaymentDetails;
import microService.jwtManagement.models.SendOfferDetails;
import microService.jwtManagement.services.CropsService;
import microService.jwtManagement.services.DealerService;
import microService.jwtManagement.services.FarmerService;
import microService.jwtManagement.services.PaymentService;
import microService.jwtManagement.services.UsersService;

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
	
	
	//Crops Management
	@PostMapping("/farmer/addCrop/{fid}")
	public ResponseEntity<String> addCrop(@RequestBody CropDetails crop, @PathVariable("fid") int fid ) {
		try {	
		String s=cropService.addCrops(crop, fid);
		return new ResponseEntity<String>(s, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<String>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	 @GetMapping("/anyRole/findCropById/{cid}")
	public ResponseEntity<Object> findCropById(@PathVariable("cid") int cid ) {
		try {	
			CropDetails crop=cropService.findCropById(cid);
			return new ResponseEntity<Object>(crop, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	 }
	 
	 @GetMapping("/anyRole/findAllCrops")
	public ResponseEntity<Object> findAllCrops(){
		try {
			List<CropDetails> list=cropService.findAllCrops();
			if(!list.isEmpty()) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Object>("There is no Crops", HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
			
	}
	 
	 @PutMapping("/farmer/updateCrop/{cid}/{fid}")
	public ResponseEntity<Object> deleteCrop(@PathVariable("cid") int cid, @PathVariable("fid") int fid, @RequestBody CropDetails crop ) {
		try {	
			String s=cropService.updateCropByIdAndFid(crop, cid, fid);
			return new ResponseEntity<Object>(s, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	 }
	 
	 @DeleteMapping("/farmer/deleteCrop/{cid}/{fid}")
		public ResponseEntity<Object> deleteCrop(@PathVariable("cid") int cid, @PathVariable("fid") int fid) {
			try {	
				String s=cropService.deleteCropByIdAndFid(cid, fid);
				return new ResponseEntity<Object>(s, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		 }

	 @GetMapping("/anyRole/findAllCropsByFid/{fid}")
	public ResponseEntity<Object> findAllCropsByFid(@PathVariable("fid") int fid){
		try {
			List<CropDetails> list=cropService.findCropsByFarmerId(fid);
			if(!list.isEmpty()) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Object>("There is no Crops", HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			return new ResponseEntity<Object>("There have some problem "+e, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
			
	}
	 
	 @GetMapping("/anyRole/findCropsByCropName/{name}")
		public ResponseEntity<Object> findCropsByCropName(@PathVariable("name") String name){
			try {
				List<CropDetails> list=cropService.findByCropName(name);
				if(!list.isEmpty()) {
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				}
				else {
					return new ResponseEntity<Object>("There is no Crops", HttpStatus.NO_CONTENT);
				}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
				
		}

	 @GetMapping("/anyRole/findCropsByCropNameAndQuantity/{name}/{qty}")
		public ResponseEntity<Object> findCropsByCropNameAndQuantity(@PathVariable("name") String name, @PathVariable("qty") double qty){
			try {
				List<CropDetails> list=cropService.findByCropNameAndQuantity(name, qty);
						
				if(!list.isEmpty()) {
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				}
				else {
					return new ResponseEntity<Object>("There is no Crops", HttpStatus.NO_CONTENT);
				}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
				
		}
	 
	 @GetMapping("/anyRole/findCropsByCropNameAndPrice/{name}/{price}")
		public ResponseEntity<Object> findCropsByCropNameAndPrice(@PathVariable("name") String name, @PathVariable("price") double price){
			try {
				List<CropDetails> list=cropService.findByCropNameAndPrice(name, price);
						
				if(!list.isEmpty()) {
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				}
				else {
					return new ResponseEntity<Object>("There is no Crops", HttpStatus.NO_CONTENT);
				}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
				
		}
	 
	 @GetMapping("/anyRole/findCropsByCropNameAndPriceAndQuantity/{name}/{price}/{qty}")
		public ResponseEntity<Object> findCropsByCropNameAndPriceAndQuantity(@PathVariable("name") String name, @PathVariable("price") double price, @PathVariable("qty") double qty){
			try {
				List<CropDetails> list=cropService.findByCropNameAndPriceAndQuantity(name, price, qty);
						
				if(!list.isEmpty()) {
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				}
				else {
					return new ResponseEntity<Object>("There is no Crops", HttpStatus.NO_CONTENT);
				}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
				
		}
	 
	 //Farmer Management
	 
	@GetMapping("/admin/findAllFarmers")
	public ResponseEntity<Object> getAllFarmers(){
		try {
		List<FarmerDetails> list=farmerService.findAllFarmers();
		if(!list.isEmpty()) {
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Object>("There is no Farmers", HttpStatus.NO_CONTENT);
		}
		}catch(Exception e) {
			return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
	}
	
	
	@GetMapping("/farmer/getOffers")
	public ResponseEntity<Object> getOffers(){
		try {
		List<SendOfferDetails> list=farmerService.getOffers();
		if(!list.isEmpty()) {
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Object>("There is no Offers", HttpStatus.NO_CONTENT);
		}
		}catch(Exception e) {
			return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
	}
	
	@GetMapping("/anyRole/findFarmerDetailsWithCrops/{fid}")
	public ResponseEntity<Object> findFarmerDetailsWithCrops(@PathVariable("fid") int fid ) {
		try {	
			FarmerDetailsWithCrops farmer=farmerService.getFarmerDetailsWithCrops(fid);
		return new ResponseEntity<Object>(farmer, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
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
				return new ResponseEntity<String>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @GetMapping("/admin/findAllPrimeFarmers")
		public ResponseEntity<Object> findAllPrimeFarmers(){
			try {
			List<FarmerDetails> list=farmerService.findAllPrimeFarmers();
			if(!list.isEmpty()) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Object>("There is no Prime Farmers", HttpStatus.NO_CONTENT);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			
		}
	 
	 @GetMapping("/admin/findAllActiveFarmers")
		public ResponseEntity<Object> findAllActiveFarmers(){
			try {
			List<FarmerDetails> list=farmerService.findAllActiveFarmers();
			if(!list.isEmpty()) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Object>("There is no Active Farmers", HttpStatus.NO_CONTENT);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			
		}
	 
	 @PutMapping("/farmer/updateFarmer/{fid}")
		public ResponseEntity<String> updateFarmer(@RequestBody FarmerDetails farmer, @PathVariable("fid") int fid ) {
			try {	
				farmer.setPassword(getPasswordEncoder.encode(farmer.getPassword()));
			String s=farmerService.updateFarmers(farmer, fid);
			return new ResponseEntity<String>(s, HttpStatus.CREATED);
			}catch(Exception e) {
				return new ResponseEntity<String>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @GetMapping("/findByIdOfFarmer/{fid}")
		public ResponseEntity<Object> findByIdOfFarmer(@PathVariable("fid") int fid ) {
			try {	
			FarmerDetails farmer=farmerService.findByIdOfFarmers(fid);
			return new ResponseEntity<Object>(farmer, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @PutMapping("/farmer/deactivateFarmer/{fid}")
		public ResponseEntity<String> deactivateFarmer(@PathVariable("fid") int fid ) {
			try {	
			String s=farmerService.deactivateFarmerById(fid);
			return new ResponseEntity<String>(s, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<String>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @PutMapping("/farmer/changePasswordOfFarmer/{fid}")
		public ResponseEntity<Object> changePasswordOfFarmer(@RequestBody PasswordChange pass, @PathVariable("fid") int fid ) {
			try {
				FarmerDetails fr=farmerService.findByIdOfFarmers(fid);
					if(fr.getRole().equals("ROLE_FARMER") && fr.isActive()) {
						if(pass.getMobileNo().equals(fr.getMobileNo()) && getPasswordEncoder.matches(pass.getOldPassword(), fr.getPassword()))
						{
							pass.setNewPassword(getPasswordEncoder.encode(pass.getNewPassword()));
						   String s=farmerService.changePasswordOfFarmer(fid, pass);
							return new ResponseEntity<Object>(s, HttpStatus.OK);						
						}
						else {
							return new ResponseEntity<Object>("Give Correct Mobile Number and Old Password", HttpStatus.BAD_REQUEST);	
						}
					   }
					 else {
					   return new ResponseEntity<Object>(fid+ " not exist", HttpStatus.NOT_FOUND);
					 }
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @GetMapping("/farmer/payForPrimeAc/{fid}")
		public ResponseEntity<Object> payForPrimeAcOfFarmer(@PathVariable("fid") int fid ) {
			try {	
			//String s=farmerService.payForPrime(fid);
			return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8052/farmer/payForPrime/"+fid)).build();
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
	 @GetMapping("/anyRole/findFarmerContactDetails/{fid}")
		public ResponseEntity<Object> findFarmerContactDetails(@PathVariable("fid") int fid ) {
			try {	
				FarmerContactDetails farmer=farmerService.findFarmerContact(fid);
			return new ResponseEntity<Object>(farmer, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	 
	 //Dealer Management
	 
	 @GetMapping("/admin/findAllDealers")
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
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			
		}
	 
	 @PostMapping("/dealer/sendOfferToFarmers/{did}")
		public ResponseEntity<Object> sendOffertoFarmers(@RequestBody CropRequirements req, @PathVariable("did") int did ) {
			try {	
			String s=dealerService.sendOffer(req, did);
			return new ResponseEntity<Object>(s, HttpStatus.OK);
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
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
					return new ResponseEntity<String>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		 
		 @PutMapping("/dealer/updateDealer/{did}")
			public ResponseEntity<String> updateDealer(@RequestBody DealerDetails dealer, @PathVariable("did") int did ) {
				try {	
					dealer.setPassword(getPasswordEncoder.encode(dealer.getPassword()));
				String s=dealerService.updateDealers(dealer, did);
				return new ResponseEntity<String>(s, HttpStatus.CREATED);
				}catch(Exception e) {
					return new ResponseEntity<String>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		 
		 @GetMapping("/findByIdOfDealer/{did}")
			public ResponseEntity<Object> findByIdOfDealer(@PathVariable("did") int did ) {
				try {	
				DealerDetails dealer=dealerService.findByIdOfDealers(did);
				return new ResponseEntity<Object>(dealer, HttpStatus.OK);
				}catch(Exception e) {
					return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		 
		 @GetMapping("/admin/findAllPrimeDealers")
			public ResponseEntity<Object> findAllPrimeDealers(){
				try {
				List<DealerDetails> list=dealerService.findAllPrimeDealers();
				if(!list.isEmpty()) {
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				}
				else {
					return new ResponseEntity<Object>("There is no Prime Dealers", HttpStatus.NO_CONTENT);
				}
				}catch(Exception e) {
					return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
				}	
				
			}
		 
		 @GetMapping("/admin/findAllActiveDealers")
			public ResponseEntity<Object> findAllActiveDealers(){
				try {
				List<DealerDetails> list=dealerService.findAllActiveDealers();
				if(!list.isEmpty()) {
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				}
				else {
					return new ResponseEntity<Object>("There is no Active Dealers", HttpStatus.NO_CONTENT);
				}
				}catch(Exception e) {
					return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
				}	
				
			}
		 
		 
		 @PutMapping("/dealer/deactivateDealer/{did}")
			public ResponseEntity<String> deactivateDealer(@PathVariable("did") int did ) {
				try {	
				String s=dealerService.deactivateByIdOfDealers(did);
				return new ResponseEntity<String>(s, HttpStatus.OK);
				}catch(Exception e) {
					return new ResponseEntity<String>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		 
		 @PutMapping("/dealer/changePasswordOfDealer/{did}")
			public ResponseEntity<Object> changePasswordOfDealer(@RequestBody PasswordChange pass, @PathVariable("did") int did ) {
				try {	
					DealerDetails dr=dealerService.findByIdOfDealers(did);
					if(dr.getRole().equals("ROLE_DEALER") && dr.isActive()) {
						if(pass.getMobileNo().equals(dr.getMobileNo()) && getPasswordEncoder.matches(pass.getOldPassword(), dr.getPassword()))
						{
							pass.setNewPassword(getPasswordEncoder.encode(pass.getNewPassword()));
						   String s=dealerService.changePasswordOfDealer(did, pass);
							return new ResponseEntity<Object>(s, HttpStatus.OK);						
						}
						else {
							return new ResponseEntity<Object>("Give Correct Mobile Number and Old Password", HttpStatus.BAD_REQUEST);	
						}
					   }
					 else {
					   return new ResponseEntity<Object>(did+ " not exist", HttpStatus.NOT_FOUND);
					 }
				}catch(Exception e) {
					return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}

		 @GetMapping("/dealer/payForPrimeAc/{fid}")
			public ResponseEntity<Object> payForPrimeAcOfDealer(@PathVariable("did") int did ) {
				try {	
				String s=dealerService.payForPrime(did);
				return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8053/dealer/payForPrime/"+did)).build();
				}catch(Exception e) {
					return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			
		 
		 //Payment Management
		 
		 @GetMapping("/admin/findAllPayments")
			public ResponseEntity<Object> getAllPayments(){
				try {
				List<PaymentDetails> list=paymentService.findAllPayments();
				if(!list.isEmpty()) {
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				}
				else {
					return new ResponseEntity<Object>("There is no Payments", HttpStatus.NO_CONTENT);
				}
				}catch(Exception e) {
					return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
				}	
				
			}
		 
		 @GetMapping("/admin/findPaymentsByCustomerId/{cid}")
			public ResponseEntity<Object> getAllPaymentsByCustomerId(@PathVariable("cid") int cid ){
				try {
				PaymentDetails payment=paymentService.findPaymentByCustomerId(cid);
					return new ResponseEntity<Object>(payment, HttpStatus.OK);
				}catch(Exception e) {
					return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
				}	
				
			}
		 
		 


}
