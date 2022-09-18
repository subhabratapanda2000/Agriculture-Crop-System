package microService.farmerManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DealerBasicInfo {
	private String name;
	private String mobileNo;
	private String address;

}
