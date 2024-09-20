package Ideas.QuizApp.quiz_data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer applicationUserId;

    @Column(nullable = false)
    private String applicationUserFirstName;

    @Column(nullable = false)
    private String applicationUserLastName;

    @Column(nullable = false, unique = true)
    private String applicationUserEmail;

    @Column(nullable = false)
    private String applicationUserPassword;

    @OneToMany(mappedBy = "applicationUser")
    private List<UserResponse> userResponses;

    @OneToMany(mappedBy = "applicationUser")
    private List<QuizTaken> userQuizTakens;

    private String applicationUserrole;
}






//package Ideas.QuizApp.quiz_data.entity;
//
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//public class ApplicationUser {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer applicationUserId;
//
//    @Column(nullable = false)
//    private String applicationUserFirstName;
//    @Column(nullable = false)
//    private String applicationUserLastName;
//    @Column(nullable = false,unique = true)
//    private String applicationUserEmail;
//    @Column(nullable = false)
//    private String applicationUserPassword;
//
//    @OneToMany(mappedBy = "applicationUser") //One User can have many UserResponse and many Score entries.
//    private List<UserResponse> userResponses;
//
//    @OneToMany(mappedBy = "applicationUser")
//    private List<Score> userScores;
//
//    public ApplicationUser()
//    {
//
//    }
//
//    public ApplicationUser( Integer applicationUserId,String applicationUserFirstName, String applicationUserLastName, String applicationUserEmail,String applicationUserPassword) {
//        this.applicationUserId = applicationUserId;
//        this.applicationUserFirstName = applicationUserFirstName;
//        this.applicationUserLastName = applicationUserLastName;
//        this.applicationUserEmail = applicationUserEmail;
//        this.applicationUserPassword = applicationUserPassword;
//    }
//
//    // Getters and Setters
//    public Integer getApplicationUserId() {
//        return applicationUserId;
//    }
//
//    public void setApplicationUserId(Integer applicationUserId) {
//        this.applicationUserId = applicationUserId;
//    }
//
//    public String getApplicationUserFirstName() {
//        return applicationUserFirstName;
//    }
//
//    public void setApplicationUserFirstName(String applicationUserFirstName) {
//        this.applicationUserFirstName = applicationUserFirstName;
//    }
//
//    public String getApplicationUserLastName() {
//        return applicationUserLastName;
//    }
//
//    public void setApplicationUserLastName(String applicationUserLastName) {
//        this.applicationUserLastName = applicationUserLastName;
//    }
//
//    public String getApplicationUserEmail() {
//        return applicationUserEmail;
//    }
//
//    public void setApplicationUserEmail(String applicationUserEmail) {
//        this.applicationUserEmail = applicationUserEmail;
//    }
//
//    public String getApplicationUserPassword() {
//        return applicationUserPassword;
//    }
//
//    public void setApplicationUserPassword(String applicationUserPassword) {
//        this.applicationUserPassword = applicationUserPassword;
//    }
//
//    public List<UserResponse> getUserResponses() {
//        return userResponses;
//    }
//
//    public void setUserResponses(List<UserResponse> userResponses) {
//        this.userResponses = userResponses;
//    }
//
//    public List<Score> getUserScores() {
//        return userScores;
//    }
//
//    public void setUserScores(List<Score> userScores) {
//        this.userScores = userScores;
//    }
//
//    @Override
//    public String toString() {
//        return "ApplicationUser{" +
//                "applicationUserId=" + applicationUserId +
//                ", applicationUserFirstName='" + applicationUserFirstName + '\'' +
//                ", applicationUserLastName='" + applicationUserLastName + '\'' +
//                ", applicationUserEmail='" + applicationUserEmail + '\'' +
//                ", applicationUserPassword='" + applicationUserPassword + '\'' +
//                '}';
//    }
//}
