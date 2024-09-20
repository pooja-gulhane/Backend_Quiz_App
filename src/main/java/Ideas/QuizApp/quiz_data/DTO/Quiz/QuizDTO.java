package Ideas.QuizApp.quiz_data.DTO.Quiz;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizDTO {
    private Integer quizId;
    private Integer quizNoOfQuestions;
    private Integer quizTotalMarks;
    private Integer quizTimeAllocated;
    private String quizName;
    private String quizImage;
}