package be_course.be_online_course.modules.auth;

import be_course.be_online_course.DTO.*;
import be_course.be_online_course.exception.UserExceptions;
import be_course.be_online_course.modules.role.RoleRepository;
import be_course.be_online_course.modules.role.Roles;
import be_course.be_online_course.modules.user.User;
import be_course.be_online_course.modules.user.UserRepository;
import be_course.be_online_course.utils.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.security.Principal;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(userDetails.getUsername());

        AuthResponse authResponse = new AuthResponse(token, userDetails.getUsername());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPasswordUser(@RequestBody ResetPasswordDTO resetPasswordDTO, Principal principal){

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UserExceptions.UserNotFoundException("User not found"));

        // Checking old password is invalid
        if (!passwordEncoder.matches(resetPasswordDTO.getOldPassword(), user.getPassword())) {
            throw new UserExceptions.CheckValidPassword("Old password does not match");
        }

        // Save new password
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.success("Password reset successfully"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // Add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            throw new UserExceptions.UserOrEmailAlreadyExistsException("User already exists");
        }
        // Add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            throw new UserExceptions.UserOrEmailAlreadyExistsException("Email already exists");
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Roles role = roleRepository.findByName("ROLE_ADMIN").get();  // Assuming Role is of type Roles
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.success("User registered successfully!"));
    }
}