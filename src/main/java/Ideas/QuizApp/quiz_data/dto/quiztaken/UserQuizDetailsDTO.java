package Ideas.QuizApp.quiz_data.dto.quiztaken;

import Ideas.QuizApp.quiz_data.dto.quiz.QuizProjection;

import java.time.LocalDateTime;

public interface UserQuizDetailsDTO {

    Integer getScoreValue();
    LocalDateTime getQuizTakenDate();
    QuizProjection getQuiz();
    UserProjection getApplicationUser();

    interface UserProjection {
        Integer getApplicationUserId();
        String getApplicationUserFirstName();
        String getApplicationUserLastName();
    }
}
