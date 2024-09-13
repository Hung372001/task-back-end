package com.backend.task.controller;

import com.backend.task.dto.ApiResponse;
import com.backend.task.dto.AuthenticationRequest;
import com.backend.task.dto.AuthenticationResponse;
import com.backend.task.service.Impl.UserServiceImpl;

import com.backend.task.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        System.out.println("Login attempt for username: " + authenticationRequest.getUsername());

        try {
            System.out.println("Attempting to authenticate user: " + authenticationRequest.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            System.out.println("Authentication successful for username: " + authenticationRequest.getUsername());
        } catch (BadCredentialsException e) {
            System.out.println("Authentication failed for username: " + authenticationRequest.getUsername() + ". Error: " + e.getMessage());
            return ResponseEntity.status(401).body(new ApiResponse("Incorrect username or password", 401));
        } catch (Exception e) {
            System.out.println("Unexpected error during authentication: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse("An unexpected error occurred", 500));
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        System.out.println("User details loaded for username: " + authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);
        System.out.println("JWT token generated for username: " + authenticationRequest.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

//    @GetMapping("/profile")
//    public ResponseEntity<?> getEmployeeProfile(Authentication authentication) {
//        if (authentication != null && authentication.isAuthenticated()) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            String username = userDetails.getUsername();
//            EmployeeDto employeeDto = employeeServices.findEmployeeByUserName(username);
//            return ResponseEntity.ok(employeeDto);
//
//        }else {
//            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(new ApiResponse("User not authenticated", 401));
//        }
//    }

}
