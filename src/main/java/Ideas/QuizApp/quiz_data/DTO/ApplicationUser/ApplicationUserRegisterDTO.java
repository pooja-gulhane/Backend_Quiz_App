package Ideas.QuizApp.quiz_data.DTO.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserRegisterDTO {
    private Integer applicationUserId;
    private String applicationUserFirstName;
    private String applicationUserLastName;
    private String applicationUserEmail;
    private String applicationUserPassword;
    private String applicationUserRole;
}
