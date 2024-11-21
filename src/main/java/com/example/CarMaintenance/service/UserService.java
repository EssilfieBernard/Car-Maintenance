package com.example.CarMaintenance.service;

import com.example.CarMaintenance.exceptions.PasswordConfirmationException;
import com.example.CarMaintenance.exceptions.UsernameAlreadyExistsException;
import com.example.CarMaintenance.model.User;
import com.example.CarMaintenance.repository.UserRepository;
import com.example.CarMaintenance.request.LoginUserRequest;
import com.example.CarMaintenance.request.RegisterUserRequest;
import com.example.CarMaintenance.response.LoginResponse;
import com.example.CarMaintenance.response.RegisterUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {



    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User register(RegisterUserRequest request) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            logger.info("Username already exists: {}", request.getUsername());
            throw new UsernameAlreadyExistsException(request.getUsername());
        }


        // Validate password confirmation
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            logger.info("Password confirmation does not match for {}", request.getUsername());
            throw new PasswordConfirmationException();
        }

        // Create and save the new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword())); // Use request password

        // Save user to the repository
        return userRepository.save(user);
    }


    public LoginResponse login(LoginUserRequest request) {
        try {
            // Authenticate the user
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Fetch user details from the repository
            var optionalUser = userRepository.findByUsername(request.getUsername());

            // Check if user exists and account is verified
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // Check if authentication is successful
                if (authentication.isAuthenticated()) {
                    String token = jwtService.generateToken(request.getUsername());

                    return new LoginResponse(true, token, null);
                }
            }
        } catch (AuthenticationException e) {
            logger.warn("Failed login attempt for username: {}", request.getUsername(), e);
        }

        logger.info("Invalid login attempt for username: {}", request.getUsername());
        return new LoginResponse(false, null, "Invalid username or password");
    }




}
