package microService.securityManagement.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import microService.securityManagement.models.UserData;
import microService.securityManagement.services.UsersService;

@Service
public class MyUserDetailsService implements UserDetailsService {
//	public class MyUserDetailsService{

	@Autowired
    UsersService service;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserData> user =service.findByUserNameOfUser(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        return user.map(MyUserDetails::new).get();
    }
}
