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
	
	public int count() {
		return ((int)repo.count());
	}
	

	public List<FarmerDetails> findAll(){
		List<FarmerDetails> list=repo.findAllFarmer("ROLE_FARMER");
		System.out.println(list);
		return list;
	}
	
	
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	public List<FarmerDetails> findActiveFarmer(boolean b){
		return repo.findActiveFarmer(b, "ROLE_FARMER");
	}
	
	public List<FarmerDetails> findAllPrimeFarmers(){
		return repo.findAllPrimeMember(true, "ROLE_FARMER");
	}
	
	public Optional<FarmerDetails> findByUserName(String userName) {
		return repo.findByUserName(userName);
	}
	public Optional<FarmerDetails> findByMobile(String mobile) {
		return repo.findByMobile(mobile);
	}
	public Optional<FarmerDetails> findByName(String name) {
		return repo.findByName(name);
	}
	
	

}
