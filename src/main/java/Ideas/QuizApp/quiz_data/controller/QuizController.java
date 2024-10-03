package Ideas.QuizApp.quiz_data.controller;

import Ideas.QuizApp.quiz_data.dto.quiz.QuizDTO;
import Ideas.QuizApp.quiz_data.dto.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.entity.Quiz;
import Ideas.QuizApp.quiz_data.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quizDTO) {
        QuizDTO createdQuiz = quizService.createQuiz(quizDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable("id") int quizId) {
        QuizDTO quizDTO = quizService.getQuizById(quizId);
        return ResponseEntity.ok(quizDTO);
    }

    @GetMapping("/not-taken/{userId}")
    public ResponseEntity<List<QuizDTO>> getQuizzesNotTakenByUser(@PathVariable("userId") int userId) {
        List<QuizDTO> quizzes = quizService.getQuizzesNotTakenByUser(userId);
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/user/{id}/quiz")
    public ResponseEntity<List<UserQuizDetailsDTO>> getQuizByUser(@PathVariable("id") Integer userId) {
        List<UserQuizDetailsDTO> quizzes = quizService.getQuizzesByUser(userId);
        return ResponseEntity.ok(quizzes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDTO> updateQuiz( @RequestBody Quiz quiz) {
        QuizDTO updatedQuiz = quizService.updateQuiz( quiz);
        return ResponseEntity.ok(updatedQuiz);
    }
}



