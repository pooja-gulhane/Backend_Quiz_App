package Ideas.QuizApp.quiz_data.dto.userResponse;


import Ideas.QuizApp.quiz_data.dto.applicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.dto.question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.dto.quiz.QuizProjection;

public interface CurrentResponseDTO {
    Integer getUserResponseId();
    String getUserResponseAns();
    DisplayQuestionDTO getQuestion();
    UserDTO getApplicationUser();
    QuizProjection getQuiz();
}
