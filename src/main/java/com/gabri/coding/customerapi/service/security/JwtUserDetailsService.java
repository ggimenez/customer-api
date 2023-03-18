package com.gabri.coding.customerapi.service.security;

import com.gabri.coding.customerapi.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    public static final String USER = "USER";
    public static final String ROLE_USER = "ROLE_" + USER;

    UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {

        Optional<com.gabri.coding.customerapi.model.security.User> userFromDBOptional =
                userRepository.findByUserName(username);

        if(userFromDBOptional.isPresent()){
            com.gabri.coding.customerapi.model.security.User userFromDB = userFromDBOptional.get();
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
            userFromDB.getRoles().forEach(
                    role -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));

            return new User(userFromDB.getUserName(), userFromDB.getPassword(), simpleGrantedAuthorities);
        }else {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

    }

}
