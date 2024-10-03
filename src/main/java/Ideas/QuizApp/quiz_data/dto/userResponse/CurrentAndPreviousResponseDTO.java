package Ideas.QuizApp.quiz_data.dto.userResponse;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAndPreviousResponseDTO {
    SubmitUserResponseDTO currentResponse;
    SubmitUserResponseDTO previousResponse;
}
