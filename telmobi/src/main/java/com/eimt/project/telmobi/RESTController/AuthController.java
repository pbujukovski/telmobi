package com.eimt.project.telmobi.RESTController;

import com.eimt.project.telmobi.dto.ApiResponse;
import com.eimt.project.telmobi.dto.JwtAuthenticationResponse;
import com.eimt.project.telmobi.dto.LoginRequest;
import com.eimt.project.telmobi.dto.SignUpRequest;
import com.eimt.project.telmobi.model.Role;
import com.eimt.project.telmobi.model.RoleName;
import com.eimt.project.telmobi.model.User;
import com.eimt.project.telmobi.model.exception.AppException;
import com.eimt.project.telmobi.repository.RoleRepository;
import com.eimt.project.telmobi.repository.UserRepository;
import com.eimt.project.telmobi.security.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtTokenUtils jwtTokenUtils;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtils.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "A user with that email already exists!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(
                () -> new AppException(("User role not set"))
        );
        user.setRoles(Collections.singleton(userRole));
        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/user/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
