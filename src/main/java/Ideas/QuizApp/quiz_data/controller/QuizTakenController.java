package Ideas.QuizApp.quiz_data.controller;

import Ideas.QuizApp.quiz_data.DTO.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.services.QuizTakenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class QuizTakenController {

    @Autowired
    private QuizTakenService quizTakenService;

    @GetMapping("/quiztaken/user/{id}")
    public List<UserQuizDetailsDTO> getUserQuizHistory(@PathVariable("id") int applicationUserId) {
        return quizTakenService.getUserQuizHistory(applicationUserId);
    }
}




