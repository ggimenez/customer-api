package com.gabri.coding.customerapi.controller;

import com.gabri.coding.customerapi.dto.AuthenticationRequest;
import com.gabri.coding.customerapi.dto.AuthenticationResponse;
import com.gabri.coding.customerapi.dto.UserDTO;
import com.gabri.coding.customerapi.service.UserService;
import com.gabri.coding.customerapi.security.CustomAuthenticationManager;
import com.gabri.coding.customerapi.service.security.JwtTokenService;
import com.gabri.coding.customerapi.service.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RequestMapping("/api/")
@RestController
public class AuthenticationController {

    // ...
    UserService userService;

    JwtUserDetailsService jwtUserDetailsService;
    JwtTokenService jwtTokenService;
    CustomAuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(JwtUserDetailsService jwtUserDetailsService,
                                    JwtTokenService jwtTokenService,
                                    CustomAuthenticationManager authenticationManager,
                                    UserService userService){
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public AuthenticationResponse authenticate(@RequestBody final AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getLogin(), authenticationRequest.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getLogin());
        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(jwtTokenService.generateToken(userDetails));
        return authenticationResponse;
    }

    @PostMapping("/auth/user")
    public ResponseEntity<Object> saveUser(@RequestBody UserDTO userDTO) {
        UserDTO userCreated = userService.createUser(userDTO);
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }

    @GetMapping("/auth/user")
    public ResponseEntity<Object> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/auth/user/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {

        UserDTO userCreated = userService.findUser(id);
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }

    /*@PutMapping("/auth/user")
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO userDTO, @RequestParam Long id) {

        UserDTO userCreated = userService.updateUser(userDTO, id);
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }*/



}

