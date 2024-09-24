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
    //private LocalDateTime userResponseDateTime;

    private long userResponseDateTime;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private Boolean isCorrect;

    // Method to set the LocalDateTime as long (epoch milliseconds)
    public void setUserResponseDateTime(LocalDateTime localDateTime) {
        this.userResponseDateTime = localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    // Method to get LocalDateTime from epoch milliseconds
    public LocalDateTime getUserResponseDateTimeAsLocalDateTime() {
        return LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(this.userResponseDateTime), java.time.ZoneId.systemDefault());
    }
}




//package Ideas.QuizApp.quiz_data.entity;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//public class UserResponse {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer userResponseId;
//
//    @ManyToOne //Many UserResponse instances can be associated with one Question.
//    @JoinColumn(name = "question_id")
//    private Question question;
//
//    @ManyToOne //Many UserResponse instances can be associated with one User.
//    @JoinColumn(name = "application_user_id")
//    private ApplicationUser applicationUser;
//
//    private String userResponseAns;
//    private LocalDateTime userResponseDateTime;
//
//    @ManyToOne // Many UserResponse instances can be associated with one Quiz.
//    @JoinColumn(name = "quiz_id")
//    private Quiz quiz;
//
//    public UserResponse()
//    {
//
//    }
//
//    public UserResponse(Integer userResponseId, ApplicationUser applicationUser, Question question, String userResponseAns,LocalDateTime userResponseDateTime,Quiz quiz) {
//        this.userResponseId = userResponseId;
//        this.applicationUser = applicationUser;
//        this.question = question;
//        this.quiz = quiz;
//        this.userResponseAns = userResponseAns;
//        this.userResponseDateTime =userResponseDateTime;
//    }
//
//    // Getters and Setters
//    public Integer getUserResponseId() {
//        return userResponseId;
//    }
//
//    public void setUserResponseId(Integer userResponseId) {
//        this.userResponseId = userResponseId;
//    }
//
//    public Question getQuestion() {
//        return question;
//    }
//
//    public void setQuestion(Question question) {
//        this.question = question;
//    }
//
//    public ApplicationUser getApplicationUser() {
//        return applicationUser;
//    }
//
//    public void setApplicationUser(ApplicationUser user) {
//        this.applicationUser = user;
//    }
//
//    public String getUserResponseAns() {
//        return userResponseAns;
//    }
//
//    public void setUserResponseAns(String userResponseAns) {
//        this.userResponseAns = userResponseAns;
//    }
//
//    public LocalDateTime getUserResponseDateTime() {
//        return userResponseDateTime;
//    }
//
//    public void setUserResponseDateTime(LocalDateTime userResponseDateTime) {
//        this.userResponseDateTime = userResponseDateTime;
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
//    @Override
//    public String toString() {
//        return "UserResponse{" +
//                "userResponseId=" + userResponseId +
//                ", question=" + question +
//                ", applicationUser=" + applicationUser +
//                ", userResponseAns='" + userResponseAns + '\'' +
//                ", userResponseDateTime='" + userResponseDateTime +'\''+
//                ", quiz=" +quiz+
//                '}';
//    }
//}
//
