package microService.jwtManagement.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import microService.jwtManagement.models.UserData;

public class MyUserDetails implements UserDetails {


	 private String userName;
	    private String password;
	    private boolean active;
	    private String authorities;

	    public MyUserDetails(UserData farmer) {
	        this.userName = farmer.getUserName();
	        this.password = farmer.getPassword();
	        this.active = farmer.isActive();
	        this.authorities = farmer.getRole();
	        
	    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(authorities));
    }

    @Override
    public String getPassword() {
        return  password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}