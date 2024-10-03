package Ideas.QuizApp.quiz_data.services;

import Ideas.QuizApp.quiz_data.dto.applicationUser.ApplicationUserLoginDTO;
import Ideas.QuizApp.quiz_data.dto.applicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.dto.AuthenticationResponse;
import Ideas.QuizApp.quiz_data.repository.ApplicationUserRepository;
import Ideas.QuizApp.quiz_data.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;


@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;
    @Autowired
    private ApplicationUserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationResponse authenticate(ApplicationUserLoginDTO loginRequest) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserEmail(), loginRequest.getUserPassword())
        );

        UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(loginRequest.getUserEmail());
        UserDTO userDTO = userRepository.findByApplicationUserEmail(loginRequest.getUserEmail());
        String jwt = jwtUtil.generateToken(userDetails);

        return new AuthenticationResponse(userDTO, jwt);
    }
}
