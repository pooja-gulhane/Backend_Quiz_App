package Ideas.QuizApp.quiz_data.controller;

import Ideas.QuizApp.quiz_data.dto.applicationUser.ApplicationUserLoginDTO;
import Ideas.QuizApp.quiz_data.dto.AuthenticationResponse;
import Ideas.QuizApp.quiz_data.services.AuthService; // Import the new AuthService
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody ApplicationUserLoginDTO loginRequest) throws AuthenticationException {
        try {
            AuthenticationResponse authenticationResponse = authService.authenticate(loginRequest);
            return ResponseEntity.ok(authenticationResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Authentication failed");
        }
    }
}





