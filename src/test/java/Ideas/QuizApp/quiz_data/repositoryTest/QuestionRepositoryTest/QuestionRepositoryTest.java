package Ideas.QuizApp.quiz_data.repositoryTest.QuestionRepositoryTest;

import Ideas.QuizApp.quiz_data.DTO.Question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Test
    void findCountByQuizId()
    {
        Integer quizId = 52;
        Integer actualFindCountByQuizId = questionRepository.findCountByQuizId(quizId);
        assertEquals(actualFindCountByQuizId, 5);
    }

    @Test
    void findByQuiz()
    {
        Integer quizId = 52;
        List<DisplayQuestionDTO> actualFindByQuiz = questionRepository.findByQuiz(quizId);
        assertEquals(actualFindByQuiz.size(),5);
    }
}
