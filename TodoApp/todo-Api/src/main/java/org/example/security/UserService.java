package org.example.security;

import org.example.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Bean
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      //  return new User("Hadeer",passwordEncoder().encode("pass"), AuthorityUtils.NO_AUTHORITIES);
        AppUser user= userRepository.findByEmail(username);
        if(user == null){
            throw new NotFoundException("user not found");
        }
        return user;
    }
    public void save(AppUser user){
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }
    public List<AppUser> findAll(){
        return userRepository.findAll();
    }
}
