package Ideas.QuizApp.quiz_data.controller;

import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.ApplicationUserRegisterDTO;
import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.services.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class ApplicationUserController {
    @Autowired
    private ApplicationUserService applicationUserService;

    @GetMapping("/users/{id}")
    public ResponseEntity<ApplicationUserRegisterDTO> getUserById(@PathVariable("id") int applicationUserId) {
        ApplicationUserRegisterDTO userDTO = applicationUserService.getUserById(applicationUserId);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/users/register")
    public ApplicationUser registerStudent(@RequestBody ApplicationUser applicationUser) {
        return applicationUserService.registerUser(applicationUser);
    }

    @PostMapping("/users/login")
    public ApplicationUser loginStudent(@RequestBody ApplicationUserRegisterDTO applicationUser) {
        return applicationUserService.loginUser(applicationUser);
    }

    @PutMapping("/users/{id}")
    public ApplicationUserRegisterDTO updateApplicationUser(@PathVariable("id") int applicationUserId, @RequestBody ApplicationUser applicationUser) {
        return applicationUserService.updateUser(applicationUserId, applicationUser);
    }

    @GetMapping("/users")
    public UserDTO getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        return applicationUserService.getCurrentUser(authorizationHeader);
    }
}

