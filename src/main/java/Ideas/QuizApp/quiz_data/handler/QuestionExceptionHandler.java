package Ideas.QuizApp.quiz_data.handler;

import Ideas.QuizApp.quiz_data.controller.QuestionController;
import Ideas.QuizApp.quiz_data.exception.QuestionLimitExceededException;
import Ideas.QuizApp.quiz_data.exception.QuizAlreadyExists;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = QuestionController.class)
@ControllerAdvice
public class QuestionExceptionHandler {
    @ExceptionHandler(QuestionLimitExceededException.class)
    public ResponseEntity<String> handleQuestionLimitExceeded(QuestionLimitExceededException questionLimitExceededException){
        return new ResponseEntity<>("Cannot add more questions to this quiz.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<String> ResourceNotFound(ResourceNotFound resourceNotFound){
        return new ResponseEntity<>(resourceNotFound.getMessage(), HttpStatus.CONFLICT);
    }
}
