package Ideas.QuizApp.quiz_data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
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





