package Ideas.QuizApp.quiz_data.controllerTest;

import Ideas.QuizApp.quiz_data.dto.question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.dto.question.QuestionDTO;
import Ideas.QuizApp.quiz_data.controller.QuestionController;
import Ideas.QuizApp.quiz_data.entity.Question;
import Ideas.QuizApp.quiz_data.services.ApplicationUserDetailsService;
import Ideas.QuizApp.quiz_data.services.QuestionService;
import Ideas.QuizApp.quiz_data.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@WebMvcTest(QuestionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class QuestionControllerTest {
    @MockBean
    QuestionService questionService;
    @MockBean
    ApplicationUserDetailsService applicationUserDetailsService;
    @MockBean
    JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;


    @WithMockUser(username = "abc@gmail.com", roles = "{STUDENT}")
    @Test
    void shouldGetQuestionsByQuizId() throws Exception {
        DisplayQuestionDTO mockedDisplayQuestionDTO = buildDisplayQuestionDTO();
        List<DisplayQuestionDTO> displayQuestionDTOList = new ArrayList<>();
        displayQuestionDTOList.add(mockedDisplayQuestionDTO);

        when(questionService.getQuestionsByQuizId(1, 2)).thenReturn(displayQuestionDTOList);

        mockMvc.perform(get("/questions/quiz/1/user/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "abc@gmail.com", roles = "{ADMIN}")
    @Test
    void shouldUpdateQuestion() throws Exception {
        QuestionDTO mockedQuestionDTO = new QuestionDTO();
        mockedQuestionDTO.setQuestionId(1);
        mockedQuestionDTO.setQuestionDescription("Updated Question");

        when(questionService.updateQuestion(any(Question.class))).thenReturn(mockedQuestionDTO);

        mockMvc.perform(put("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"questionDescription\": \"Updated Question\"}")) // Adjust JSON as needed
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionId").value(1))
                .andExpect(jsonPath("$.questionDescription").value("Updated Question"));
    }


    @WithMockUser(username = "abc@gmail.com", roles = "{ADMIN}")
    @Test
    void shouldAddQuestions() throws Exception {
        List<Question> mockedQuestions = new ArrayList<>();
        Question question1 = new Question();
        question1.setQuestionId(1);
        question1.setQuestionDescription("Question 1");
        mockedQuestions.add(question1);

        when(questionService.addQuestions(anyList())).thenReturn(mockedQuestions);

        mockMvc.perform(post("/questions/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"questionDescription\": \"Question 1\"}]")) // Adjust JSON as needed
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].questionId").value(1))
                .andExpect(jsonPath("$[0].questionDescription").value("Question 1"));
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
