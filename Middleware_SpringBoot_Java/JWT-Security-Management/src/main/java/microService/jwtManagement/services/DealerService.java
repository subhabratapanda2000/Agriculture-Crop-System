package microService.jwtManagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import microService.jwtManagement.models.CropRequirements;
import microService.jwtManagement.models.DealerDetails;
import microService.jwtManagement.models.PasswordChange;

@Service
public class DealerService {
	@Autowired
	private RestTemplate restTemplate;
	
//		@HystrixCommand(fallbackMethod = "findAllDealersFallback")
		public List<DealerDetails> findAllDealers() throws Exception{
			return restTemplate.getForObject("http://Cloud-Api-Gateway/dealer/alldealer", List.class);
		
		}
		
		public String sendOffer(CropRequirements req, int id) throws Exception{
			return restTemplate.postForObject("http://Cloud-Api-Gateway/dealer/sendOffer/"+id,req, String.class);
		}
	
		//Crate Farmer
//		@HystrixCommand(fallbackMethod = "createDealersFallback")
		public String createDealers(DealerDetails dealer) throws Exception{
			return restTemplate.postForObject("http://Cloud-Api-Gateway/dealer/create",dealer, String.class);
		}
	
		//Update Farmer
//		@HystrixCommand(fallbackMethod = "updateDealerFallback")
		public String updateDealers(DealerDetails dealer, int id) throws Exception{
			restTemplate.put("http://Cloud-Api-Gateway/dealer/update/"+id,dealer);
			return "Update";
		}

		//findById
//		@HystrixCommand(fallbackMethod = "findByIdOfDealersFallback")
		public DealerDetails findByIdOfDealers(int id) throws Exception{
			return restTemplate.getForObject("http://Cloud-Api-Gateway/dealer/getById/"+id, DealerDetails.class);
			
		}
		
		public List<DealerDetails> findAllPrimeDealers() throws Exception{
			return restTemplate.getForObject("http://Cloud-Api-Gateway/dealer/allPrimeDealer", List.class);
		
		}
		
		public List<DealerDetails> findAllActiveDealers() throws Exception{
			return restTemplate.getForObject("http://Cloud-Api-Gateway/dealer/findallactivedealers", List.class);
		
		}
		
		//deactivateById
//		@HystrixCommand(fallbackMethod = "deactivateByIdOfDealersFallback")
		public String deactivateByIdOfDealers(int id) throws Exception{
			restTemplate.put("http://Cloud-Api-Gateway/dealer/deactivate/"+id, null);
			return "Deactivate Successfully";
			
		}
		

		public String changePasswordOfDealer(int id, PasswordChange pass) throws Exception{
			restTemplate.put("http://Cloud-Api-Gateway/dealer/changePassword/"+id, pass);
			return "Password Change Successfully";
			
		}
		
		public String payForPrime(int id) throws Exception{
			return restTemplate.getForObject("http://Cloud-Api-Gateway/dealer/payForPrime/"+id, String.class);	
		}
		
		
		
		
//		public List<DealerDetails> findAllDealersFallback(){
//			List<DealerDetails> ls=new ArrayList();
//			ls.add(new DealerDetails());
//			return ls;
//			
//		}
//		
//		public String createDealersFallback(DealerDetails dealer){
//			return "Operation Failed";
//			
//		}
//		
//		public String updateDealerFallback(DealerDetails farmer, int id){
//			return "Operation Failed";
//			
//		}
//		
//		public DealerDetails findByIdOfDealersFallback(int id){
//			return new DealerDetails();
//			
//		}
//		
//		public String deactivateByIdOfDealersFallback(int id){
//			return "Deactivate operation unsuccessful";
//			
//		}
//		

	

}



