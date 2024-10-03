package Ideas.QuizApp.quiz_data.serviceTest.quizTakenService;

import Ideas.QuizApp.quiz_data.dto.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.serviceTest.projectionutils.MockUtils;
import Ideas.QuizApp.quiz_data.repository.ApplicationUserRepository;
import Ideas.QuizApp.quiz_data.repository.QuizTakenRepository;
import Ideas.QuizApp.quiz_data.services.QuizTakenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuizTakenServiceTest {

    @Mock
    private QuizTakenRepository quizTakenRepository;

    @Mock
    private ApplicationUserRepository applicationUserRepository;

    @InjectMocks
    private QuizTakenService quizTakenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserQuizHistory_UserExists_ShouldReturnQuizHistory() {
        int applicationUserId = 1;
        ApplicationUser mockUser = createMockUser(applicationUserId);

        when(applicationUserRepository.findById(applicationUserId)).thenReturn(Optional.of(mockUser));

        UserQuizDetailsDTO mockQuizDetails = createMockQuizDetails();
        when(quizTakenRepository.findByApplicationUser(mockUser)).thenReturn(List.of(mockQuizDetails));

        List<UserQuizDetailsDTO> quizHistory = quizTakenService.getUserQuizHistory(applicationUserId);

        verifyQuizHistory(quizHistory);
        verify(quizTakenRepository, times(1)).findByApplicationUser(mockUser);
    }


    private ApplicationUser createMockUser(int applicationUserId) {
        ApplicationUser mockUser = new ApplicationUser();
        mockUser.setApplicationUserId(applicationUserId);
        return mockUser;
    }

    private UserQuizDetailsDTO createMockQuizDetails() {
        return MockUtils.mockUserQuizDetailsDTO(100, 1, "Math Quiz", 1, "Pooja", "Gulhane");
    }

    private void verifyQuizHistory(List<UserQuizDetailsDTO> quizHistory) {
        assertNotNull(quizHistory);
        assertEquals(1, quizHistory.size());

        UserQuizDetailsDTO quizDetails = quizHistory.get(0);
        assertEquals(100, quizDetails.getScoreValue());
        assertEquals("Math Quiz", quizDetails.getQuiz().getQuizName());
        assertEquals("Pooja", quizDetails.getApplicationUser().getApplicationUserFirstName());
    }


    @Test
    void testGetUserQuizHistory_UserNotFound_ShouldThrowResourceNotFoundException() {
        int applicationUserId = 1;

        when(applicationUserRepository.findById(applicationUserId)).thenReturn(Optional.empty());

        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () -> {
            quizTakenService.getUserQuizHistory(applicationUserId);
        });

        assertEquals("User Not Found", exception.getMessage());
        verify(quizTakenRepository, never()).findByApplicationUser(any());
    }
}
