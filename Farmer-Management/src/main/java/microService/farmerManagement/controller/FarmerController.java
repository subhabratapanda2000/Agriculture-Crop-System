package microService.farmerManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/farmer")
public class FarmerController {
	
	
	
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
	
	 
	    @PostMapping("/create")
		public ResponseEntity<String> insertFarmer(@RequestBody FarmerDetails farmer) {
			try {
				service.save(farmer);
			System.out.println("Save...");
			System.out.println(farmer);
			return new ResponseEntity<String>("farmer add successfully", HttpStatus.CREATED);
			}catch(Exception e) {
				return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@GetMapping("/getById/{id}")
		public ResponseEntity<Object> getCropDetails(@PathVariable("id") int id) {
			Optional<FarmerDetails> op = service.findById(id);
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
				FarmerDetails fr=op.get();
				farmer.setFid(fr.getFid());
				service.save(farmer);
				return new ResponseEntity<Object>("The Data is update successfully of "+farmer.getName(), HttpStatus.OK);
				
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
