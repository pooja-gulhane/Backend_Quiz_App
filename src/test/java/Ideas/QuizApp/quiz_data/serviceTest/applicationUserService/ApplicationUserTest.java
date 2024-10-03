package Ideas.QuizApp.quiz_data.serviceTest.applicationUserService;

import Ideas.QuizApp.quiz_data.dto.applicationUser.ApplicationUserRegisterDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.exception.EmailAlreadyRegisteredException;
import Ideas.QuizApp.quiz_data.exception.InvalidCredentialsException;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.repository.ApplicationUserRepository;
import Ideas.QuizApp.quiz_data.roles.Roles;
import Ideas.QuizApp.quiz_data.services.ApplicationUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ApplicationUserTest {

    @InjectMocks
    private ApplicationUserService applicationUserService;

    @Mock
    private ApplicationUserRepository applicationUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private ApplicationUser testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = buildTestUser();
    }

    private ApplicationUser buildTestUser() {
        return ApplicationUser.builder()
                .applicationUserId(1)
                .applicationUserFirstName("John")
                .applicationUserLastName("Doe")
                .applicationUserEmail("john.doe@example.com")
                .applicationUserPassword("password")
                .applicationUserrole(Roles.ROLE_STUDENT)
                .build();
    }

    @Test
    public void testRegisterUser_Success() {
        // Arrange
        when(applicationUserRepository.existsByApplicationUserEmail(testUser.getApplicationUserEmail())).thenReturn(false);
        when(passwordEncoder.encode(testUser.getApplicationUserPassword())).thenReturn("encodedPassword");
        when(applicationUserRepository.save(any(ApplicationUser.class))).thenReturn(testUser);

        // Act
        ApplicationUser registeredUser = applicationUserService.registerUser(testUser);

        // Assert
        assertEquals(testUser.getApplicationUserEmail(), registeredUser.getApplicationUserEmail());
        verify(applicationUserRepository).save(testUser);
    }

    @Test
    public void testRegisterUser_EmailAlreadyExists() {
        // Arrange
        when(applicationUserRepository.existsByApplicationUserEmail(testUser.getApplicationUserEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyRegisteredException.class, () -> applicationUserService.registerUser(testUser));
    }

    @Test
    public void testRegisterUser_AssignDefaultRoleIfRoleIsNull() {
        // Arrange
        testUser.setApplicationUserrole(null);
        when(applicationUserRepository.existsByApplicationUserEmail(testUser.getApplicationUserEmail())).thenReturn(false);
        when(passwordEncoder.encode(testUser.getApplicationUserPassword())).thenReturn("encodedPassword");
        when(applicationUserRepository.save(any(ApplicationUser.class))).thenReturn(testUser);

        // Act
        ApplicationUser registeredUser = applicationUserService.registerUser(testUser);

        // Assert
        assertEquals(Roles.ROLE_STUDENT, registeredUser.getApplicationUserrole());
        verify(applicationUserRepository).save(testUser);
    }

    @Test
    public void testRegisterUser_AssignDefaultRoleIfRoleIsEmpty() {
        // Arrange
        testUser.setApplicationUserrole("");
        when(applicationUserRepository.existsByApplicationUserEmail(testUser.getApplicationUserEmail())).thenReturn(false);
        when(passwordEncoder.encode(testUser.getApplicationUserPassword())).thenReturn("encodedPassword");
        when(applicationUserRepository.save(any(ApplicationUser.class))).thenReturn(testUser);

        // Act
        ApplicationUser registeredUser = applicationUserService.registerUser(testUser);

        // Assert
        assertEquals(Roles.ROLE_STUDENT, registeredUser.getApplicationUserrole());
        verify(applicationUserRepository).save(testUser);
    }

    @Test
    public void testLoginUser_Success() {
        // Arrange
        when(applicationUserRepository.findByApplicationUserEmailAndApplicationUserPassword(
                testUser.getApplicationUserEmail(), testUser.getApplicationUserPassword()))
                .thenReturn(Optional.of(testUser));

        // Act
        ApplicationUser loggedInUser = applicationUserService.loginUser(buildLoginDTO(testUser));

        // Assert
        assertEquals(testUser.getApplicationUserEmail(), loggedInUser.getApplicationUserEmail());
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
        // Arrange
        when(applicationUserRepository.findByApplicationUserEmailAndApplicationUserPassword(
                testUser.getApplicationUserEmail(), "wrongPassword"))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> applicationUserService.loginUser(buildLoginDTOWithWrongPassword(testUser)));
    }

    @Test
    public void testUpdateUser_Success() {
        // Arrange
        ApplicationUser updatedUser = buildTestUser();
        updatedUser.setApplicationUserFirstName("UpdatedFirstName");
        updatedUser.setApplicationUserLastName("UpdatedLastName");
        updatedUser.setApplicationUserPassword("newPassword");

        when(applicationUserRepository.findById(testUser.getApplicationUserId())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(applicationUserRepository.save(any(ApplicationUser.class))).thenReturn(testUser);

        // Act
        ApplicationUserRegisterDTO result = applicationUserService.updateUser(updatedUser);

        // Assert
        assertEquals("UpdatedFirstName", result.getApplicationUserFirstName());
        assertEquals("UpdatedLastName", result.getApplicationUserLastName());
        verify(passwordEncoder).encode("newPassword");
        verify(applicationUserRepository).save(any(ApplicationUser.class));
    }



    @Test
    public void testUpdateUser_UserNotFound() {
        // Arrange
        when(applicationUserRepository.findById(testUser.getApplicationUserId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFound.class, () -> applicationUserService.updateUser(testUser));
    }

    @Test
    public void testGetUserById_Success() {
        // Arrange
        when(applicationUserRepository.findById(testUser.getApplicationUserId())).thenReturn(Optional.of(testUser));

        // Act
        ApplicationUserRegisterDTO foundUser = applicationUserService.getUserById(testUser.getApplicationUserId());

        // Assert
        assertEquals(testUser.getApplicationUserEmail(), foundUser.getApplicationUserEmail());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        // Arrange
        when(applicationUserRepository.findById(testUser.getApplicationUserId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFound.class, () -> applicationUserService.getUserById(testUser.getApplicationUserId()));
    }




    private ApplicationUserRegisterDTO buildLoginDTO(ApplicationUser user) {
        return new ApplicationUserRegisterDTO(
                user.getApplicationUserId(),
                user.getApplicationUserFirstName(),
                user.getApplicationUserLastName(),
                user.getApplicationUserEmail(),
                user.getApplicationUserPassword(),
                user.getApplicationUserrole());
    }

    private ApplicationUserRegisterDTO buildLoginDTOWithWrongPassword(ApplicationUser user) {
        return new ApplicationUserRegisterDTO(
                user.getApplicationUserId(),
                user.getApplicationUserFirstName(),
                user.getApplicationUserLastName(),
                user.getApplicationUserEmail(),
                "wrongPassword",
                user.getApplicationUserrole());
    }
}
