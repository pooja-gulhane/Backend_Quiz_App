package Ideas.QuizApp.quiz_data.dto;

import Ideas.QuizApp.quiz_data.dto.applicationUser.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private  UserDTO userDTO;
    private  String jwt;
}