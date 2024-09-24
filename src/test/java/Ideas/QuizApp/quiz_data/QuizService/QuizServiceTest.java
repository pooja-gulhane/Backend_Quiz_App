package Ideas.QuizApp.quiz_data.QuizService;

import Ideas.QuizApp.quiz_data.DTO.Quiz.QuizDTO;
import Ideas.QuizApp.quiz_data.DTO.Quiz.QuizProjection;
import Ideas.QuizApp.quiz_data.DTO.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.entity.Quiz;
import Ideas.QuizApp.quiz_data.entity.QuizTaken;
import Ideas.QuizApp.quiz_data.exception.QuizAlreadyExists;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.projectionutils.MockUtils;
import Ideas.QuizApp.quiz_data.repository.ApplicationUserRepository;
import Ideas.QuizApp.quiz_data.repository.QuizRepository;
import Ideas.QuizApp.quiz_data.repository.QuizTakenRepository;
import Ideas.QuizApp.quiz_data.services.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    @InjectMocks
    QuizService quizService;

    @Mock
    QuizRepository quizRepository;

    @Mock
    QuizTakenRepository quizTakenRepository;

    @Mock
    ApplicationUserRepository applicationUserRepository;


    private QuizDTO quizDTO;
    private Quiz quiz;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quizDTO = new QuizDTO(1, 10, 100, 60, "Sample Quiz", "image.jpg");
        quiz = new Quiz();
        quiz.setQuizId(1);
        quiz.setQuizNoOfQuestions(10);
        quiz.setQuizTotalMarks(100);
        quiz.setQuizTimeAllocated(60);
        quiz.setQuizName("Sample Quiz");
        quiz.setQuizImage("image.jpg");
    }

    //create Quiz
    @Test
    void testCreateQuizSuccess() {
        when(quizRepository.existsByQuizName(anyString())).thenReturn(false);
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        QuizDTO result = quizService.createQuiz(quizDTO);

        assertEquals(quizDTO.getQuizName(), result.getQuizName());
        verify(quizRepository, times(1)).save(any(Quiz.class));
    }

    @Test
    void testCreateQuizThrowsQuizAlreadyExists() {
        when(quizRepository.existsByQuizName(anyString())).thenReturn(true);

        assertThrows(QuizAlreadyExists.class, () -> quizService.createQuiz(quizDTO));
        verify(quizRepository, never()).save(any(Quiz.class));
    }

    //Get Quiz by ID
    @Test
    void testGetQuizByIdSuccess() {
        when(quizRepository.findById(anyInt())).thenReturn(Optional.of(quiz));

        QuizDTO result = quizService.getQuizById(1);

        assertEquals(quizDTO.getQuizId(), result.getQuizId());
        verify(quizRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetQuizByIdThrowsResourceNotFound() {
        when(quizRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> quizService.getQuizById(1));
        verify(quizRepository, times(1)).findById(anyInt());
    }


    @Test
    void testGetQuizzesByUserSuccess() {
        // Arrange
        ApplicationUser user = createMockUser(1, "John", "Doe");
        when(applicationUserRepository.findById(anyInt())).thenReturn(Optional.of(user));

        List<UserQuizDetailsDTO> mockedQuizDetails = MockUtils.getMockUserQuizDetailsDTOs();
        when(quizTakenRepository.findByApplicationUser(any(ApplicationUser.class)))
                .thenReturn(mockedQuizDetails);

        // Act
        List<UserQuizDetailsDTO> result = quizService.getQuizzesByUser(1);

        // Assert
        assertQuizDetails(result);
        verifyQuizTakenRepositoryCall();
    }

    private ApplicationUser createMockUser(int userId, String firstName, String lastName) {
        ApplicationUser user = new ApplicationUser();
        user.setApplicationUserId(userId);
        user.setApplicationUserFirstName(firstName);
        user.setApplicationUserLastName(lastName);
        return user;
    }

    private void assertQuizDetails(List<UserQuizDetailsDTO> result) {
        assertNotNull(result);
        assertEquals(1, result.size());

        UserQuizDetailsDTO quizDetails = result.get(0);

        assertEquals(85, quizDetails.getScoreValue());
        assertQuizProjection(quizDetails.getQuiz());
        assertUserProjection(quizDetails.getApplicationUser());
    }

    private void assertQuizProjection(QuizProjection quiz) {
        assertNotNull(quiz);
        assertEquals(1, quiz.getQuizId());
        assertEquals("Sample Quiz", quiz.getQuizName());
        assertEquals(100, quiz.getQuizTotalMarks());
    }

    private void assertUserProjection(UserQuizDetailsDTO.UserProjection user) {
        assertNotNull(user);
        assertEquals(1, user.getApplicationUserId());
        assertEquals("John", user.getApplicationUserFirstName());
        assertEquals("Doe", user.getApplicationUserLastName());
    }

    private void verifyQuizTakenRepositoryCall() {
        verify(quizTakenRepository, times(1)).findByApplicationUser(any(ApplicationUser.class));
    }



    //Quiz not taken by user
    @Test
    void testGetQuizzesNotTakenByUserSuccess() {
        ApplicationUser user = new ApplicationUser();
        user.setApplicationUserId(1);

        List<Quiz> allQuizzes = new ArrayList<>();
        allQuizzes.add(quiz);

        List<QuizTaken> quizzesTakenByUser = new ArrayList<>();

        when(applicationUserRepository.findById(anyInt()))
                .thenReturn(Optional.of(user));
        when(quizRepository.findAll()).thenReturn(allQuizzes);
        when(quizTakenRepository.findByApplicationUser_ApplicationUserId(anyInt()))
                .thenReturn(quizzesTakenByUser);

        List<QuizDTO> result = quizService.getQuizzesNotTakenByUser(1);

        assertEquals(1, result.size());
        verify(quizRepository, times(1)).findAll();
    }

    @Test
    void testGetQuizzesNotTakenByUserThrowsResourceNotFound() {
        when(applicationUserRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> quizService.getQuizzesNotTakenByUser(1));
        verify(quizRepository, never()).findAll();
    }

    //update the quizzes
    @Test
    void testUpdateQuizSuccess() {
        when(quizRepository.findById(anyInt())).thenReturn(Optional.of(quiz));
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        QuizDTO result = quizService.updateQuiz(1, quiz);

        assertEquals(quizDTO.getQuizId(), result.getQuizId());
        verify(quizRepository, times(1)).save(any(Quiz.class));
    }

    @Test
    void testUpdateQuizThrowsResourceNotFound() {
        when(quizRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> quizService.updateQuiz(1, quiz));
        verify(quizRepository, never()).save(any(Quiz.class));
    }
}
