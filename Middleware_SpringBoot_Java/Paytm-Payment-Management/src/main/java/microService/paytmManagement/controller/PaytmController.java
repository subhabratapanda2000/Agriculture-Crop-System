package microService.paytmManagement.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.paytm.pg.merchant.CheckSumServiceHelper;

import microService.paytmManagement.model.PaymentDetails;
import microService.paytmManagement.model.PaymentException;
import microService.paytmManagement.model.PaytmDetails;
import microService.paytmManagement.repository.PaymentRepository;

@RestController
@RequestMapping("/payment")
@Controller
public class PaytmController {
	private int customerId;
	private int orderID;
	
	@Autowired
	private PaytmDetails paytmDetails;
	@Autowired
	private Environment env;
	@Autowired
	private PaymentRepository repo;
	@Autowired
	private RestTemplate restTemplate;
	

	 @GetMapping(value = "/pgredirect/{userId}")
	    public ModelAndView getRedirect(@PathVariable("userId") int userId ){
		 try {
		 Optional<PaymentDetails> op = repo.findByUserId(userId);
			if(op.isPresent()) {
				PaymentDetails payment=op.get();
				if(payment.getResult().equals("Payment Successful"))
				{
					 throw new PaymentException("You are already a prime customer");
				}
			}
		 Random rd = new Random(); // creating Random object
		 this.orderID=rd.nextInt();
	      String orderId=Integer.toString(this.orderID) ;
	      String customerId=Integer.toString(userId) ;
	      String transactionAmount=Double.toString(50); //Payment Amount

	        ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetails.getPaytmUrl());
	        TreeMap<String, String> parameters = new TreeMap<>();
	        paytmDetails.getDetails().forEach((k, v) -> parameters.put(k, v));
	        parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
	        parameters.put("EMAIL", env.getProperty("paytm.email"));
	        parameters.put("ORDER_ID", orderId);
	        parameters.put("TXN_AMOUNT", transactionAmount);
	        parameters.put("CUST_ID", customerId);
	        this.customerId=userId;
	        String checkSum = getCheckSum(parameters);
	        parameters.put("CHECKSUMHASH", checkSum);
	        modelAndView.addAllObjects(parameters);
	        return modelAndView;
		 }
		 catch(Exception e) {
			 ModelAndView modelAndView = new ModelAndView(e.getMessage());
			 return modelAndView;
		 }
	    }
	 
	 
	 @PostMapping(value = "/pgresponse")
	    public ResponseEntity<Object> getResponseRedirect(HttpServletRequest request, Model model) {

	        Map<String, String[]> mapData = request.getParameterMap();
	        TreeMap<String, String> parameters = new TreeMap<String, String>();
	        mapData.forEach((key, val) -> parameters.put(key, val[0]));
	        String paytmChecksum = "";
	        if (mapData.containsKey("CHECKSUMHASH")) {
	            paytmChecksum = mapData.get("CHECKSUMHASH")[0];
	        }
	        String result;

	        boolean isValideChecksum = false;
	        System.out.println("RESULT : "+parameters.toString());
	        try {
	            isValideChecksum = validateCheckSum(parameters, paytmChecksum);
	            if (isValideChecksum && parameters.containsKey("RESPCODE")) {
	                if (parameters.get("RESPCODE").equals("01")) {
	                    result = "Payment Successful";
	                } else {
	                    result = "Payment Failed";
	                }
	            } else {
	                result = "Checksum mismatched";
	            }
	        } catch (Exception e) {
	            result = e.toString();
	        }
	        parameters.remove("CHECKSUMHASH");
	        Optional<PaymentDetails> op = repo.findByUserId(this.customerId);
			if(op.isPresent()) {
				//update
			   PaymentDetails payment=op.get();
			   payment.getTransactionDeatils().add(parameters);
			   payment.setResult(result);
			   repo.save(payment);	  
			}else {	
	        List<TreeMap<String, String>> transactionDeatils=new ArrayList<>();
	        transactionDeatils.add(parameters);
	        PaymentDetails payment=new PaymentDetails(this.orderID, this.customerId, result, transactionDeatils);
	        if(this.customerId!=0 && this.orderID!=0) {
		        repo.save(payment);}}
			Optional<PaymentDetails> opt = repo.findByUserId(this.customerId);
			String isPrime="";
			if(opt.isPresent()) {
			   PaymentDetails payment=opt.get();
			   if(payment.getResult().equals("Payment Successful"))
				{
				  isPrime= restTemplate.getForObject("http://Cloud-Api-Gateway/farmer/primeUser/"+this.customerId, String.class);
				}
			   
			}
	        model.addAttribute("parameters",parameters);
	        model.addAttribute("result",result);
	        model.addAttribute("IsPrime",isPrime);
	        model.addAttribute("customerId",this.customerId);
	        System.out.println(model);
	        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:3000/paymentAck")).build();
	    }

	    private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
	        return CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(paytmDetails.getMerchantKey(),
	                parameters, paytmChecksum);
	    }


	private String getCheckSum(TreeMap<String, String> parameters) throws Exception {
		return CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(paytmDetails.getMerchantKey(), parameters);
	}
	
	@GetMapping("/allPayment")
	public ResponseEntity<Object> getAll(){
		try {
		List<PaymentDetails> list=new ArrayList<>();
		repo.findAll().forEach(list::add);
		if(!list.isEmpty()) {
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Object>("There is no Payments", HttpStatus.NO_CONTENT);
		}
		}catch(Exception e) {
			return new ResponseEntity<Object>("There have some problem, \n "+e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping("/getByUserId/{id}")
	public ResponseEntity<Object> findById(@PathVariable("id") int id) {
		Optional<PaymentDetails> op = repo.findByUserId(id);
		if(op.isPresent()) {
		   PaymentDetails payment=op.get();
		  
			   return new ResponseEntity<Object>(payment, HttpStatus.OK);
		}else {
			return new ResponseEntity<Object>("Not found any payment of this customer.", HttpStatus.NOT_FOUND);
		}
	}
	
	
	
}