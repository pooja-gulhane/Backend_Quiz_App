package Ideas.QuizApp.quiz_data.repositoryTest.QuizRepositoryTest;

import Ideas.QuizApp.quiz_data.repository.QuizRepository;
import Ideas.QuizApp.quiz_data.repository.QuizTakenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class QuizRepositorytest {
    @Autowired
    QuizRepository quizRepository;

    @Test
    void existsByQuizName()
    {
        String quizName = "Environmental Science";
        boolean actualexistsByQuizName = quizRepository.existsByQuizName(quizName);
        assertEquals(actualexistsByQuizName, true);
    }
}
