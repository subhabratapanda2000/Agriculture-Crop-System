package microService.jwtManagement.models;

import java.util.List;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentAck {
	private int orderId;
	private int customerId;
	private String result;
	private TreeMap<String, String> transactionDeatils;

}
