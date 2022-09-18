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

@Document(collection="UsersDetails")
public class FarmerDetails {
	@Id
	private int fid;
	@Field
	private String name;
	@Field
	private String userName;
	@Field
	private String password;
	@Field
	private String mobileNo;
	@Field
	private String address;
	@Field
	private boolean active;
	@Field
	private String role;
	@Field
	private String joinDate;
	@Field
	private boolean primeMember;


}
