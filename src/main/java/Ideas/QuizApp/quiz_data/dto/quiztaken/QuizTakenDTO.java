package Ideas.QuizApp.quiz_data.dto.quiztaken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizTakenDTO {
    private Integer quizTakenId;
    private Integer applicationUserId;
    private Integer quizId;
    private Integer scoreValue;
}