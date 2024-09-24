package Ideas.QuizApp.quiz_data.repository;

import Ideas.QuizApp.quiz_data.DTO.Question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.entity.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface QuestionRepository extends CrudRepository<Question, Integer> {
    @Query(value = "SELECT q.question_id as questionId,q.question_description as questionDescription, " +
            "q.question_option1 as questionOption1,q.question_option2 as questionOption2, q.question_option3" +
            " as questionOption3, q.question_option4 as questionOption4 FROM Question " +
            "q WHERE q.quiz_id = ?1 ORDER BY RANDOM()", nativeQuery = true)
    List<DisplayQuestionDTO> findByQuiz( Integer quizId);

    @Query(value = "Select count(q.question_id) as totalQuestions from Question q where q.quiz_id = ?1",nativeQuery = true)
    Integer findCountByQuizId(Integer quizId);

}