package microService.farmerManagement.rabbitmq;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import microService.farmerManagement.models.CropRequirements;
import microService.farmerManagement.models.DealerBasicInfo;
import microService.farmerManagement.models.SendOfferDetails;

@Component
public class ReceiverMsg {
public static List<SendOfferDetails> offer=new ArrayList<>();
public static String s="";
	
	@RabbitListener(queues = "notification_queue")
    public static void consumeMessageFromQueue(SendOfferDetails offers) {
		
		if(offers!=null) {
		DealerBasicInfo d=offers.getDealerInfo();
		CropRequirements cr=offers.getCropRequirements();
        s+="\n\nHii.. a offer has been come from dealer\n************************************************\n"
        		+ d.getName()+" wants to buy "+cr.getQuantity()+" kg "+cr.getCropName()+" for Rs.- "+cr.getPrice()+"/-"
        		+"\nDealer's Mobile No.- "+d.getMobileNo()+"\nDealer's Address:- "+d.getAddress()+
        		"\nIf you interested to this deal then please contact to the Dealer.\nThank You!";
        offer.add(offers);
		}else {
			s+="No offer!";
		}
		System.out.println("hello"+s);
    }

}
