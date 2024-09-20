package Ideas.QuizApp.quiz_data.DTO.quiztaken;

import java.time.LocalDateTime;

public interface UserQuizDetailsDTO {

    Integer getScoreValue();
    LocalDateTime getQuizTakenDate();
    QuizProjection getQuiz();
    UserProjection getApplicationUser();

    interface QuizProjection {
        Integer getQuizId();
        String getQuizName();
        Integer getQuizTotalMarks();
    }

    interface UserProjection {
        Integer getApplicationUserId();
        String getApplicationUserFirstName();
        String getApplicationUserLastName();
    }
}
