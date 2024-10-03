package Ideas.QuizApp.quiz_data.dto.userResponse;

import Ideas.QuizApp.quiz_data.dto.applicationUser.ApplicationUserRegisterDTO;
import Ideas.QuizApp.quiz_data.dto.question.QuestionDTO;
import Ideas.QuizApp.quiz_data.dto.quiz.QuizDTO;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmitUserResponseDTO {
    private QuestionDTO question;
    private ApplicationUserRegisterDTO applicationUser;
    private String userResponseAns;
    private QuizDTO quiz;
}
