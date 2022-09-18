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
		List<CropDetails> list=repo.findByQunatity(0);
		//System.out.println(list);
		return list;
	}
	
	
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	public long count() {
		return repo.count();
	}
	
	public List<CropDetails> findByName(String cropName){
	
	   return repo.findByName(cropName);
	}
	
	public List<CropDetails> findByCropNameAndQunatity(String cropName, double qty){
		
		   return repo.findByCropNameAndQunatity(cropName, qty-1);
		}
	
	public List<CropDetails> findByCropNameAndPrice(String cropName, double price){
		
		   return repo.findByCropNameAndPrice(cropName, price+1);
		}
	
	public List<CropDetails> findByCropNameAndPriceAndQunatity(String cropName, double price, double qty){
		
		   return repo.findByCropNameAndPriceAndQunatity(cropName, price+1, qty-1);
		}
	
	
	public Optional<CropDetails> findByNameAndId(String cropName, int fid){
		
		return repo.findByNameAndId(cropName, fid);
	}
	
public Optional<CropDetails> findByIdAndFid(int cid, int fid){
		
		return repo.findByIdAndFid(cid, fid);
	}
	
	
	
}
