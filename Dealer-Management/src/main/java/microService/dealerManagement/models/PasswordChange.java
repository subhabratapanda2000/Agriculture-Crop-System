package microService.dealerManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChange {
	private String mobileNo;
	private String oldPassword;
	private String newPassword;
	

}
