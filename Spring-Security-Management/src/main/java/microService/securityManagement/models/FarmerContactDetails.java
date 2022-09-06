package microService.securityManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerContactDetails {
	private String farmerName;
	private String mobileNo;
	private String address;
	

}
