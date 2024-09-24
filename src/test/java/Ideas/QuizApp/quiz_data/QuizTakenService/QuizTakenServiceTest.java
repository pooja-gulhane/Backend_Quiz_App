package Ideas.QuizApp.quiz_data.QuizTakenService;

import Ideas.QuizApp.quiz_data.DTO.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.projectionutils.MockUtils;
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
        // Arrange
        int applicationUserId = 1;
        ApplicationUser mockUser = createMockUser(applicationUserId);

        // Mock the repositories
        when(applicationUserRepository.findById(applicationUserId)).thenReturn(Optional.of(mockUser));

        UserQuizDetailsDTO mockQuizDetails = createMockQuizDetails();
        when(quizTakenRepository.findByApplicationUser(mockUser)).thenReturn(List.of(mockQuizDetails));

        // Act
        List<UserQuizDetailsDTO> quizHistory = quizTakenService.getUserQuizHistory(applicationUserId);

        // Assert
        verifyQuizHistory(quizHistory);
        verify(quizTakenRepository, times(1)).findByApplicationUser(mockUser);
    }

    // Helper function to create mock user
    private ApplicationUser createMockUser(int applicationUserId) {
        ApplicationUser mockUser = new ApplicationUser();
        mockUser.setApplicationUserId(applicationUserId);
        return mockUser;
    }

    // Helper function to create mock quiz details
    private UserQuizDetailsDTO createMockQuizDetails() {
        return MockUtils.mockUserQuizDetailsDTO(100, 1, "Math Quiz", 1, "Pooja", "Gulhane");
    }

    // Helper function to verify quiz history assertions
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
        // Arrange
        int applicationUserId = 1;

        // Mock ApplicationUserRepository to return empty
        when(applicationUserRepository.findById(applicationUserId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFound exception = assertThrows(ResourceNotFound.class, () -> {
            quizTakenService.getUserQuizHistory(applicationUserId);
        });

        assertEquals("User Not Found", exception.getMessage());

        // Verify that the repository method was not called
        verify(quizTakenRepository, never()).findByApplicationUser(any());
    }
}
