package Ideas.QuizApp.quiz_data.controller;

import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.ApplicationUserLoginDTO;
import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.ApplicationUserRegisterDTO;
import Ideas.QuizApp.quiz_data.DTO.DisplayUserHistoryDTO;
import Ideas.QuizApp.quiz_data.DTO.Quiz.QuizDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.entity.Quiz;
import Ideas.QuizApp.quiz_data.exception.EmailAlreadyRegisteredException;
import Ideas.QuizApp.quiz_data.exception.InvalidCredentialsException;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.repository.ApplicationUserRepository;
import Ideas.QuizApp.quiz_data.roles.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class ApplicationUserController {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationUserRegisterDTO> getUserById(@PathVariable("id") int applicationUserId) {
        Optional<ApplicationUser> userOptional = applicationUserRepository.findById(applicationUserId);
        if (userOptional.isPresent()) {
            ApplicationUser user = userOptional.get();
            ApplicationUserRegisterDTO userDTO = toApplicationDTO(user);
            return ResponseEntity.ok(userDTO);
        } else {
            throw new ResourceNotFound("User with ID " + applicationUserId + " not found");
        }
    }


    @PostMapping("/register")
    public ApplicationUser registerStudent(@RequestBody ApplicationUser applicationUser) {
        System.out.println("Hello World");
        if (applicationUserRepository.existsByApplicationUserEmail(applicationUser.getApplicationUserEmail())) {
            throw new EmailAlreadyRegisteredException();
        }

        applicationUser.setApplicationUserPassword(passwordEncoder.encode(applicationUser.getApplicationUserPassword()));

        if(applicationUser.getApplicationUserrole()  == null || applicationUser.getApplicationUserrole().isEmpty()) {
            applicationUser.setApplicationUserrole(Roles.ROLE_STUDENT);
        }

        return (applicationUserRepository.save(applicationUser));
    }

    @PostMapping("/login")
    public ApplicationUser loginStudent(@RequestBody ApplicationUserRegisterDTO applicationUser) {
        Optional<ApplicationUser> existingUser = applicationUserRepository.findByApplicationUserEmailAndApplicationUserPassword(applicationUser.getApplicationUserEmail(), applicationUser.getApplicationUserPassword());

        if (existingUser.isPresent()) {
            return (existingUser.get());
        } else {
            throw new InvalidCredentialsException();
        }
    }

    @PutMapping("/{id}")
    public ApplicationUserRegisterDTO updateApplicationUser(@PathVariable("id") int applicationUserId, @RequestBody ApplicationUser applicationUser) {

        applicationUser.setApplicationUserId(applicationUserId);
        applicationUser.setApplicationUserPassword(passwordEncoder.encode(applicationUser.getApplicationUserPassword()));
        Optional<ApplicationUser> existingUser = applicationUserRepository.findById(applicationUserId);
        if(existingUser.isPresent())
        {
            applicationUser.setApplicationUserrole(existingUser.get().getApplicationUserrole());
            return toApplicationDTO(applicationUserRepository.save(applicationUser));
        }
        throw new ResourceNotFound("User Id not Found");
    }

    private ApplicationUserRegisterDTO toApplicationDTO(ApplicationUser applicationUser) {
        return new ApplicationUserRegisterDTO(
                applicationUser.getApplicationUserId(),
                applicationUser.getApplicationUserFirstName(),
                applicationUser.getApplicationUserLastName(),
                applicationUser.getApplicationUserEmail(),
                applicationUser.getApplicationUserPassword(),
                applicationUser.getApplicationUserrole());
    }

//
//    @DeleteMapping("/{id}")
//    public void deleteApplicationUser(@PathVariable("id") int applicationUserId) {
//        applicationUserRepository.deleteById(applicationUserId);
//    }
}
