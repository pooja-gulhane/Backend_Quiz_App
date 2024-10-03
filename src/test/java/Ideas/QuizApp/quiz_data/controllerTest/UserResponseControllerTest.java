package Ideas.QuizApp.quiz_data.controllerTest;

import Ideas.QuizApp.quiz_data.dto.applicationUser.ApplicationUserRegisterDTO;
import Ideas.QuizApp.quiz_data.dto.applicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.dto.question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.dto.question.QuestionDTO;
import Ideas.QuizApp.quiz_data.dto.quiz.QuizDTO;
import Ideas.QuizApp.quiz_data.dto.quiz.QuizProjection;
import Ideas.QuizApp.quiz_data.dto.userResponse.*;
import Ideas.QuizApp.quiz_data.dto.quiztaken.ScoreDTO;
import Ideas.QuizApp.quiz_data.controller.UserResponseController;
import Ideas.QuizApp.quiz_data.filter.JwtRequestFilter;
import Ideas.QuizApp.quiz_data.services.UserResponseService;
import Ideas.QuizApp.quiz_data.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserResponseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserResponseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserResponseService userResponseService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private CurrentAndNextResponseDTO currentAndNextResponseDTO;
    private SubmitUserResponseDTO currentResponseDTO;
    private SubmitUserResponseDTO nextResponseDTO;
    private CurrentAndPreviousResponseDTO currentAndPreviousResponseDTO;

    @BeforeEach
    public void setUp() {
        currentResponseDTO = SubmitUserResponseDTO.builder()
                .userResponseAns("A")
                .quiz(QuizDTO.builder().quizId(1).build())
                .question(QuestionDTO.builder().questionId(1).build())
                .applicationUser(ApplicationUserRegisterDTO.builder().applicationUserId(1).build())
                .build();

        nextResponseDTO = SubmitUserResponseDTO.builder()
                .userResponseAns("B")
                .quiz(QuizDTO.builder().quizId(1).build())
                .question(QuestionDTO.builder().questionId(2).build())
                .applicationUser(ApplicationUserRegisterDTO.builder().applicationUserId(1).build())
                .build();

        currentAndNextResponseDTO = CurrentAndNextResponseDTO.builder()
                .currentResponse(currentResponseDTO)
                .nextResponse(nextResponseDTO)
                .build();

        currentAndPreviousResponseDTO = CurrentAndPreviousResponseDTO.builder()
                .currentResponse(currentResponseDTO)
                .previousResponse(nextResponseDTO)
                .build();
    }

    @WithMockUser(username = "user@gmail.com", roles = "{STUDENT}")
    @Test
    void shouldSaveCurrentResponseAndGetPrevious() throws Exception {
        CurrentResponseDTO previousResponse = mockPreviousResponseDTO();

        when(userResponseService.getPreviousResponseIfPresent(any(SubmitUserResponseDTO.class))).thenReturn(previousResponse);

        mockMvc.perform(post("/quiz/previous")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentAndPreviousResponseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userResponseAns").value("A"))
                .andExpect(jsonPath("$.question.questionId").value(1));
    }

    @WithMockUser(username = "user@gmail.com", roles = "{STUDENT}")
    @Test
    void shouldSaveCurrentResponseAndGetNext() throws Exception {
        CurrentResponseDTO nextResponse = mockNextResponseDTO();

        when(userResponseService.getNextResponseIfPresent(any(SubmitUserResponseDTO.class))).thenReturn(nextResponse);

        mockMvc.perform(post("/quiz/next")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentAndNextResponseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userResponseAns").value("B"))
                .andExpect(jsonPath("$.question.questionId").value(2));
    }


    @WithMockUser(username = "user@gmail.com", roles = "{STUDENT}")
    @Test
    void shouldSubmitUserResponsesAndReturnScore() throws Exception {
        ScoreDTO scoreDTO = new ScoreDTO(10);

        when(userResponseService.submitUserResponses(any(CurrentAndNextResponseDTO.class))).thenReturn(scoreDTO);

        mockMvc.perform(post("/quiz/userResponses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currentAndNextResponseDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.scoreValue").value(10));
    }

    @WithMockUser(username = "user@gmail.com", roles = "{STUDENT}")
    @Test
    void shouldGetUserResponsesByQuizAndUser() throws Exception {
        List<DisplayUserResponseDTO> mockResponses = buildMockUserResponseDTOList();

        // Mock the service layer behavior
        when(userResponseService.getUserResponsesByQuizAndUser(1, 1)).thenReturn(mockResponses);

        // Perform GET request
        mockMvc.perform(get("/quiz/1/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userResponseAns").value("A"))
                .andExpect(jsonPath("$[0].isCorrect").value(true))
                .andExpect(jsonPath("$[0].question.questionDescription").value("Sample Question"));
    }



    private CurrentResponseDTO mockPreviousResponseDTO() {
        return new CurrentResponseDTO() {
            @Override
            public Integer getUserResponseId() {
                return 1;
            }

            @Override
            public String getUserResponseAns() {
                return "A";
            }

            @Override
            public DisplayQuestionDTO getQuestion() {
                return buildPreviousDisplayQuestionDTO();
            }

            @Override
            public UserDTO getApplicationUser() {
                return buildUserDTO();
            }

            @Override
            public QuizProjection getQuiz() {
                return buildQuizProjection();
            }
        };
    }

    private DisplayQuestionDTO buildPreviousDisplayQuestionDTO() {
        return new DisplayQuestionDTO() {
            @Override
            public Integer getQuestionId() {
                return 1;
            }

            @Override
            public String getQuestionDescription() {
                return "Previous Sample Question";
            }

            @Override
            public String getQuestionOption1() {
                return "Previous Option 1";
            }

            @Override
            public String getQuestionOption2() {
                return "Previous Option 2";
            }

            @Override
            public String getQuestionOption3() {
                return "Previous Option 3";
            }

            @Override
            public String getQuestionOption4() {
                return "Previous Option 4";
            }
        };
    }




    private CurrentResponseDTO mockNextResponseDTO() {
        return new CurrentResponseDTO() {
            @Override
            public Integer getUserResponseId() {
                return 2;
            }

            @Override
            public String getUserResponseAns() {
                return "B";
            }

            @Override
            public DisplayQuestionDTO getQuestion() {
                return buildDisplayQuestionDTO();
            }

            @Override
            public UserDTO getApplicationUser() {
                return buildUserDTO();
            }

            @Override
            public QuizProjection getQuiz() {
                return buildQuizProjection();
            }
        };
    }

    private DisplayQuestionDTO buildDisplayQuestionDTO()
    {
        return new DisplayQuestionDTO() {
            @Override
            public Integer getQuestionId() {
                return 2;
            }

            @Override
            public String getQuestionDescription() {
                return "";
            }

            @Override
            public String getQuestionOption1() {
                return "";
            }

            @Override
            public String getQuestionOption2() {
                return "";
            }

            @Override
            public String getQuestionOption3() {
                return "";
            }

            @Override
            public String getQuestionOption4() {
                return "";
            }
        };
    }

    private UserDTO buildUserDTO()
    {
        return new UserDTO() {
            @Override
            public Integer getApplicationUserId() {
                return 0;
            }

            @Override
            public String getApplicationUserFirstName() {
                return "";
            }

            @Override
            public String getApplicationUserLastName() {
                return "";
            }

            @Override
            public String getApplicationUserEmail() {
                return "";
            }

            @Override
            public String getApplicationUserPassword() {
                return "";
            }

            @Override
            public String getApplicationUserrole() {
                return "";
            }
        };
    }

    private QuizProjection buildQuizProjection() {
        return new QuizProjection() {
            @Override
            public Integer getQuizId() {
                return 0;
            }

            @Override
            public Integer getQuizNoOfQuestions() {
                return 0;
            }

            @Override
            public Integer getQuizTotalMarks() {
                return 0;
            }

            @Override
            public Integer getQuizTimeAllocated() {
                return 0;
            }

            @Override
            public String getQuizName() {
                return "";
            }

            @Override
            public String getQuizImage() {
                return "";
            }
        };
    }

    private List<DisplayUserResponseDTO> buildMockUserResponseDTOList() {
        List<DisplayUserResponseDTO> userResponses = new ArrayList<>();
        userResponses.add(new DisplayUserResponseDTO() {
            @Override
            public String getUserResponseAns() {
                return "A";
            }

            @Override
            public Boolean getIsCorrect() {
                return true;
            }

            @Override
            public QuestionDTO getQuestion() {
                return new QuestionDTO() {
                    @Override
                    public String getQuestionDescription() {
                        return "Sample Question";
                    }

                    @Override
                    public String getQuestionOption1() {
                        return "Option 1";
                    }

                    @Override
                    public String getQuestionOption2() {
                        return "Option 2";
                    }

                    @Override
                    public String getQuestionOption3() {
                        return "Option 3";
                    }

                    @Override
                    public String getQuestionOption4() {
                        return "Option 4";
                    }

                    @Override
                    public String getQuestionCorrectAns() {
                        return "A";
                    }
                };
            }
        });
        return userResponses;
    }
}
