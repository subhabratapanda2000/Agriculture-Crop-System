package microService.securityManagement.models;

import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

//@Document(collection="FarmerDetails")
public class FarmerDetailsWithCrops {

	private int fid;
	private String name;
	private String address;
	private String mobileNo;
	private List<CropDetails> cropsDetails;
}
