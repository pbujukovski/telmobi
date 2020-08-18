package com.eimt.project.telmobi.RESTController;

import com.eimt.project.telmobi.dto.UserIdentityAvailability;
import com.eimt.project.telmobi.dto.UserProfile;
import com.eimt.project.telmobi.dto.UserSummary;
import com.eimt.project.telmobi.model.Role;
import com.eimt.project.telmobi.model.User;
import com.eimt.project.telmobi.model.exception.ResourceNotFoundException;
import com.eimt.project.telmobi.repository.UserRepository;
import com.eimt.project.telmobi.security.CurrentUser;
import com.eimt.project.telmobi.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger= LoggerFactory.getLogger(UserController.class);

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser){
        User u = userRepository.getOne(currentUser.getId());
        Role role= (Role) u.getRoles().toArray()[0];
        String rolename= role.getName().name();
        return new UserSummary(currentUser.getId(),currentUser.getUsername(),currentUser.getName(),rolename);
    }

    @GetMapping("/checkUsernameAvailability")
    public UserIdentityAvailability checkUsername(@RequestParam String username){
        Boolean available=!userRepository.existsByUsername(username);
        return new UserIdentityAvailability(available);
    }
    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmail(@RequestParam String email){
        Boolean available=!userRepository.existsByEmail(email);
        return new UserIdentityAvailability(available);
    }

    @GetMapping("/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username){
        User user=userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User","Username",username));
        Role role= (Role) user.getRoles().toArray()[0];
        String rolename= role.getName().name();

        return new UserProfile(
                user.getId(),user.getUsername(),user.getName(),user.getCreatedAt(),
                rolename
        );
    }

}
