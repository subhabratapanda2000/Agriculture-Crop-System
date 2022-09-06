package microService.farmerManagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import microService.farmerManagement.models.CropDetails;

@Service
public class CropService {
	

	@Autowired
	private RestTemplate restTemplate;
	
//	@HystrixCommand(fallbackMethod = "getFallbackCrop")
	public List<CropDetails> getCrops(int userId){
		   return restTemplate.getForObject("http://Cloud-Api-Gateway/crop/findbyfarmerid/"+userId, List.class);
	}
//	public List<CropDetails> getFallbackCrop(int userId){
//		CropDetails cd=new CropDetails(0, "No Crop is available", 0,0,0, "");
//		List<CropDetails> ls=new ArrayList<CropDetails>();
//		ls.add(cd);
//		return ls;
//	}
	
}
