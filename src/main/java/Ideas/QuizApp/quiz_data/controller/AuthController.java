package Ideas.QuizApp.quiz_data.controller;

import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.ApplicationUserLoginDTO;
import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.ApplicationUserRegisterDTO;
import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.DTO.AuthenticationResponse;
import Ideas.QuizApp.quiz_data.repository.ApplicationUserRepository;
import Ideas.QuizApp.quiz_data.services.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import Ideas.QuizApp.quiz_data.util.JwtUtil;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    ApplicationUserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody ApplicationUserLoginDTO loginRequest) throws AuthenticationException {

        System.out.println(loginRequest.getUserEmail());
        System.out.println(loginRequest.getUserPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserEmail(), loginRequest.getUserPassword())
        );

        final UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(loginRequest.getUserEmail());
        UserDTO userDTO = userRepository.findByApplicationUserEmail(loginRequest.getUserEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(userDTO, jwt));

    }
}