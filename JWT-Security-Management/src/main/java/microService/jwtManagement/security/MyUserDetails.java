package microService.jwtManagement.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import microService.jwtManagement.models.UserData;

public class MyUserDetails implements UserDetails {


	 private String userName;
	    private String password;
	    private boolean active;
	    private String authorities;
	    private int id;

	    public MyUserDetails(UserData farmer) {
	        this.userName = farmer.getUserName();
	        this.password = farmer.getPassword();
	        this.active = farmer.isActive();
	        this.authorities = farmer.getRole();
	        this.id=farmer.getFid();
	        
	    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(authorities));
    }

    @Override
    public String getPassword() {
        return  password;
    }
    
    
    public int getId() {
    	return id;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}