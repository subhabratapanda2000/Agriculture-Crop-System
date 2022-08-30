package microService.cropManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microService.cropManagement.models.CropDetails;
import microService.cropManagement.repo.CropRepository;

@Service
public class CropService {
	@Autowired
	CropRepository repo;
	
	public CropDetails save(CropDetails crop) {
		System.out.println(crop);
		return repo.save(crop);
	}
	
	public CropDetails findById(int id) {
		Optional<CropDetails> op = repo.findById(id);
		if(op.isPresent()) {
			
			return op.get();
		}
		else {
			return null;
		}
		
	}
	

	public List<CropDetails> findAll(){
		List<CropDetails> list=repo.findAll();
		System.out.println(list);
		return list;
	}
	
	
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	public long count() {
		return repo.count();
	}
	
	public List<CropDetails> findByName(String name){
	
	   return repo.findByName(name);
	}
	
	
	public List<CropDetails> findByNameAndId(String name, int id){
		
		return repo.findByNameAndId(name, id);
	}
	
	
	
}
