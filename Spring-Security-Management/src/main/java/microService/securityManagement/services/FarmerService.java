package microService.securityManagement.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import microService.securityManagement.models.FarmerDetails;
import microService.securityManagement.models.UserData;
@Service
public class FarmerService {
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "findAllFarmersFallback")
	public List<FarmerDetails> findAllFarmers(){
		return restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/allfarmer", List.class);
		
	}
	public List<FarmerDetails> findAllFarmersFallback(){
		List<FarmerDetails> ls=new ArrayList();
		ls.add(new FarmerDetails());
		return ls;
		
	}
	//Crate Farmer
	@HystrixCommand(fallbackMethod = "createFarmersFallback")
	public String createFarmers(FarmerDetails farmer){
		try {
			
			return restTemplate.postForObject("http://Cloud-Api-Gateway/farmer/create",farmer, String.class);
			
		}
		catch(Exception e) {
			
			return e.getMessage();
		}
		
	}
	public String createFarmersFallback(FarmerDetails farmer){
		return "Operation Failed";
		
	}
	
	
	//Update Farmer
		@HystrixCommand(fallbackMethod = "updateFarmersFallback")
		public String updateFarmers(FarmerDetails farmer, int id){
			try {
			restTemplate.put("http://Cloud-Api-Gateway/farmer/update/"+id,farmer);
			return "Update";
			}
			catch(Exception e) {
				
				return e.getMessage();
			}
			
		}
		public String updateFarmersFallback(FarmerDetails farmer, int id){
			return "Operation Failed";
			
		}
		
		
		//findById
		@HystrixCommand(fallbackMethod = "findByIdOfFarmersFallback")
		public FarmerDetails findByIdOfFarmers(int id){
			return restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/getById/"+id, FarmerDetails.class);
			
		}
		public FarmerDetails findByIdOfFarmersFallback(int id){
			return new FarmerDetails();
			
		}
		
		
		//deactivateById
		@HystrixCommand(fallbackMethod = "deactivateByIdOfFarmersFallback")
		public String deactivateByIdOfFarmers(int id){
			restTemplate.put("http://Cloud-Api-Gateway/farmer/deactivate/"+id, null);
			return "Deactivate Successfully";
			
		}
		public String deactivateByIdOfFarmersFallback(int id){
			return "Deactivate operation unsuccessful";
			
		}
		

	

}
