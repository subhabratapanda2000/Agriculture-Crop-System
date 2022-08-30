package microService.jwtManagement.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import microService.jwtManagement.models.UserData;
import microService.jwtManagement.services.UsersService;

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
        
//        return new User("foo", "foo",
//                new ArrayList<>());
    
}
