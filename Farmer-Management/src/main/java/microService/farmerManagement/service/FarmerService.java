package microService.farmerManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microService.farmerManagement.models.FarmerDetails;
import microService.farmerManagement.repository.FarmerRepository;

@Service
public class FarmerService {
	@Autowired
	FarmerRepository repo;
	
	public FarmerDetails save(FarmerDetails farmer) {
		System.out.println(farmer);
		return repo.save(farmer);
	}
	
	public Optional<FarmerDetails> findById(int id) {
		Optional<FarmerDetails> op = repo.findById(id);
		return op;
		
	}
	

	public List<FarmerDetails> findAll(){
		List<FarmerDetails> list=repo.findAll();
		System.out.println(list);
		return list;
	}
	
	
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	

}
