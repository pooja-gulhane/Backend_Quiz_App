package Ideas.QuizApp.quiz_data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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




//package Ideas.QuizApp.quiz_data.entity;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Score {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer scoreId;
//
//    @ManyToOne //Many Score instances can be associated with one User.
//    @JoinColumn(name = "application_user_id")
//    private ApplicationUser applicationUser;
//
//    @ManyToOne //Many Score instances can be associated with one Quiz.
//    @JoinColumn(name = "quiz_id")
//    private Quiz quiz;
//
//    private Integer scoreValue;
//
//    public Score()
//    {
//
//    }
//
//    public Score(Integer scoreId, ApplicationUser applicationUser, Quiz quiz, Integer scoreValue) {
//        this.scoreId = scoreId;
//        this.applicationUser = applicationUser;
//        this.quiz = quiz;
//        this.scoreValue = scoreValue;
//    }
//
//    // Getters and Setters
//    public Integer getScoreId() {
//        return scoreId;
//    }
//
//    public void setScoreId(Integer scoreId) {
//        this.scoreId = scoreId;
//    }
//
//    public ApplicationUser getApplicationUser() {
//        return applicationUser;
//    }
//
//    public void setApplicationUser(ApplicationUser applicationUser) {
//        this.applicationUser = applicationUser;
//    }
//
//    public Quiz getQuiz() {
//        return quiz;
//    }
//
//    public void setQuiz(Quiz quiz) {
//        this.quiz = quiz;
//    }
//
//    public Integer getScoreValue() {
//        return scoreValue;
//    }
//
//    public void setScoreValue(Integer scoreValue) {
//        this.scoreValue = scoreValue;
//    }
//
//    @Override
//    public String toString() {
//        return "Score{" +
//                "scoreId=" + scoreId +
//                ", applicationUser=" + applicationUser +
//                ", quiz=" + quiz +
//                ", scoreValue=" + scoreValue +
//                '}';
//    }
//}
