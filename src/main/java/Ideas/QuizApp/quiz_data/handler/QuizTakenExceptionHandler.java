package Ideas.QuizApp.quiz_data.handler;

import Ideas.QuizApp.quiz_data.controller.QuizTakenController;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = QuizTakenController.class)
public class QuizTakenExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<String> ResourceNotFound(ResourceNotFound resourceNotFound) {
        return new ResponseEntity<>(resourceNotFound.getMessage(), HttpStatus.CONFLICT);
    }
}
