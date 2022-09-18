package microService.securityManagement.models;

import java.util.List;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetails {
	private int orderId;
	private int customerId;
	private String result;
	private List<TreeMap<String, String>> transactionDeatils;

}
