package Ideas.QuizApp.quiz_data.repositoryTest.quizRepositoryTest;

import Ideas.QuizApp.quiz_data.repository.QuizRepository;
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
