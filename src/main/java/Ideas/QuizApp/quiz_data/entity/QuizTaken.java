package Ideas.QuizApp.quiz_data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizTaken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer quizTakenId;

    @ManyToOne
    @JoinColumn(name = "application_user_id")
    private ApplicationUser applicationUser;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private Integer scoreValue;

    private LocalDateTime quizTakenDate;
}




