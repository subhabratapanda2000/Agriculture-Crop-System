package microService.cropManagement.controller;

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

import microService.cropManagement.models.CropDetails;
import microService.cropManagement.repo.CropRepository;

@RestController
@RequestMapping("/crop")
public class CropController {
	@Autowired
	CropRepository repo;
	
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
		Optional<CropDetails> op = repo.findById(id);
		
		
		if(op.isPresent()) {
			CropDetails crop = op.get();
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
			return new ResponseEntity<Object>("There is no Employee Data", HttpStatus.NO_CONTENT);
		}
		}catch(Exception e) {
			return new ResponseEntity<Object>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@DeleteMapping("deletecrop/{id}")
	public ResponseEntity<String> deleteEmployeeByID(@PathVariable("id") int id) {
		try {
		Optional<CropDetails> op = repo.findById(id);
		if(op.isPresent()) {
			repo.deleteById(id);
		    return new ResponseEntity<String>("Delete Successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("There is no Employee Data", HttpStatus.NO_CONTENT);
		}
		}catch(Exception e) {
			return new ResponseEntity<String>("There have some problem", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/updateCrop/{id}")
	public ResponseEntity<Object> updateCropById(@PathVariable("id") int id, @RequestBody CropDetails crop)
	{
		try {
		Optional<CropDetails> op=repo.findById(id);
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
	

}
