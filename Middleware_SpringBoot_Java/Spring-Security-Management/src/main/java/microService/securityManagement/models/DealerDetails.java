package microService.securityManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class DealerDetails {
	private int fid;

	private String name;

	private String userName;

	private String password;

	private String mobileNo;

	private String address;

	private boolean active;

	private String role;
	private String joinDate;
	
	private boolean primeMember;



}