package microService.farmerManagement.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection="FarmerDetails")
public class FarmerDetails {
	@Id
	private int fid;
	@Field
	private String name;
	@Field
	private String address;
	@Field
	private String mobileNo;


}
