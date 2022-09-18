package microService.jwtManagement.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import microService.jwtManagement.models.UserData;

@Service
public class UsersService {
	@Autowired
	private RestTemplate restTemplate;
	
//	@HystrixCommand(fallbackMethod = "findByUserNameOfUserFallback")
	public Optional<UserData> findByUserNameOfUser(String userName){
		UserData user= restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/findByUserName/"+userName, UserData.class);
		return Optional.of(user);
	}
//	public Optional<UserData> findByUserNameOfUserFallback(String userName){
//		Optional<UserData> op=Optional.of(new UserData());
//		return op;
//	}
	
	@HystrixCommand(fallbackMethod = "findAllFarmersFallback")
	public List<UserData> findAllFarmers(){
		return restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/allfarmer", List.class);
		
	}
	public List<UserData> findAllFarmersFallback(){
		List<UserData> ls=new ArrayList();
		ls.add(new UserData());
		return ls;
		
	}

}
