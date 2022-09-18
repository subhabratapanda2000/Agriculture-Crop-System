package microService.paytmManagement.model;

import java.util.List;
import java.util.TreeMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="PaymentDetails")
public class PaymentDetails {
	@Id
	private int orderId;
	@Field
	private int customerId;
	@Field
	private String result;
	@Field
	private List<TreeMap<String, String>> transactionDeatils;

}
