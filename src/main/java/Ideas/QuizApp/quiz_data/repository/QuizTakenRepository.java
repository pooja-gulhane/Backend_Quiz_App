package Ideas.QuizApp.quiz_data.repository;

import Ideas.QuizApp.quiz_data.dto.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.entity.QuizTaken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuizTakenRepository extends CrudRepository<QuizTaken, Integer> {
    List<UserQuizDetailsDTO> findByApplicationUser(ApplicationUser applicationUser);

    List<QuizTaken> findByApplicationUser_ApplicationUserId(int applicationUserId);
}
