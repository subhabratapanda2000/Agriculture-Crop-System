package microService.jwtManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private int  id;
	private String accessToken;
	private String username;
	private String password;

}
