package Ideas.QuizApp.quiz_data.controllerTest;

import Ideas.QuizApp.quiz_data.dto.quiz.QuizProjection;
import Ideas.QuizApp.quiz_data.dto.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.controller.QuizTakenController;
import Ideas.QuizApp.quiz_data.services.ApplicationUserDetailsService;
import Ideas.QuizApp.quiz_data.services.QuizTakenService;
import Ideas.QuizApp.quiz_data.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizTakenController.class)
@AutoConfigureMockMvc(addFilters = false)
public class QuizTakenControllerTest {

    @MockBean
    QuizTakenService quizTakenService;

    @MockBean
    ApplicationUserDetailsService applicationUserDetailsService;

    @MockBean
    JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    String header;

    @WithMockUser(username = "abc@gmail.com", roles = "{STUDENT}")
    @Test
    void shouldGetUserQuizHistory() throws Exception {
        UserQuizDetailsDTO mockUserQuizDetailsDTO = buildUserQuizDetailsDTO();
        List<UserQuizDetailsDTO> userQuizDetailsDTOList = new ArrayList<>();
        userQuizDetailsDTOList.add(mockUserQuizDetailsDTO);

        when(quizTakenService.getUserQuizHistory(1)).thenReturn(userQuizDetailsDTOList);

        mockMvc.perform(get("/quiztaken/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].scoreValue").value(10))
                .andExpect(jsonPath("$[0].quizTakenDate").isNotEmpty())
                .andExpect(jsonPath("$[0].quiz.quizName").value("Sample Quiz"))
                .andExpect(jsonPath("$[0].applicationUser.applicationUserFirstName").value("John"))
                .andExpect(jsonPath("$[0].applicationUser.applicationUserLastName").value("Doe"));
    }

    private UserQuizDetailsDTO buildUserQuizDetailsDTO() {
        return new UserQuizDetailsDTO() {
            @Override
            public Integer getScoreValue() {
                return 10;
            }

            @Override
            public LocalDateTime getQuizTakenDate() {
                return LocalDateTime.now();
            }

            @Override
            public QuizProjection getQuiz() {
                return new QuizProjection() {
                    @Override
                    public Integer getQuizId() {
                        return 1;
                    }

                    @Override
                    public String getQuizName() {
                        return "Sample Quiz";
                    }

                    @Override
                    public Integer getQuizNoOfQuestions() {
                        return 10;
                    }

                    @Override
                    public Integer getQuizTotalMarks() {
                        return 10;
                    }

                    @Override
                    public Integer getQuizTimeAllocated() {
                        return 10;
                    }

                    @Override
                    public String getQuizImage() {
                        return "image.jpg";
                    }
                };
            }

            @Override
            public UserProjection getApplicationUser() {
                return new UserProjection() {
                    @Override
                    public Integer getApplicationUserId() {
                        return 1;
                    }

                    @Override
                    public String getApplicationUserFirstName() {
                        return "John";
                    }

                    @Override
                    public String getApplicationUserLastName() {
                        return "Doe";
                    }
                };
            }
        };
    }
}

