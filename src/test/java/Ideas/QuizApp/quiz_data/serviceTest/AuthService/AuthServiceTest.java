package Ideas.QuizApp.quiz_data.serviceTest.AuthService;

import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.ApplicationUserLoginDTO;
import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.DTO.AuthenticationResponse;
import Ideas.QuizApp.quiz_data.repository.ApplicationUserRepository;
import Ideas.QuizApp.quiz_data.services.ApplicationUserDetailsService;
import Ideas.QuizApp.quiz_data.services.AuthService;
import Ideas.QuizApp.quiz_data.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.springframework.security.core.AuthenticationException;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private ApplicationUserRepository applicationUserRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private ApplicationUserDetailsService applicationUserDetailsService;
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private AuthService authService;

    @Test
    public void authenticate() {
        String email = "test@example.com";
        String password = "password";
        ApplicationUserLoginDTO loginRequest = new ApplicationUserLoginDTO(email, password);
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        UserDTO userProjection = Mockito.mock(UserDTO.class);
        String jwt = "jwt_token";

        when(applicationUserDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(applicationUserRepository.findByApplicationUserEmail(email)).thenReturn(userProjection);
        when(jwtUtil.generateToken(userDetails)).thenReturn(jwt);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, password));

        AuthenticationResponse response = authService.authenticate(loginRequest);
        assertNotNull(response);
    }

    @Test
    void testAuthenticateFailure() {
        // Given
        String email = "test@example.com";
        String password = "wrongPassword";
        ApplicationUserLoginDTO loginRequest = new ApplicationUserLoginDTO(email, password);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Authentication failed"));

        assertThrows(AuthenticationException.class, () -> authService.authenticate(loginRequest));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(applicationUserDetailsService, never()).loadUserByUsername(anyString());
        verify(applicationUserRepository, never()).findByApplicationUserEmail(anyString());
        verify(jwtUtil, never()).generateToken(any());
    }
}
