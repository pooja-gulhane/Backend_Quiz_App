package Ideas.QuizApp.quiz_data.DTO.UserResponse;

import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.ApplicationUserRegisterDTO;
import Ideas.QuizApp.quiz_data.DTO.Question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.DTO.Question.QuestionDTO;
import Ideas.QuizApp.quiz_data.DTO.Quiz.QuizDTO;
import Ideas.QuizApp.quiz_data.DTO.Quiz.QuizProjection;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.entity.Question;
import Ideas.QuizApp.quiz_data.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmitUserResponseDTO {
    private QuestionDTO question; // Reference to the Question entity
    private ApplicationUserRegisterDTO applicationUser; // Reference to the ApplicationUser entity
    private String userResponseAns; // User's answer
    private QuizDTO quiz; // Reference to the Quiz entity
}
