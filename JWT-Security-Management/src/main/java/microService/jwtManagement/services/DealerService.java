package microService.jwtManagement.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import microService.jwtManagement.models.DealerDetails;

@Service
public class DealerService {
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "findAllDealersFallback")
	public List<DealerDetails> findAllDealers(){
		return restTemplate.getForObject("http://Cloud-Api-Gateway/dealer/alldealer", List.class);
		
	}
	public List<DealerDetails> findAllDealersFallback(){
		List<DealerDetails> ls=new ArrayList();
		ls.add(new DealerDetails());
		return ls;
		
	}
	//Crate Farmer
	@HystrixCommand(fallbackMethod = "createDealersFallback")
	public String createDealers(DealerDetails dealer){
		try {
			
			return restTemplate.postForObject("http://Cloud-Api-Gateway/dealer/create",dealer, String.class);
			
		}
		catch(Exception e) {
			
			return e.getMessage();
		}
		
	}
	public String createDealersFallback(DealerDetails dealer){
		return "Operation Failed";
		
	}
	
	
	//Update Farmer
		@HystrixCommand(fallbackMethod = "updateDealerFallback")
		public String updateDealers(DealerDetails dealer, int id){
			try {
			restTemplate.put("http://Cloud-Api-Gateway/dealer/update/"+id,dealer);
			return "Update";
			}
			catch(Exception e) {
				
				return e.getMessage();
			}
			
		}
		public String updateDealerFallback(DealerDetails farmer, int id){
			return "Operation Failed";
			
		}
		

		//findById
		@HystrixCommand(fallbackMethod = "findByIdOfDealersFallback")
		public DealerDetails findByIdOfDealers(int id){
			return restTemplate.getForObject("http://Cloud-Api-Gateway/dealer/getById/"+id, DealerDetails.class);
			
		}
		public DealerDetails findByIdOfDealersFallback(int id){
			return new DealerDetails();
			
		}
		
		
		//deactivateById
		@HystrixCommand(fallbackMethod = "deactivateByIdOfDealersFallback")
		public String deactivateByIdOfDealers(int id){
			restTemplate.put("http://Cloud-Api-Gateway/dealer/deactivate/"+id, null);
			return "Deactivate Successfully";
			
		}
		public String deactivateByIdOfDealersFallback(int id){
			return "Deactivate operation unsuccessful";
			
		}
		

	

}



