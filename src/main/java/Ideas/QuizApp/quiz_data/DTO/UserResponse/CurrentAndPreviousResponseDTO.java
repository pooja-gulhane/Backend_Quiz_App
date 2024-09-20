package Ideas.QuizApp.quiz_data.DTO.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAndPreviousResponseDTO {
    SubmitUserResponseDTO currentResponse;
    SubmitUserResponseDTO previousResponse;
}
