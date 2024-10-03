package Ideas.QuizApp.quiz_data.dto.applicationUser;
import lombok.*;

@Builder
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
