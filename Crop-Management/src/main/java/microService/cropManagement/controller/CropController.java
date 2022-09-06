package microService.cropManagement.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
import org.springframework.web.client.RestTemplate;

import microService.cropManagement.models.CropDetails;
import microService.cropManagement.repo.CropRepository;
import microService.cropManagement.service.CropService;

@RestController
@RequestMapping("/crop")
public class CropController {
	@Autowired
	CropService repo;
	
	@Autowired
	CropRepository repo1;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	//@RequestMapping(value="/create/{fid}", method=RequestMethod.POST)
	@PostMapping("/create/{fid}")
	public ResponseEntity<String> insertCrops(@RequestBody CropDetails crop, @PathVariable("fid") int fid) {
		if(restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/validFarmer/"+fid, Boolean.class))
		{
		Optional<CropDetails> op1=repo.findByNameAndId(crop.getCropName(), fid);
		if(op1.isPresent()) {
			return new ResponseEntity<String>("You have already added "+crop.getCropName(), HttpStatus.BAD_REQUEST);
		}
		else {
		try {
			//Auto Id generate
			int id=((int)repo.count())+10;
			boolean flag=true;
			while(flag) {
				Optional<CropDetails> op = repo1.findById(id);
				if(op.isPresent()) {
					id++;
				}
				else {
					flag=false;
				}	
			}
			crop.setId(id);
			crop.setFarmerId(fid);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
			   LocalDateTime now = LocalDateTime.now(); 
			   String dt=dtf.format(now);  
			   String date=dt.substring(0, 10);
			crop.setDate(date);
			repo.save(crop);
		System.out.println("Save...");
		System.out.println(crop);
		return new ResponseEntity<String>("Crop add successfully", HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
		}
		else {
			return new ResponseEntity<String>("Invalid UserId", HttpStatus.BAD_REQUEST);
		}
	}
//	
	@GetMapping("/count")
	public long count() {
		return repo.count();
	}
	
	@GetMapping("/getcrop/{id}")
	public ResponseEntity<Object> getCropDetails(@PathVariable("id") int id) {
		CropDetails crop = repo.findById(id);
		
		
		if(crop!=null) {
			return new ResponseEntity<Object>(crop, HttpStatus.OK);
		}else {
		 
			return new ResponseEntity<Object>("Failure", HttpStatus.NOT_FOUND);
		}
	}
	

	
	@GetMapping("/allCrop")
	public ResponseEntity<Object> getAllCrops(){
		try {
		List<CropDetails> list=new ArrayList<>();
		repo.findAll().forEach(list::add);
		if(!list.isEmpty()) {
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Object>("There is no Crops", HttpStatus.NO_CONTENT);
		}
		}catch(Exception e) {
			return new ResponseEntity<Object>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@DeleteMapping("deletecrop/{id}/{fid}")
	public ResponseEntity<String> deleteCropByID(@PathVariable("id") int id, @PathVariable("fid") int fid) {
		if(restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/validFarmer/"+fid, Boolean.class))
		{
		try {
		Optional<CropDetails> op = repo.findByIdAndFid(id, fid);
		if(op.isPresent()) {
			repo.deleteById(id);
		    return new ResponseEntity<String>("Delete Successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("There is no Crop Data", HttpStatus.NO_CONTENT);
		}
		}catch(Exception e) {
			return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
		}}
		else {
			return new ResponseEntity<String>("Invalid UserId", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/updateCrop/{id}/{fid}")
	public ResponseEntity<Object> updateCropById(@PathVariable("id") int id, @PathVariable("fid") int fid, @RequestBody CropDetails crop)
	{
		if(restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/validFarmer/"+fid, Boolean.class))
		{
		try {
				Optional<CropDetails> op1=repo.findByNameAndId(crop.getCropName(), fid);
				Optional<CropDetails> op=repo.findByIdAndFid(id, fid);
				if(op1.isPresent() && !op.get().getCropName().equals(crop.getCropName())) {
					return new ResponseEntity<Object>("You have already added "+crop.getCropName(), HttpStatus.BAD_REQUEST);
				}
				else {
					
					if(op.isPresent())
					{
						CropDetails crop1=op.get();
						crop.setId(crop1.getId());
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
						LocalDateTime now = LocalDateTime.now(); 
						String dt=dtf.format(now);  
						String date=dt.substring(0, 10);
						crop.setDate(date);
						crop.setFarmerId(fid);
						repo.save(crop);
						return new ResponseEntity<Object>("The Data is update successfully of "+crop1.getCropName(), HttpStatus.OK);
			
					}
					else
					{
						return new ResponseEntity<Object>("NOT FOUND", HttpStatus.NOT_FOUND);
					}
				}
		}catch(Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
		else {
			return new ResponseEntity<Object>("Invalid UserId", HttpStatus.BAD_REQUEST);
		}
	}
	
	//By Criteria Query
	@GetMapping("/findbyfarmerid/{fid}")
	List<CropDetails> findbyfarmerid(@PathVariable("fid") int fid){
		if(restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/validFarmer/"+fid, Boolean.class))
		{
		Query query=new Query();
		query.addCriteria(Criteria.where("farmerId").is(fid));
		return mongoTemplate.find(query, CropDetails.class);
		}
		else {
			return null;
		}
	}
	
//	By Query Annotation
	@GetMapping("/findbyname/{cropName}")
	public ResponseEntity<Object>  findbyname(@PathVariable("cropName") String cropName){
		try {
			List<CropDetails> list=new ArrayList<>();
			repo.findByName(cropName).forEach(list::add);
			if(!list.isEmpty()) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Object>("Sorry! "+cropName+" is not available", HttpStatus.NO_CONTENT);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@GetMapping("/findbyNameAndQuantity/{cropName}/{qnt}")
	public ResponseEntity<Object>  findbyNameAndQuantity(@PathVariable("cropName") String cropName, @PathVariable("qnt") double qnt){
		try {
			List<CropDetails> list=new ArrayList<>();
			repo.findByCropNameAndQunatity(cropName, qnt).forEach(list::add);
			if(!list.isEmpty()) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Object>("Sorry! "+qnt+" kg "+cropName+" is not available.", HttpStatus.NO_CONTENT);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@GetMapping("/findbyNameAndPrice/{cropName}/{price}")
	public ResponseEntity<Object>  findbyNameAndPrice(@PathVariable("cropName") String cropName, @PathVariable("price") double price){
		try {
			List<CropDetails> list=new ArrayList<>();
			repo.findByCropNameAndPrice(cropName, price).forEach(list::add);
			if(!list.isEmpty()) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Object>("Sorry! "+cropName+" is not available for Rs.-"+price+" per kg", HttpStatus.NO_CONTENT);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@GetMapping("/findbyNameAndPriceAndQuantity/{cropName}/{price}/{qnt}")
	public ResponseEntity<Object>  findbyNameAndPrice(@PathVariable("cropName") String cropName, @PathVariable("price") double price, @PathVariable("qnt") double qnt){
		try {
			List<CropDetails> list=new ArrayList<>();
			repo.findByCropNameAndPriceAndQunatity(cropName, price, qnt).forEach(list::add);
			if(!list.isEmpty()) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Object>("Sorry! "+qnt+" kg "+cropName+" is not available for Rs.-"+price+" per kg", HttpStatus.NO_CONTENT);
			}
			}catch(Exception e) {
				return new ResponseEntity<Object>("There have some problem: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	@GetMapping("/findbynameid/{cropName}/{fid}")
	CropDetails findByNameAndId(@PathVariable("cropName") String cropName, @PathVariable("fid") int fid){
		
		return repo.findByNameAndId(cropName, fid).get();
	}
	
	
	

}
