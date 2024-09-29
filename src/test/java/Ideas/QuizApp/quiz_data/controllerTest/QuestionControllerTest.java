package Ideas.QuizApp.quiz_data.controllerTest;

import Ideas.QuizApp.quiz_data.DTO.Question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.controller.QuestionController;
import Ideas.QuizApp.quiz_data.services.ApplicationUserDetailsService;
import Ideas.QuizApp.quiz_data.services.QuestionService;
import Ideas.QuizApp.quiz_data.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {
    @MockBean
    QuestionService questionService;
    @MockBean
    ApplicationUserDetailsService applicationUserDetailsService;
    @MockBean
    JwtUtil jwtUtil;

    String header;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("abc@gmail.com");

        when(applicationUserDetailsService.loadUserByUsername("abc@gmail.com")).thenReturn(userDetails);

        when(jwtUtil.generateToken(userDetails)).thenReturn("mockJwtToken");
        header = "Bearer mockJwtToken";
    }

    @WithMockUser(username = "abc@gmail.com", roles = "{STUDENT}")
    @Test
    void shouldGetQuestionsByQuizId() throws Exception {
        DisplayQuestionDTO mockedDisplayQuestionDTO = buildDisplayQuestionDTO();
        List<DisplayQuestionDTO> displayQuestionDTOList = new ArrayList<>();
        displayQuestionDTOList.add(mockedDisplayQuestionDTO);

        when(questionService.getQuestionsByQuizId(1, 2)).thenReturn(displayQuestionDTOList);

        mockMvc.perform(get("/questions/quiz/1/user/2")
                        .header("Authorization", header)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private DisplayQuestionDTO buildDisplayQuestionDTO() {
        return new DisplayQuestionDTO() {
            @Override
            public Integer getQuestionId() {
                return 0;
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
}
