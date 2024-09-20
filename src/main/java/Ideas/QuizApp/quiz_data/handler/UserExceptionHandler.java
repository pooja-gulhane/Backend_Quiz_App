package Ideas.QuizApp.quiz_data.handler;

import Ideas.QuizApp.quiz_data.controller.UserResponseController;
import Ideas.QuizApp.quiz_data.exception.EmailAlreadyRegisteredException;
import Ideas.QuizApp.quiz_data.exception.InvalidCredentialsException;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.services.ApplicationUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes ={UserResponseController.class, ApplicationUserDetailsService.class})
@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<String> ResourceNotFound(ResourceNotFound resourceNotFound) {
        return new ResponseEntity<>(resourceNotFound.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyRegisteredException emailAlreadyRegisteredException){
        return new ResponseEntity<>("Email Already Exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException invalidCredentialsException){
        return new ResponseEntity<>("Invalid email or password", HttpStatus.CONFLICT);
    }
}
