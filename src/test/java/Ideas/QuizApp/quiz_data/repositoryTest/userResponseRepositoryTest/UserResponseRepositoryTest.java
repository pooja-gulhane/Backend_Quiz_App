package Ideas.QuizApp.quiz_data.repositoryTest.userResponseRepositoryTest;


import Ideas.QuizApp.quiz_data.dto.userResponse.CurrentResponseDTO;
import Ideas.QuizApp.quiz_data.dto.userResponse.DisplayUserResponseDTO;
import Ideas.QuizApp.quiz_data.dto.quiztaken.QuestionResponseProjection;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.entity.Question;
import Ideas.QuizApp.quiz_data.entity.Quiz;
import Ideas.QuizApp.quiz_data.repository.UserResponseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserResponseRepositoryTest {

    @Autowired
    UserResponseRepository userResponseRepository;

    @Test
    void findByQuizQuizIdAndApplicationUserApplicationUserId()
    {
        Integer quizId = 52;
        Integer userId = 652;
        List<DisplayUserResponseDTO> actualFindByQuizQuizIdAndApplicationUserApplicationUserId = userResponseRepository.findByQuizQuizIdAndApplicationUserApplicationUserId(quizId,userId);

        assertEquals(actualFindByQuizQuizIdAndApplicationUserApplicationUserId.size() , 5);
        assertEquals(actualFindByQuizQuizIdAndApplicationUserApplicationUserId.get(0).getUserResponseAns(), "Natural Gas");
    }

    @Test
    void existsByQuizAndApplicationUserAndQuestion()
    {
        Quiz quiz = new Quiz();
        quiz.setQuizId(52);

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setApplicationUserId(652);

        Question question = new Question();
        question.setQuestionId(152);

        Boolean actualExistsByQuizAndApplicationUserAndQuestion= userResponseRepository.existsByQuizAndApplicationUserAndQuestion(quiz, applicationUser, question);
        assertEquals(true, actualExistsByQuizAndApplicationUserAndQuestion);
    }

    @Test
    void findByQuizAndApplicationUserAndQuestion()
    {
        Quiz quiz = new Quiz();
        quiz.setQuizId(52);

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setApplicationUserId(652);

        Question question = new Question();
        question.setQuestionId(152);

        CurrentResponseDTO actualFindByQuizAndApplicationUserAndQuestion = userResponseRepository.findByQuizAndApplicationUserAndQuestion(quiz, applicationUser,question);
        assertEquals(actualFindByQuizAndApplicationUserAndQuestion.getUserResponseAns(), "Natural Gas");
    }

    @Test
    void findByApplicationUserAndQuiz()
    {
        Quiz quiz = new Quiz();
        quiz.setQuizId(52);

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setApplicationUserId(652);

        List<QuestionResponseProjection> actualFindByApplicationUserAndQuiz = userResponseRepository.findByApplicationUserAndQuiz(applicationUser,quiz);
        assertEquals(actualFindByApplicationUserAndQuiz.get(0).getUserResponseId(), 2152);
        assertEquals(actualFindByApplicationUserAndQuiz.get(0).getIsCorrect(), false);
    }

}
