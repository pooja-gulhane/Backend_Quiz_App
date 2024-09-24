package Ideas.QuizApp.quiz_data.repositoryTest.QuizTakenRepositoryTest;

import Ideas.QuizApp.quiz_data.DTO.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.entity.QuizTaken;
import Ideas.QuizApp.quiz_data.repository.QuizTakenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class QuizTakenRepositoryTest {
    @Autowired
    private QuizTakenRepository quizTakenRepository;

    @Test
    void findByApplicationUser() {
        ApplicationUser user = new ApplicationUser();
        user.setApplicationUserId(652);

        List<UserQuizDetailsDTO> actualQuizDetails = quizTakenRepository.findByApplicationUser(user);
        assertEquals(actualQuizDetails.get(0).getScoreValue(), 2);
    }

    @Test
    void findByApplicationUser_ApplicationUserId()
    {
        int applicationUserId = 652;
        List<QuizTaken> actualQuizTaken = quizTakenRepository.findByApplicationUser_ApplicationUserId(applicationUserId);

        assertEquals(actualQuizTaken.get(0).getQuizTakenId(), 1102);
        assertEquals(actualQuizTaken.get(0).getScoreValue(), 2);
    }
}
