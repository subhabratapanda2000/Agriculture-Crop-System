package microService.jwtManagement.models;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private final int  id;
    private final String role;
    private final String name;

    public AuthenticationResponse(String jwt, int id, String role, String name) {
        this.jwt = jwt;
        this.id=id;
        this.role=role;
        this.name=name;
        
    }

    public String getJwt() {
        return jwt;
    }
    
    public int getId() {
    	return id;
    }

	public String getRole() {
		return role;
	}
	
	public String getName() {
		return name;
	}
    
    
}