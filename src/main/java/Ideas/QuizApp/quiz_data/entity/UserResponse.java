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
public class UserResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userResponseId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "application_user_id")
    private ApplicationUser applicationUser;

    private String userResponseAns;

    private long userResponseDateTime;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private Boolean isCorrect;

    // Method to set the LocalDateTime as long (epoch milliseconds)
    public void setUserResponseDateTime(LocalDateTime localDateTime) {
        this.userResponseDateTime = localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public LocalDateTime getUserResponseDateTimeAsLocalDateTime() {
        return LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(this.userResponseDateTime), java.time.ZoneId.systemDefault());
    }
}


