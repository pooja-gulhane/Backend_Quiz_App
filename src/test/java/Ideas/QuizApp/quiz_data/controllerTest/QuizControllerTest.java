package Ideas.QuizApp.quiz_data.controllerTest;

import Ideas.QuizApp.quiz_data.dto.quiz.QuizDTO;
import Ideas.QuizApp.quiz_data.controller.QuizController;
import Ideas.QuizApp.quiz_data.dto.quiz.QuizProjection;
import Ideas.QuizApp.quiz_data.dto.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.services.QuizService;
import Ideas.QuizApp.quiz_data.services.ApplicationUserDetailsService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(QuizController.class)
@AutoConfigureMockMvc(addFilters = false)
public class QuizControllerTest {

    @MockBean
    private QuizService quizService;

    @MockBean
    private ApplicationUserDetailsService applicationUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    private String header;

    @WithMockUser(username = "test@gmail.com", roles = "{ADMIN}")
    @Test
    void shouldCreateQuiz() throws Exception {
        QuizDTO quizDTO = buildQuizDTO();

        when(quizService.createQuiz(any(QuizDTO.class))).thenReturn(quizDTO);

        mockMvc.perform(post("/quiz/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"quizName\": \"Sample Quiz\", \"quizNoOfQuestions\": 10, \"quizTotalMarks\": 100, \"quizTimeAllocated\": 60 }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quizName").value("Sample Quiz"));
    }

    @WithMockUser(username = "test@gmail.com", roles = "{STUDENT}")
    @Test
    void shouldGetQuizById() throws Exception {
        QuizDTO quizDTO = buildQuizDTO();

        when(quizService.getQuizById(1)).thenReturn(quizDTO);

        mockMvc.perform(get("/quiz/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quizName").value("Sample Quiz"))
                .andExpect(jsonPath("$.quizNoOfQuestions").value(10));
    }

    @WithMockUser(username = "test@gmail.com", roles = "{STUDENT}")
    @Test
    void shouldGetQuizzesNotTakenByUser() throws Exception {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        quizDTOList.add(buildQuizDTO());

        when(quizService.getQuizzesNotTakenByUser(1)).thenReturn(quizDTOList);

        mockMvc.perform(get("/quiz/not-taken/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quizName").value("Sample Quiz"));
    }


    @WithMockUser(username = "test@gmail.com", roles = "{STUDENT}")
    @Test
    void shouldGetQuizByUser() throws Exception {
        List<UserQuizDetailsDTO> userQuizDetailsList = new ArrayList<>();
        userQuizDetailsList.add(buildUserQuizDetailsDTO());

        when(quizService.getQuizzesByUser(1)).thenReturn(userQuizDetailsList);

        mockMvc.perform(get("/quiz/user/1/quiz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quiz.quizName").value("Sample Quiz"))
                .andExpect(jsonPath("$[0].scoreValue").value(85));
    }



    private QuizDTO buildQuizDTO() {
        return new QuizDTO(1, 10, 100, 60, "Sample Quiz", null);
    }

    private UserQuizDetailsDTO buildUserQuizDetailsDTO() {
        return new UserQuizDetailsDTO() {
            @Override
            public Integer getScoreValue() {
                return 85;
            }

            @Override
            public LocalDateTime getQuizTakenDate() {
                return LocalDateTime.now();
            }

            @Override
            public QuizProjection getQuiz() {
                return buildQuizProjection();
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

    private QuizProjection buildQuizProjection() {
        return new QuizProjection() {
            @Override
            public Integer getQuizId() {
                return 1;
            }

            @Override
            public Integer getQuizNoOfQuestions() {
                return 10;
            }

            @Override
            public Integer getQuizTotalMarks() {
                return 100;
            }

            @Override
            public Integer getQuizTimeAllocated() {
                return 60;
            }

            @Override
            public String getQuizName() {
                return "Sample Quiz";
            }

            @Override
            public String getQuizImage() {
                return "quiz-image.png";
            }
        };
    }


}

