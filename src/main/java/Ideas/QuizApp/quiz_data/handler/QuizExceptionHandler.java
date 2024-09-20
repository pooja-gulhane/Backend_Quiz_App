package Ideas.QuizApp.quiz_data.handler;

import Ideas.QuizApp.quiz_data.controller.QuestionController;
import Ideas.QuizApp.quiz_data.controller.QuizController;
import Ideas.QuizApp.quiz_data.exception.QuizAlreadyExists;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = QuizController.class)
@ControllerAdvice
public class QuizExceptionHandler {
    @ExceptionHandler(QuizAlreadyExists.class)
    public ResponseEntity<String> handleQuizAlreadyExist(QuizAlreadyExists quizAlreadyExists){
        return new ResponseEntity<>("Quiz By This Name Already Exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<String> ResourceNotFound(ResourceNotFound resourceNotFound) {
        return new ResponseEntity<>(resourceNotFound.getMessage(), HttpStatus.CONFLICT);
    }
}
