package Ideas.QuizApp.quiz_data.controller;

import Ideas.QuizApp.quiz_data.DTO.Quiz.QuizDTO;
import Ideas.QuizApp.quiz_data.DTO.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.entity.Quiz;
import Ideas.QuizApp.quiz_data.repository.QuizRepository;
import Ideas.QuizApp.quiz_data.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping()
    public QuizDTO createQuiz(@RequestBody QuizDTO quizDTO) {
        return quizService.createQuiz(quizDTO);
    }

    @GetMapping("{id}")
    public QuizDTO getQuizById(@PathVariable("id") int quizId) {
        return quizService.getQuizById(quizId);
    }

    @GetMapping("/not-taken/{userId}")
    public List<QuizDTO> getQuizzesNotTakenByUser(@PathVariable("userId") int userId) {
        return quizService.getQuizzesNotTakenByUser(userId);
    }

    @GetMapping("/user/{id}/quiz")
    public List<UserQuizDetailsDTO> getQuizByUser(@PathVariable("id") Integer userId) {
        return quizService.getQuizzesByUser(userId);
    }

    @PutMapping("/{id}")
    public QuizDTO updateQuiz(@PathVariable("id") int quizId, @RequestBody Quiz quiz) {
        return quizService.updateQuiz(quizId, quiz);
    }
}




