package Ideas.QuizApp.quiz_data.controller;

import Ideas.QuizApp.quiz_data.dto.question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.dto.question.QuestionDTO;
import Ideas.QuizApp.quiz_data.entity.Question;
import Ideas.QuizApp.quiz_data.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<List<Question>> addQuestions(@RequestBody List<Question> questions) {
        List<Question> createdQuestions = questionService.addQuestions(questions);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestions);
    }

    @PutMapping()
    public ResponseEntity<QuestionDTO> updateQuestion(@RequestBody Question updatedQuestion) {
        QuestionDTO updatedQuestionDTO = questionService.updateQuestion(updatedQuestion);
        return ResponseEntity.ok(updatedQuestionDTO);
    }

    @GetMapping("/quiz/{quizId}/user/{userId}")
    public ResponseEntity<List<DisplayQuestionDTO>> getQuestionsByQuizId(@PathVariable("quizId") int quizId, @PathVariable("userId") int userId) {
        List<DisplayQuestionDTO> questions = questionService.getQuestionsByQuizId(quizId, userId);
        return ResponseEntity.ok(questions);
    }
}

