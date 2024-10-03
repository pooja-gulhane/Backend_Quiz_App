package Ideas.QuizApp.quiz_data.controller;

import Ideas.QuizApp.quiz_data.dto.applicationUser.ApplicationUserRegisterDTO;
import Ideas.QuizApp.quiz_data.dto.applicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.services.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/users") // Base path for all user-related endpoints
public class ApplicationUserController {

    @Autowired
    private ApplicationUserService applicationUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationUserRegisterDTO> getUserById(@PathVariable("id") int applicationUserId) {
        ApplicationUserRegisterDTO userDTO = applicationUserService.getUserById(applicationUserId);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<ApplicationUser> registerStudent(@RequestBody ApplicationUser applicationUser) {
        ApplicationUser registeredUser = applicationUserService.registerUser(applicationUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<ApplicationUser> loginStudent(@RequestBody ApplicationUserRegisterDTO applicationUser) {
        ApplicationUser loggedInUser = applicationUserService.loginUser(applicationUser);
        return ResponseEntity.ok(loggedInUser);
    }

    @PutMapping
    public ResponseEntity<ApplicationUserRegisterDTO> updateApplicationUser(@RequestBody ApplicationUser applicationUser) {
        ApplicationUserRegisterDTO updatedUser = applicationUserService.updateUser(applicationUser);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping
    public ResponseEntity<UserDTO> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        UserDTO currentUser = applicationUserService.getCurrentUser(authorizationHeader);
        return ResponseEntity.ok(currentUser);
    }
}


