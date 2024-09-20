package Ideas.QuizApp.quiz_data.controller;

import Ideas.QuizApp.quiz_data.DTO.Question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.DTO.Question.QuestionDTO;
import Ideas.QuizApp.quiz_data.entity.Question;
import Ideas.QuizApp.quiz_data.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/questions")
    public List<Question> addQuestions(@RequestBody List<Question> questions) {
        return questionService.addQuestions(questions);
    }

    @PutMapping("/questions/{id}")
    public QuestionDTO updateQuestion(@PathVariable("id") int questionId, @RequestBody Question updatedQuestion) {
        return questionService.updateQuestion(questionId, updatedQuestion);
    }

    @GetMapping("/questions/quiz/{quizId}/user/{userId}")
    public List<DisplayQuestionDTO> getQuestionsByQuizId(@PathVariable("quizId") int quizId, @PathVariable("userId") int userId) {
        return questionService.getQuestionsByQuizId(quizId, userId);
    }
}


