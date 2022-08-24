package microService.cropManagement.controller;

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
	
	@PostMapping("/create")
	public ResponseEntity<String> insertCrops(@RequestBody CropDetails crop) {
		try {
			repo.save(crop);
		System.out.println("Save...");
		System.out.println(crop);
		return new ResponseEntity<String>("Crop add successfully", HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
	
	@DeleteMapping("deletecrop/{id}")
	public ResponseEntity<String> deleteEmployeeByID(@PathVariable("id") int id) {
		try {
		Optional<CropDetails> op = repo1.findById(id);
		if(op.isPresent()) {
			repo.deleteById(id);
		    return new ResponseEntity<String>("Delete Successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("There is no Crop Data", HttpStatus.NO_CONTENT);
		}
		}catch(Exception e) {
			return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/updateCrop/{id}")
	public ResponseEntity<Object> updateCropById(@PathVariable("id") int id, @RequestBody CropDetails crop)
	{
		try {
		Optional<CropDetails> op=repo1.findById(id);
		if(op.isPresent())
		{
			CropDetails crop1=op.get();
			repo.save(crop);
			return new ResponseEntity<Object>("The Data is update successfully of "+crop1.getCropName(), HttpStatus.OK);
			
		}
		else
		{
			return new ResponseEntity<Object>("NOT FOUND", HttpStatus.NOT_FOUND);
		}
		}catch(Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//By Criteria Query
	@GetMapping("/findbyfarmerid/{fid}")
	List<CropDetails> findbyname(@PathVariable("fid") int fid){
		Query query=new Query();
		query.addCriteria(Criteria.where("farmerId").is(fid));
		return mongoTemplate.find(query, CropDetails.class);
	}
	
//	By Query Annotation
	@GetMapping("/findbyname/{name}")
	List<CropDetails> findbyname(@PathVariable("name") String name){
		
		return repo.findByName(name);
	}
	
	@GetMapping("/findbynameid/{name}/{id}")
	List<CropDetails> findByNameAndId(@PathVariable("name") String name, @PathVariable("id") int id){
		
		return repo.findByNameAndId(name, id);
	}
	
	
	

}
