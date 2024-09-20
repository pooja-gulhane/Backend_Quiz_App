package Ideas.QuizApp.quiz_data.services;

import Ideas.QuizApp.quiz_data.DTO.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.repository.ApplicationUserRepository;
import Ideas.QuizApp.quiz_data.repository.QuizTakenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizTakenService {

    @Autowired
    private QuizTakenRepository quizTakenRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    public List<UserQuizDetailsDTO> getUserQuizHistory(int applicationUserId) {
        // Check if the user exists
        Optional<ApplicationUser> userOptional = applicationUserRepository.findById(applicationUserId);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFound("User Not Found");
        }

        ApplicationUser user = userOptional.get();
        List<UserQuizDetailsDTO> userQuizDetailsDTO =  quizTakenRepository.findByApplicationUser(user);
        // Fetch the quiz history for the user
        return userQuizDetailsDTO;
    }
}
