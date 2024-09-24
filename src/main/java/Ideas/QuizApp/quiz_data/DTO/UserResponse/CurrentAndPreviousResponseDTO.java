package Ideas.QuizApp.quiz_data.DTO.UserResponse;

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
