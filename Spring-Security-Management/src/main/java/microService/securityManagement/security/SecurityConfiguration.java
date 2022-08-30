package microService.securityManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
	@Autowired
	private PasswordEncoder getPasswordEncoder;
	
	 @Autowired
	 private   UserDetailsService userDetailsService;
	 
	 
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Set your configuration on the auth object
        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(getPasswordEncoder.encode("user"))
//                .roles("USER")
//                .and()
                .withUser("admin")
                .password(getPasswordEncoder.encode("admin"))
                .roles("ADMIN");
		auth.userDetailsService(userDetailsService);
    }

	

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .cors()
        .and()
        .authorizeRequests()
//        		 .anyRequest().fullyAuthenticated()
        .antMatchers("/security/farmer/**").hasAnyRole("ADMIN", "FARMER")
        .antMatchers("/security/dealer/**").hasAnyRole("ADMIN", "DEALER")
        .antMatchers("/security/createFarmer").permitAll()
        .antMatchers("/security/createDealer").permitAll()
        		.antMatchers("/security/**").hasRole("ADMIN")
                .and().formLogin();
                
        
        http.csrf().disable();
       
    }
    
    
}