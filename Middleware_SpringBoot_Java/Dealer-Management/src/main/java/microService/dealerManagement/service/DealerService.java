package microService.dealerManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microService.dealerManagement.models.DealerDetails;
import microService.dealerManagement.repository.DealerRepository;

@Service
public class DealerService {
	@Autowired
	DealerRepository repo;
	
	public DealerDetails save(DealerDetails dealer) {
		System.out.println(dealer);
		return repo.save(dealer);
	}
	
	public Optional<DealerDetails> findById(int id) {
		Optional<DealerDetails> op = repo.findById(id);
		return op;
		
	}
	
	public int count() {
		return ((int)repo.count());
	}
	

	public List<DealerDetails> findAll(){
		List<DealerDetails> list=repo.findAllDealer("ROLE_DEALER");
		System.out.println(list);
		return list;
	}
	
	
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	public List<DealerDetails> findActiveDealer(boolean b){
		return repo.findActiveDealer(b, "ROLE_DEALER");
	}
	
	public List<DealerDetails> findAllPrimeDealers(){
		return repo.findAllPrimeMember(true, "ROLE_DEALER");
	}
	
	public Optional<DealerDetails> findByUserName(String userName) {
		return repo.findByUserName(userName);
	}
	public Optional<DealerDetails> findByMobile(String mobile) {
		return repo.findByMobile(mobile);
	}
	public Optional<DealerDetails> findByName(String name) {
		return repo.findByName(name);
	}
	

}