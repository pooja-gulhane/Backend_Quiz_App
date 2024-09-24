package Ideas.QuizApp.quiz_data.DTO.quiztaken;

import Ideas.QuizApp.quiz_data.DTO.Quiz.QuizProjection;

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
