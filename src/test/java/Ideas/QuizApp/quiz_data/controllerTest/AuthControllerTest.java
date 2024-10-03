package Ideas.QuizApp.quiz_data.controllerTest;

import Ideas.QuizApp.quiz_data.QuizDataApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import Ideas.QuizApp.quiz_data.dto.applicationUser.ApplicationUserLoginDTO;
import Ideas.QuizApp.quiz_data.dto.AuthenticationResponse;
import Ideas.QuizApp.quiz_data.dto.applicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.services.AuthService;
import Ideas.QuizApp.quiz_data.services.ApplicationUserDetailsService;
import Ideas.QuizApp.quiz_data.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = QuizDataApplication.class)
@AutoConfigureMockMvc()
public class AuthControllerTest {

    @MockBean
    private ApplicationUserDetailsService applicationUserDetailsService;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtUtil jwtUtil;

    String header;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser@gmail.com");

        when(applicationUserDetailsService.loadUserByUsername("testuser@gmail.com")).thenReturn(userDetails);

        when(jwtUtil.generateToken(userDetails)).thenReturn("mockJwtToken");
        header = "Bearer mockJwtToken";
    }

    @WithMockUser(username = "testuser@gmail.com", roles = {"STUDENT"})
    @Test
    void shouldCreateAuthenticationToken() throws Exception {
        ApplicationUserLoginDTO loginDTO = buildLoginDTO();
        AuthenticationResponse authenticationResponse = buildAuthenticationResponse();

        when(authService.authenticate(loginDTO)).thenReturn(authenticationResponse);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", header)
                        .content(new ObjectMapper().writeValueAsString(loginDTO)))
                .andExpect(status().isOk());
    }

    private AuthenticationResponse buildAuthenticationResponse() {
        UserDTO userDTO = buildUserDTO();
        return new AuthenticationResponse(userDTO, "jwt");
    }

    private UserDTO buildUserDTO() {
        return new UserDTO() {
            @Override
            public Integer getApplicationUserId() {
                return 1;
            }

            @Override
            public String getApplicationUserFirstName() {
                return "Test";
            }

            @Override
            public String getApplicationUserLastName() {
                return "user";
            }

            @Override
            public String getApplicationUserEmail() {
                return "testuser@gmail.com";
            }

            @Override
            public String getApplicationUserPassword() {
                return "Test@12345";
            }

            @Override
            public String getApplicationUserrole() {
                return "STUDENT";
            }
        };
    }

    private ApplicationUserLoginDTO buildLoginDTO() {
        return new ApplicationUserLoginDTO() {
            @Override
            public String getUserEmail() {
                return "testuser@gmail.com";
            }

            @Override
            public String getUserPassword() {
                return "Test@12345";
            }
        };
    }

}