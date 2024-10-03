package Ideas.QuizApp.quiz_data.controller;

import Ideas.QuizApp.quiz_data.dto.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.services.QuizTakenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/quiztaken")
public class QuizTakenController {

    @Autowired
    private QuizTakenService quizTakenService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<UserQuizDetailsDTO>> getUserQuizHistory(@PathVariable("id") int applicationUserId) {
        List<UserQuizDetailsDTO> userQuizHistory = quizTakenService.getUserQuizHistory(applicationUserId);
        return ResponseEntity.ok(userQuizHistory);
    }
}



