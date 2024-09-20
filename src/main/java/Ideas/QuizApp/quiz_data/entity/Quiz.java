package Ideas.QuizApp.quiz_data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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





//package Ideas.QuizApp.quiz_data.entity;
//
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//public class Quiz {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer quizId;
//
//    private Integer quizNoOfQuestions;
//    private Integer quizTotalMarks;
//    private Integer quizTimeAllocated;
//    private String quizName;
//
//    @OneToMany(mappedBy = "quiz")//One Quiz can have many Question and Score entries.
//    private List<Question> quizQuestions;
//
//    @OneToMany(mappedBy = "quiz")
//    private List<Score> quizScores;
//
//    public Quiz()
//    {
//
//    }
//
//    public Quiz(Integer quizId, Integer quizNoOfQuestions, Integer quizTotalMarks, Integer quizTimeAllocated, String quizName) {
//        this.quizId = quizId;
//        this.quizNoOfQuestions = quizNoOfQuestions;
//        this.quizTotalMarks = quizTotalMarks;
//        this.quizTimeAllocated = quizTimeAllocated;
//        this.quizName = quizName;
//    }
//
//    // Getters and Setters
//    public List<Score> getQuizScores() {
//        return quizScores;
//    }
//
//    public void setQuizScores(List<Score> quizScores) {
//        this.quizScores = quizScores;
//    }
//
//    public List<Question> getQuizQuestions() {
//        return quizQuestions;
//    }
//
//    public void setQuizQuestions(List<Question> quizQuestions) {
//        this.quizQuestions = quizQuestions;
//    }
//
//    public String getQuizName() {
//        return quizName;
//    }
//
//    public void setQuizName(String quizName) {
//        this.quizName = quizName;
//    }
//
//    public Integer getQuizTimeAllocated() {
//        return quizTimeAllocated;
//    }
//
//    public void setQuizTimeAllocated(Integer quizTimeAllocated) {
//        this.quizTimeAllocated = quizTimeAllocated;
//    }
//
//    public Integer getQuizTotalMarks() {
//        return quizTotalMarks;
//    }
//
//    public void setQuizTotalMarks(Integer quizTotalMarks) {
//        this.quizTotalMarks = quizTotalMarks;
//    }
//
//    public Integer getQuizNoOfQuestions() {
//        return quizNoOfQuestions;
//    }
//
//    public void setQuizNoOfQuestions(Integer quizNoOfQuestions) {
//        this.quizNoOfQuestions = quizNoOfQuestions;
//    }
//
//    public Integer getQuizId() {
//        return quizId;
//    }
//
//    public void setQuizId(Integer quizId) {
//        this.quizId = quizId;
//    }
//
//    @Override
//    public String toString() {
//        return "Quiz{" +
//                "quizId=" + quizId +
//                ", quizNoOfQuestions=" + quizNoOfQuestions +
//                ", quizTotalMarks=" + quizTotalMarks +
//                ", quizTimeAllocated=" + quizTimeAllocated +
//                ", quizName='" + quizName + '\'' +
//                '}';
//    }
//}
