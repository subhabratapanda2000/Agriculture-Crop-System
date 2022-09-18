package microService.jwtManagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import microService.jwtManagement.models.FarmerContactDetails;
import microService.jwtManagement.models.FarmerDetails;
import microService.jwtManagement.models.FarmerDetailsWithCrops;
import microService.jwtManagement.models.PasswordChange;
import microService.jwtManagement.models.SendOfferDetails;
@Service
public class FarmerService {
	@Autowired
	private RestTemplate restTemplate;
	
//		@HystrixCommand(fallbackMethod = "findAllFarmersFallback")
		public List<FarmerDetails> findAllFarmers() throws Exception{
			return restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/allfarmer", List.class);
		
		}
		
		public List<SendOfferDetails> getOffers() throws Exception{
			return restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/getOffers", List.class);
		
		}
		
		public FarmerDetailsWithCrops getFarmerDetailsWithCrops(int id) throws Exception{
			return restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/farmerDetailsWithCrops/"+id, FarmerDetailsWithCrops.class);	
		}
		
	//Crate Farmer
//		@HystrixCommand(fallbackMethod = "createFarmersFallback")
		public String createFarmers(FarmerDetails farmer) throws Exception{
			return restTemplate.postForObject("http://Cloud-Api-Gateway/farmer/create",farmer, String.class);	
		}
		
		public List<FarmerDetails> findAllPrimeFarmers() throws Exception{
			return restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/allPrimeFarmer", List.class);
		
		}
		
		public List<FarmerDetails> findAllActiveFarmers() throws Exception{
			return restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/findallactivefarmers", List.class);
		
		}
	
	//Update Farmer
//		@HystrixCommand(fallbackMethod = "updateFarmersFallback")
		public String updateFarmers(FarmerDetails farmer, int id) throws Exception{
			restTemplate.put("http://Cloud-Api-Gateway/farmer/update/"+id,farmer);
			return "Update";
		}
		
		
		//findById
//		@HystrixCommand(fallbackMethod = "findByIdOfFarmersFallback")
		public FarmerDetails findByIdOfFarmers(int id) throws Exception{
			return restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/getById/"+id, FarmerDetails.class);	
		}
		
		
		//deactivateById
//		@HystrixCommand(fallbackMethod = "deactivateByIdOfFarmersFallback")
		public String deactivateFarmerById(int id) throws Exception{
			restTemplate.put("http://Cloud-Api-Gateway/farmer/deactivate/"+id, null);
			return "Deactivate Successfully";
			
		}
		
		public String changePasswordOfFarmer(int id, PasswordChange pass) throws Exception{
			restTemplate.put("http://Cloud-Api-Gateway/farmer/changePassword/"+id, pass);
			return "Password Change Successfully";
			
		}
		
		public String payForPrime(int id) throws Exception{
			return restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/payForPrime/"+id, String.class);	
		}
		
		public FarmerContactDetails findFarmerContact(int id) throws Exception{
			return restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/findFarmerContact/"+id, FarmerContactDetails.class);	
		}
		
		
//		public String createFarmersFallback(FarmerDetails farmer){
//			return "Operation Failed";
//			
//		}
//		
//		public String updateFarmersFallback(FarmerDetails farmer, int id){
//			return "Operation Failed";
//			
//		}
//		
//		public FarmerDetails findByIdOfFarmersFallback(int id){
//			return new FarmerDetails();
//			
//		}
//		
//		
//		public String deactivateByIdOfFarmersFallback(int id){
//			return "Deactivate operation unsuccessful";
//			
//		}
//		
//		public List<FarmerDetails> findAllFarmersFallback(){
//			List<FarmerDetails> ls=new ArrayList();
//			ls.add(new FarmerDetails());
//			return ls;
//			
//		}

	

}
