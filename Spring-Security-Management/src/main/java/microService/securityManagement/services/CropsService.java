package microService.securityManagement.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import microService.securityManagement.models.CropDetails;

@Service
public class CropsService {
	@Autowired
	private RestTemplate restTemplate;
	
//	@HystrixCommand(fallbackMethod = "addCropFallback")
	public String addCrops(CropDetails crop, int fid) throws Exception {
		return restTemplate.postForObject("http://Cloud-Api-Gateway/crop/create/"+fid,crop, String.class);
	}
	
//	@HystrixCommand(fallbackMethod = "getFallbackCrop")
	public CropDetails findCropById(int cId)  throws Exception {
		   return restTemplate.getForObject("http://Cloud-Api-Gateway/crop/getcrop/"+cId, CropDetails.class);
	}
		
	
//	@HystrixCommand(fallbackMethod = "findAllCropsFallback")
	public List<CropDetails> findAllCrops()  throws Exception {
		return restTemplate.getForObject("http://Cloud-Api-Gateway/crop/allCrop", List.class);
	}
	
	public String deleteCropByIdAndFid(int cid, int fid)  throws Exception {
		restTemplate.delete("http://Cloud-Api-Gateway/crop/deletecrop/"+cid+"/"+fid);
		return "Delete Successfully";
	}
		
	public String updateCropByIdAndFid(CropDetails crop, int cid, int fid) throws Exception  {
		restTemplate.put("http://Cloud-Api-Gateway/crop/updateCrop/"+cid+"/"+fid,crop);
		return "The Data is update successfully ";
	}
		
	public List<CropDetails> findCropsByFarmerId(int fid)  throws Exception {
		return restTemplate.getForObject("http://Cloud-Api-Gateway/crop/findbyfarmerid/"+fid, List.class);
	}
	
	public List<CropDetails> findByCropName(String cropName)  throws Exception {
		return restTemplate.getForObject("http://Cloud-Api-Gateway/crop/findbyname/"+cropName, List.class);
	}
	
	public List<CropDetails> findByCropNameAndQuantity(String cropName, double quantity)  throws Exception {
		return restTemplate.getForObject("http://Cloud-Api-Gateway/crop/findbyNameAndQuantity/"+cropName+"/"+quantity, List.class);
	}
		
	public List<CropDetails> findByCropNameAndPrice(String cropName, double price)  throws Exception {
		return restTemplate.getForObject("http://Cloud-Api-Gateway/crop/findbyNameAndPrice/"+cropName+"/"+price, List.class);
	}
		
	public List<CropDetails> findByCropNameAndPriceAndQuantity(String cropName, double price, double quantity)  throws Exception {
		return restTemplate.getForObject("http://Cloud-Api-Gateway/crop/findbyNameAndPriceAndQuantity/"+cropName+"/"+price+"/"+quantity, List.class);
	}	
	
		
		
		
		
		
//		public String addCropFallback(CropDetails crop, int fid) {
//			return "No Crop has added";
//		}
//		
//		public List<CropDetails> getFallbackCrop(int userId){
//			CropDetails cd=new CropDetails(0, "No Crop is available", 0,0,0);
//			List<CropDetails> ls=new ArrayList<CropDetails>();
//			ls.add(cd);
//			return ls;
//		}
//		
//		public List<CropDetails> findAllCropsFallback() {
//			CropDetails cd=new CropDetails(0, "No Crop is available", 0,0,0);
//			List<CropDetails> ls=new ArrayList<CropDetails>();
//			ls.add(cd);
//			return ls;
//		}
	

}
