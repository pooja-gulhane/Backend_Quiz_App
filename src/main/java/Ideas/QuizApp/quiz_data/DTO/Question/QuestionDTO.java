package Ideas.QuizApp.quiz_data.DTO.Question;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {

    private Integer questionId;
    private String questionDescription;
    private String questionOption1;
    private String questionOption2;
    private String questionOption3;
    private String questionOption4;
    private String questionCorrectAns;
    private Integer questionMarks;
}