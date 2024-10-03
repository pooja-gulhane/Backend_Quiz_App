package Ideas.QuizApp.quiz_data.controllerTest;

import Ideas.QuizApp.quiz_data.dto.applicationUser.ApplicationUserRegisterDTO;
import Ideas.QuizApp.quiz_data.dto.applicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.QuizDataApplication;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.services.ApplicationUserDetailsService;
import Ideas.QuizApp.quiz_data.services.ApplicationUserService;
import Ideas.QuizApp.quiz_data.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = QuizDataApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class ApplicationUserControllerTest {
    @MockBean
    ApplicationUserDetailsService applicationUserDetailsService;
    @MockBean
    ApplicationUserService applicationUserService;
    @MockBean
    JwtUtil jwtUtil;

    String header;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@gmail.com");

        when(applicationUserDetailsService.loadUserByUsername("test@gmail.com")).thenReturn(userDetails);

        when(jwtUtil.generateToken(userDetails)).thenReturn("mockJwtToken");
        header = "Bearer mockJwtToken";
    }


    @WithMockUser(username = "test@gmail.com", roles = {"STUDENT"})
    @Test
    public void testGetUserById() throws Exception {
        ApplicationUserRegisterDTO mockUserDTO = buildApplicationUserRegisterDTO();

        when(applicationUserService.getUserById(1)).thenReturn(mockUserDTO);

        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicationUserId").value(1))
                .andExpect(jsonPath("$.applicationUserFirstName").value("test"))
                .andExpect(jsonPath("$.applicationUserLastName").value("user"))
                .andExpect(jsonPath("$.applicationUserEmail").value("test@gmail.com"))
                .andExpect(jsonPath("$.applicationUserRole").value("STUDENT"));
    }

    @WithMockUser(username = "test@gmail.com", roles = {"ADMIN"})
    @Test
    void testRegisterStudent() throws Exception {
        ApplicationUser user = buildUserDTO();
        when(applicationUserService.registerUser(user)).thenReturn(user);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated());
    }


    @WithMockUser(username = "test@gmail.com", roles = {"STUDENT"})
    @Test
    public void testLoginStudent() throws Exception {
        ApplicationUserRegisterDTO mockLoginDTO = buildApplicationUserRegisterDTO();
        ApplicationUser mockApplicationUser = buildUserDTO();

        when(applicationUserService.loginUser(any(ApplicationUserRegisterDTO.class))).thenReturn(mockApplicationUser);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockLoginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicationUserId").value(1))
                .andExpect(jsonPath("$.applicationUserFirstName").value("test"))
                .andExpect(jsonPath("$.applicationUserLastName").value("user"))
                .andExpect(jsonPath("$.applicationUserEmail").value("test@gmail.com"))
                .andExpect(jsonPath("$.applicationUserPassword").value("Test@12345"))
                .andExpect(jsonPath("$.applicationUserrole").value("STUDENT"));
    }

    @WithMockUser(username = "test@gmail.com", roles = {"STUDENT"})
    @Test
    void shouldGetCurrentUser() throws Exception {
        String authorizationHeader = "Bearer mockJwtToken";
        UserDTO userDTO = buildUserProjection();

        when(applicationUserService.getCurrentUser(authorizationHeader)).thenReturn(userDTO);

        mockMvc.perform(get("/users")
                        .header("Authorization", header)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicationUserId").value(1))
                .andExpect(jsonPath("$.applicationUserEmail").value("test@gmail.com"))
                .andExpect(jsonPath("$.applicationUserPassword").value("Test@12345"))
                .andExpect(jsonPath("$.applicationUserrole").value("STUDENT"));
    }

    @WithMockUser(username = "test@gmail.com", roles = {"STUDENT"})
    @Test
    public void shouldUpdateUser() throws Exception {
        ApplicationUser user = buildUserDTO();

        ApplicationUserRegisterDTO applicationUserRegisterDTO = buildApplicationUserRegisterDTO();
        when(applicationUserService.updateUser(any(ApplicationUser.class))).thenReturn(applicationUserRegisterDTO);

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", header)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicationUserId").value(1))
                .andExpect(jsonPath("$.applicationUserEmail").value("test@gmail.com"))
                .andExpect(jsonPath("$.applicationUserPassword").value("Test@12345"))
                .andExpect(jsonPath("$.applicationUserRole").value("STUDENT"));
    }

    private ApplicationUserRegisterDTO buildApplicationUserRegisterDTO() {
        return ApplicationUserRegisterDTO.builder()
                .applicationUserId(1)
                .applicationUserFirstName("test")
                .applicationUserLastName("user")
                .applicationUserEmail("test@gmail.com")
                .applicationUserPassword("Test@12345")
                .applicationUserRole("STUDENT")
                .build();
    }

    private UserDTO buildUserProjection() {
        return new UserDTO() {
            @Override
            public Integer getApplicationUserId() {
                return 1;
            }

            @Override
            public String getApplicationUserFirstName() {
                return "test";
            }

            @Override
            public String getApplicationUserLastName() {
                return "user";
            }

            @Override
            public String getApplicationUserEmail() {
                return "test@gmail.com";
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


private ApplicationUser buildUserDTO() {
    return ApplicationUser.builder()
            .applicationUserId(1)
            .applicationUserFirstName("test")
            .applicationUserLastName("user")
            .applicationUserEmail("test@gmail.com")
            .applicationUserPassword("Test@12345")
            .applicationUserrole("STUDENT")
            .build();
}

}

