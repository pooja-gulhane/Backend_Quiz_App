package Ideas.QuizApp.quiz_data.QuestionService;

import Ideas.QuizApp.quiz_data.DTO.Question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.DTO.Question.QuestionDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.entity.Question;
import Ideas.QuizApp.quiz_data.entity.Quiz;
import Ideas.QuizApp.quiz_data.projectionutils.MockUtils;
import Ideas.QuizApp.quiz_data.entity.UserResponse;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.repository.QuestionRepository;
import Ideas.QuizApp.quiz_data.repository.QuizRepository;
import Ideas.QuizApp.quiz_data.repository.UserResponseRepository;
import Ideas.QuizApp.quiz_data.services.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private UserResponseRepository userResponseRepository;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuestionsByQuizId_Success() {
        int quizId = 1; int userId = 2;

        Quiz mockQuiz = new Quiz();
        mockQuiz.setQuizId(quizId);

        when(quizRepository.findById(quizId)).thenReturn(Optional.of(mockQuiz));

        DisplayQuestionDTO mockQuestionDTO = MockUtils.mockDisplayQuestionDTO(1, "Question?", "A", "B", "C", "D");
        when(questionRepository.findByQuiz(quizId)).thenReturn(List.of(mockQuestionDTO));

        when(userResponseRepository.existsByQuizAndApplicationUserAndQuestion(any(Quiz.class), any(ApplicationUser.class), any(Question.class))).thenReturn(false);

        List<DisplayQuestionDTO> result = questionService.getQuestionsByQuizId(quizId, userId);

        assertEquals(1, result.size());
        verify(userResponseRepository, times(1)).save(any(UserResponse.class));
    }

    @Test
    void testGetQuestionsByQuizId_QuizNotFound() {
        int quizId = 1;
        int userId = 2;

        when(quizRepository.findById(quizId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> questionService.getQuestionsByQuizId(quizId, userId));
    }

    @Test
    void testUpdateQuestion_Success() {
        int questionId = 1;
        Question mockQuestion = MockUtils.mockQuestionDTO(1, "Old Question?", "A", "B", "C", "D", "A", 5);

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(mockQuestion));

        Question updatedQuestion = MockUtils.mockQuestionDTO(1, "Updated Question?", "X", "Y", "Z", "W", "X", 10);
        when(questionRepository.save(any(Question.class))).thenReturn(updatedQuestion);

        QuestionDTO result = questionService.updateQuestion(questionId, updatedQuestion);

        assertEquals("Updated Question?", result.getQuestionDescription());
        assertEquals("X", result.getQuestionOption1());
        verify(questionRepository, times(1)).save(any(Question.class));
    }
}

