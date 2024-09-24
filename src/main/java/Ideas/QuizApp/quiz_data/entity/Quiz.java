package Ideas.QuizApp.quiz_data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer quizId;

    private Integer quizNoOfQuestions;
    private Integer quizTotalMarks;
    private Integer quizTimeAllocated;
    @Column(unique = true)
    private String quizName;
    private String quizImage;

    @OneToMany(mappedBy = "quiz")
    private List<Question> quizQuestions;

    @OneToMany(mappedBy = "quiz")
    private List<QuizTaken> quizQuizTaken;
}


