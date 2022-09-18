package microService.jwtManagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import microService.jwtManagement.models.PaymentDetails;

@Service
public class PaymentService {

	@Autowired
	private RestTemplate restTemplate;
	
	public PaymentDetails findPaymentByCustomerId(int cId)  throws Exception {
		   return restTemplate.getForObject("http://Cloud-Api-Gateway/payment/getByUserId/"+cId, PaymentDetails.class);
	}
		
	
//	@HystrixCommand(fallbackMethod = "findAllCropsFallback")
	public List<PaymentDetails> findAllPayments()  throws Exception {
		return restTemplate.getForObject("http://Cloud-Api-Gateway/payment/allPayment", List.class);
	}
}
