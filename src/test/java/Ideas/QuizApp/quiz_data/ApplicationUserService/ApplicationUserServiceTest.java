package Ideas.QuizApp.quiz_data.ApplicationUserService;

import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.ApplicationUserRegisterDTO;
import Ideas.QuizApp.quiz_data.controller.ApplicationUserController;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.exception.EmailAlreadyRegisteredException;
import Ideas.QuizApp.quiz_data.exception.InvalidCredentialsException;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.repository.ApplicationUserRepository;
import Ideas.QuizApp.quiz_data.roles.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationUserControllerTest {

    @InjectMocks
    ApplicationUserController applicationUserController;

    @Mock
    ApplicationUserRepository applicationUserRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    ApplicationUser applicationUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        applicationUser = new ApplicationUser();
        applicationUser.setApplicationUserId(1);
        applicationUser.setApplicationUserFirstName("John");
        applicationUser.setApplicationUserLastName("Doe");
        applicationUser.setApplicationUserEmail("john.doe@example.com");
        applicationUser.setApplicationUserPassword("password");
        applicationUser.setApplicationUserrole(Roles.ROLE_STUDENT);
    }

    @Test
    void testGetUserById_Success() {
        when(applicationUserRepository.findById(1)).thenReturn(Optional.of(applicationUser));

        ResponseEntity<ApplicationUserRegisterDTO> response = applicationUserController.getUserById(1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John", response.getBody().getApplicationUserFirstName());
    }

    @Test
    void testGetUserById_NotFound() {
        when(applicationUserRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> applicationUserController.getUserById(1));
    }

    @Test
    void testRegisterStudent_Success() {
        when(applicationUserRepository.existsByApplicationUserEmail(applicationUser.getApplicationUserEmail())).thenReturn(false);
        when(passwordEncoder.encode(applicationUser.getApplicationUserPassword())).thenReturn("encodedPassword");
        when(applicationUserRepository.save(any(ApplicationUser.class))).thenReturn(applicationUser);

        ApplicationUser result = applicationUserController.registerStudent(applicationUser);
        assertEquals("John", result.getApplicationUserFirstName());
        assertEquals(Roles.ROLE_STUDENT, result.getApplicationUserrole());
    }

    @Test
    void testRegisterStudent_EmailAlreadyRegistered() {
        when(applicationUserRepository.existsByApplicationUserEmail(applicationUser.getApplicationUserEmail())).thenReturn(true);

        assertThrows(EmailAlreadyRegisteredException.class, () -> applicationUserController.registerStudent(applicationUser));
    }

    @Test
    void testLoginStudent_Success() {
        when(applicationUserRepository.findByApplicationUserEmailAndApplicationUserPassword(
                applicationUser.getApplicationUserEmail(), applicationUser.getApplicationUserPassword()
        )).thenReturn(Optional.of(applicationUser));

        ApplicationUser result = applicationUserController.loginStudent(new ApplicationUserRegisterDTO(
                applicationUser.getApplicationUserId(),
                applicationUser.getApplicationUserFirstName(),
                applicationUser.getApplicationUserLastName(),
                applicationUser.getApplicationUserEmail(),
                applicationUser.getApplicationUserPassword(),
                applicationUser.getApplicationUserrole()
        ));
        assertEquals("John", result.getApplicationUserFirstName());
    }

    @Test
    void testLoginStudent_InvalidCredentials() {
        when(applicationUserRepository.findByApplicationUserEmailAndApplicationUserPassword(
                applicationUser.getApplicationUserEmail(), applicationUser.getApplicationUserPassword()
        )).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> applicationUserController.loginStudent(new ApplicationUserRegisterDTO(
                applicationUser.getApplicationUserId(),
                applicationUser.getApplicationUserFirstName(),
                applicationUser.getApplicationUserLastName(),
                applicationUser.getApplicationUserEmail(),
                applicationUser.getApplicationUserPassword(),
                applicationUser.getApplicationUserrole()
        )));
    }

    @Test
    void testUpdateApplicationUser_Success() {
        when(applicationUserRepository.findById(1)).thenReturn(Optional.of(applicationUser));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedNewPassword");
        when(applicationUserRepository.save(any(ApplicationUser.class))).thenReturn(applicationUser);

        ApplicationUserRegisterDTO updatedUser = applicationUserController.updateApplicationUser(1, applicationUser);
        assertEquals("John", updatedUser.getApplicationUserFirstName());
        assertEquals("encodedNewPassword", updatedUser.getApplicationUserPassword());
        verify(applicationUserRepository).findById(1);
        verify(passwordEncoder).encode("password");
    }

    @Test
    void testUpdateApplicationUser_NotFound() {
        when(applicationUserRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFound.class, () -> applicationUserController.updateApplicationUser(1, applicationUser));
    }
}
