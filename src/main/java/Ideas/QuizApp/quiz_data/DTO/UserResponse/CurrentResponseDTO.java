package Ideas.QuizApp.quiz_data.DTO.UserResponse;


import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.DTO.Question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.DTO.Quiz.QuizDTO;
import Ideas.QuizApp.quiz_data.DTO.Quiz.QuizProjection;

public interface CurrentResponseDTO {
    Integer getUserResponseId();
    String getUserResponseAns();
    DisplayQuestionDTO getQuestion();
    UserDTO getApplicationUser();
    QuizProjection getQuiz();
}
