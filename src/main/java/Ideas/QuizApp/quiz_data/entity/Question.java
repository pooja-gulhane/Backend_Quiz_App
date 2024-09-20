package Ideas.QuizApp.quiz_data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questionId;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private String questionDescription;
    private String questionOption1;
    private String questionOption2;
    private String questionOption3;
    private String questionOption4;
    private String questionCorrectAns;
    private Integer questionMarks;

}



//package Ideas.QuizApp.quiz_data.entity;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Question {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer questionId;
//
//    @ManyToOne
//    @JoinColumn(name = "quiz_id")
//    private Quiz quiz; //Many Question instances can be associated with one Quiz.
//
//    private String questionDescription;
//    private String questionOption1;
//    private String questionOption2;
//    private String questionOption3;
//    private String questionOption4;
//    private String questionCorrectAns;
//    private Integer questionMarks;
//
//    public Question()
//    {
//
//    }
//
//    public Question(Integer questionId,String questionDescription, String questionOption1, String questionOption2, String questionOption3, String questionOption4, String questionCorrectAns, Integer questionMarks,Quiz quiz) {
//        this.questionId = questionId;
//        this.quiz = quiz;
//        this.questionDescription = questionDescription;
//        this.questionOption1 = questionOption1;
//        this.questionOption2 = questionOption2;
//        this.questionOption3 = questionOption3;
//        this.questionOption4 = questionOption4;
//        this.questionCorrectAns = questionCorrectAns;
//        this.questionMarks = questionMarks;
//    }
//
//    // Getters and Setters
//    public Integer getQuestionId() {
//        return questionId;
//    }
//
//    public void setQuestionId(Integer questionId) {
//        this.questionId = questionId;
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
//    public String getQuestionDescription() {
//        return questionDescription;
//    }
//
//    public void setQuestionDescription(String questionDescription) {
//        this.questionDescription = questionDescription;
//    }
//
//    public String getQuestionOption1() {
//        return questionOption1;
//    }
//
//    public void setQuestionOption1(String questionOption1) {
//        this.questionOption1 = questionOption1;
//    }
//
//    public String getQuestionOption2() {
//        return questionOption2;
//    }
//
//    public void setQuestionOption2(String questionOption2) {
//        this.questionOption2 = questionOption2;
//    }
//
//    public String getQuestionOption3() {
//        return questionOption3;
//    }
//
//    public void setQuestionOption3(String questionOption3) {
//        this.questionOption3 = questionOption3;
//    }
//
//    public String getQuestionOption4() {
//        return questionOption4;
//    }
//
//    public void setQuestionOption4(String questionOption4) {
//        this.questionOption4 = questionOption4;
//    }
//
//    public String getQuestionCorrectAns() {
//        return questionCorrectAns;
//    }
//
//    public void setQuestionCorrectAns(String questionCorrectAns) {
//        this.questionCorrectAns = questionCorrectAns;
//    }
//
//    public Integer getQuestionMarks() {
//        return questionMarks;
//    }
//
//    public void setQuestionMarks(Integer questionMarks) {
//        this.questionMarks = questionMarks;
//    }
//
//    @Override
//    public String toString() {
//        return "Question{" +
//                "questionId=" + questionId +
//                ", quiz=" + quiz +
//                ", questionDescription='" + questionDescription + '\'' +
//                ", questionOption1='" + questionOption1 + '\'' +
//                ", questionOption2='" + questionOption2 + '\'' +
//                ", questionOption3='" + questionOption3 + '\'' +
//                ", questionOption4='" + questionOption4 + '\'' +
//                ", questionCorrectAns='" + questionCorrectAns + '\'' +
//                ", questionMarks=" + questionMarks +
//                '}';
//    }
//}
//
