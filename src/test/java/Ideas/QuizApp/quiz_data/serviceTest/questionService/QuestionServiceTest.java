package Ideas.QuizApp.quiz_data.serviceTest.questionService;

import Ideas.QuizApp.quiz_data.dto.question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.dto.question.QuestionDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.entity.Question;
import Ideas.QuizApp.quiz_data.entity.Quiz;
import Ideas.QuizApp.quiz_data.exception.QuestionLimitExceededException;
import Ideas.QuizApp.quiz_data.serviceTest.projectionutils.MockUtils;
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

import java.util.Arrays;
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
        assertEquals("Question?",result.get(0).getQuestionDescription());
        assertEquals("A",result.get(0).getQuestionOption1());
        assertEquals("B",result.get(0).getQuestionOption2());
        assertEquals("C",result.get(0).getQuestionOption3());
        assertEquals("D",result.get(0).getQuestionOption4());
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

        QuestionDTO result = questionService.updateQuestion(updatedQuestion);

        assertEquals("Updated Question?", result.getQuestionDescription());
        assertEquals("X", result.getQuestionOption1());
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    void testUpdateQuestion_QuestionNotFound()
    {
        int questionId = 1;
        Question updatedQuestion = MockUtils.mockQuestionDTO(1, "Updated Question?", "X", "Y", "Z", "W", "X", 10);

        when(questionRepository.findById(questionId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> questionService.updateQuestion(updatedQuestion));

        verify(questionRepository, never()).save(any(Question.class));
    }

    @Test
    void testAddQuestions_Success() {
        int quizId = 1;
        Quiz mockQuiz = new Quiz();
        mockQuiz.setQuizId(quizId);
        mockQuiz.setQuizNoOfQuestions(5);

        when(quizRepository.findById(quizId)).thenReturn(Optional.of(mockQuiz));
        when(questionRepository.findCountByQuizId(quizId)).thenReturn(3);
        Quiz quiz = buildQuiz();

        List<Question> questionsToAdd = Arrays.asList(
                new Question(1, quiz, "A", "B", "C", "D", "A", "A", 2),
                new Question(2, quiz, "A", "B", "C", "D", "A", "A", 2)
        );

        when(questionRepository.saveAll(questionsToAdd)).thenReturn(questionsToAdd);

        List<Question> result = questionService.addQuestions(questionsToAdd);

        assertEquals(2, result.size());
        verify(questionRepository, times(1)).saveAll(questionsToAdd);
    }

    @Test
    void testAddQuestions_QuizNotFound() {
        Quiz quiz = new Quiz();
        quiz.setQuizId(1);

        Question question = new Question();
        question.setQuiz(quiz);

        List<Question> questionsToAdd = Arrays.asList(question);

        when(quizRepository.findById(anyInt())).thenReturn(Optional.empty());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () -> {
            questionService.addQuestions(questionsToAdd);
        });
    }


    @Test
    void testAddQuestions_QuestionLimitExceeded() {
        int quizId = 1;
        Quiz mockQuiz = new Quiz();
        mockQuiz.setQuizId(quizId);
        mockQuiz.setQuizNoOfQuestions(5);

        when(quizRepository.findById(quizId)).thenReturn(Optional.of(mockQuiz));
        when(questionRepository.findCountByQuizId(quizId)).thenReturn(5); // Already 5 questions

        Quiz quiz = buildQuiz();

        List<Question> questionsToAdd = Arrays.asList(
                new Question(1, quiz, "A", "B", "C", "D", "A", "A",2)
        );

        assertThrows(QuestionLimitExceededException.class, () -> {
            questionService.addQuestions(questionsToAdd);
        });
    }

    @Test
    void testAddQuestions_NoQuestionsProvided() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionService.addQuestions(null);
        });
        assertEquals("No questions provided.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            questionService.addQuestions(Arrays.asList());
        });
        assertEquals("No questions provided.", exception.getMessage());
    }



    private Quiz buildQuiz() {
        return Quiz.builder()
                .quizId(1)
                .quizNoOfQuestions(10)
                .quizTotalMarks(20)
                .quizTimeAllocated(10)
                .quizName("EVS")
                .quizImage("image.jpg")
                .build();
    }

}

