package Ideas.QuizApp.quiz_data.repository;

import Ideas.QuizApp.quiz_data.DTO.UserResponse.CurrentResponseDTO;
import Ideas.QuizApp.quiz_data.DTO.UserResponse.DisplayUserResponseDTO;
import Ideas.QuizApp.quiz_data.DTO.quiztaken.QuestionResponseProjection;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.entity.Question;
import Ideas.QuizApp.quiz_data.entity.Quiz;
import Ideas.QuizApp.quiz_data.entity.UserResponse;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserResponseRepository extends CrudRepository<UserResponse, Integer> {
    List<DisplayUserResponseDTO> findByQuiz(Quiz quiz);

    List<DisplayUserResponseDTO> findByQuizQuizIdAndApplicationUserApplicationUserId(Integer quizId, Integer userId);

    Boolean existsByQuizAndApplicationUserAndQuestion(Quiz quiz, ApplicationUser applicationUser, Question question);

    CurrentResponseDTO findByQuizAndApplicationUserAndQuestion(Quiz quiz, ApplicationUser applicationUser, Question question);

    List<QuestionResponseProjection> findByApplicationUserAndQuiz(ApplicationUser user, Quiz quiz);
}