package microService.jwtManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import microService.jwtManagement.filter.JwtRequestFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
	@Autowired
	private PasswordEncoder getPasswordEncoder;
	
	 @Autowired
	 private   UserDetailsService userDetailsService;
	 
	 @Autowired
		private JwtRequestFilter jwtRequestFilter;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Set your configuration on the auth object
		auth.userDetailsService(userDetailsService);
        auth.inMemoryAuthentication()
      .withUser("admin")
      .password(getPasswordEncoder.encode("admin"))
      .roles("ADMIN");
    }

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http
         .cors()
         .and()
         .authorizeRequests()
//         		 .anyRequest().fullyAuthenticated()
//         .antMatchers("/farmer/getById/{id}").hasAnyRole("ADMIN", "USER")
//         		.antMatchers("/farmer/**").hasRole("ADMIN")
         .antMatchers("/jwtsecurity/anyRole/**").hasAnyRole("ADMIN","FARMER", "DEALER")
         .antMatchers("/jwtsecurity/findByIdOfDealer/{did}").hasAnyRole("ADMIN", "DEALER")
         .antMatchers("/jwtsecurity/findByIdOfFarmer/{fid}").hasAnyRole("ADMIN","FARMER")
         .antMatchers("/jwtsecurity/authenticate").permitAll()
         .antMatchers("/jwtsecurity/createFarmer").permitAll()
         .antMatchers("/jwtsecurity/createDealer").permitAll()
         .antMatchers("/jwtsecurity/farmer/**").hasRole("FARMER")
         .antMatchers("/jwtsecurity/dealer/**").hasRole("DEALER")
         .antMatchers("/jwtsecurity/admin/**").hasRole("ADMIN")
 				.anyRequest().authenticated().and().
 				exceptionHandling().and().sessionManagement()
//                 .and().formLogin()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         
         http.csrf().disable();
         http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
     
    }
    
    
}