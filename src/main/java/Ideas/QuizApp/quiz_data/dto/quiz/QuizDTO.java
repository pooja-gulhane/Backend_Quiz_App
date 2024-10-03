package Ideas.QuizApp.quiz_data.dto.quiz;


import lombok.*;

@Builder
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