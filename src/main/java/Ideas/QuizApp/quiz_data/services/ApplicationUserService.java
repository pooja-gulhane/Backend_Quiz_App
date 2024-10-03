package Ideas.QuizApp.quiz_data.services;

import Ideas.QuizApp.quiz_data.dto.applicationUser.ApplicationUserRegisterDTO;
import Ideas.QuizApp.quiz_data.dto.applicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.exception.EmailAlreadyRegisteredException;
import Ideas.QuizApp.quiz_data.exception.InvalidCredentialsException;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.repository.ApplicationUserRepository;
import Ideas.QuizApp.quiz_data.roles.Roles;
import Ideas.QuizApp.quiz_data.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationUserService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public ApplicationUser registerUser(ApplicationUser applicationUser) {
        if (applicationUserRepository.existsByApplicationUserEmail(applicationUser.getApplicationUserEmail())) {
            throw new EmailAlreadyRegisteredException();
        }

        applicationUser.setApplicationUserPassword(passwordEncoder.encode(applicationUser.getApplicationUserPassword()));

        if (applicationUser.getApplicationUserrole() == null || applicationUser.getApplicationUserrole().isEmpty()) {
            applicationUser.setApplicationUserrole(Roles.ROLE_STUDENT);
        }

        return applicationUserRepository.save(applicationUser);
    }

    public ApplicationUser loginUser(ApplicationUserRegisterDTO applicationUser) {
        Optional<ApplicationUser> existingUser = applicationUserRepository
                .findByApplicationUserEmailAndApplicationUserPassword(
                        applicationUser.getApplicationUserEmail(),
                        applicationUser.getApplicationUserPassword());

        if (existingUser.isPresent()) {
            return existingUser.get();
        } else {
            throw new InvalidCredentialsException();
        }
    }

    public ApplicationUserRegisterDTO updateUser(ApplicationUser applicationUser) {

        Optional<ApplicationUser> existingUserOptional = applicationUserRepository.findById(applicationUser.getApplicationUserId());

        if (existingUserOptional.isPresent()) {
            ApplicationUser existingUser = existingUserOptional.get();
            existingUser.setApplicationUserFirstName(applicationUser.getApplicationUserFirstName());
            existingUser.setApplicationUserLastName(applicationUser.getApplicationUserLastName());
            if(!applicationUser.getApplicationUserPassword().isEmpty()) {
                existingUser.setApplicationUserPassword(passwordEncoder.encode(applicationUser.getApplicationUserPassword()));
            }
            return toApplicationDTO(applicationUserRepository.save(existingUser));
        }

        throw new ResourceNotFound("User Id not Found");
    }

    public ApplicationUserRegisterDTO getUserById(int applicationUserId) {
        Optional<ApplicationUser> userOptional = applicationUserRepository.findById(applicationUserId);
        if (userOptional.isPresent()) {
            return toApplicationDTO(userOptional.get());
        } else {
            throw new ResourceNotFound("User with ID " + applicationUserId + " not found");
        }
    }



    public UserDTO getCurrentUser(String authorizationHeader) {
        String jwt = authorizationHeader.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        System.out.println(username);
        return applicationUserRepository.findByApplicationUserEmail(username);
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
}
