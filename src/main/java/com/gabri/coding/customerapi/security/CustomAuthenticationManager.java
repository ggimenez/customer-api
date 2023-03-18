package com.gabri.coding.customerapi.security;


import com.gabri.coding.customerapi.model.security.Role;
import com.gabri.coding.customerapi.model.security.User;
import com.gabri.coding.customerapi.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Custom authentication manager
 */
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationManager(UserRepository userRepository
                                       ,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method that performs the authentication process
     * @param authentication Authentication data, user name, credentials etc etc
     * @return Authentication data, when the authentication process was ok
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Optional<User> user = userRepository.findByUserName(authentication.getName());
        if (user.isPresent()) {
            boolean match = passwordEncoder.matches(authentication.getCredentials().toString(), user.get().getPassword());
            if (match){
                List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
                for (Role role : user.get().getRoles()) {
                    grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
                }
                return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), grantedAuthorityList);
            }else{
                throw new BadCredentialsException("Wrong Password");
            }
        } else {
            throw new BadCredentialsException("Wrong UserName");
        }

    }
}
