package Ideas.QuizApp.quiz_data.serviceTest.quizService;

import Ideas.QuizApp.quiz_data.dto.quiz.QuizDTO;
import Ideas.QuizApp.quiz_data.dto.quiz.QuizProjection;
import Ideas.QuizApp.quiz_data.dto.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.entity.Quiz;
import Ideas.QuizApp.quiz_data.entity.QuizTaken;
import Ideas.QuizApp.quiz_data.exception.QuizAlreadyExists;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.serviceTest.projectionutils.MockUtils;
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
        quizDTO = QuizDTO.builder()
                .quizId(1)
                .quizNoOfQuestions(10)
                .quizTotalMarks(100)
                .quizTimeAllocated(60)
                .quizName("Sample Quiz")
                .quizImage("image.jpg")
                .build();

        quiz = Quiz.builder()
                .quizId(1)
                .quizNoOfQuestions(10)
                .quizTotalMarks(100)
                .quizTimeAllocated(60)
                .quizName("Sample Quiz")
                .quizImage("image.jpg")
                .build();
    }

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
        ApplicationUser user = createMockUser(1, "John", "Doe");
        when(applicationUserRepository.findById(anyInt())).thenReturn(Optional.of(user));

        List<UserQuizDetailsDTO> mockedQuizDetails = MockUtils.getMockUserQuizDetailsDTOs();
        when(quizTakenRepository.findByApplicationUser(any(ApplicationUser.class)))
                .thenReturn(mockedQuizDetails);

        List<UserQuizDetailsDTO> result = quizService.getQuizzesByUser(1);

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

    @Test
    void testUpdateQuizSuccess() {
        when(quizRepository.findById(anyInt())).thenReturn(Optional.of(quiz));
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        QuizDTO result = quizService.updateQuiz(quiz);

        assertEquals(quizDTO.getQuizId(), result.getQuizId());
        verify(quizRepository, times(1)).save(any(Quiz.class));
    }

    @Test
    void testUpdateQuizThrowsResourceNotFound() {
        when(quizRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> quizService.updateQuiz(quiz));
        verify(quizRepository, never()).save(any(Quiz.class));
    }
}
