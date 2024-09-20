package Ideas.QuizApp.quiz_data.repository;

import Ideas.QuizApp.quiz_data.entity.Quiz;
import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends CrudRepository<Quiz, Integer> {
    Boolean existsByQuizName(String quizName);
}