package Ideas.QuizApp.quiz_data.dto.applicationUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserLoginDTO {
    private String userEmail;
    private String userPassword;
}
