package Ideas.QuizApp.quiz_data.controller;

import Ideas.QuizApp.quiz_data.DTO.UserResponse.*;
import Ideas.QuizApp.quiz_data.DTO.quiztaken.ScoreDTO;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.services.UserResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class UserResponseController {

    @Autowired
    private UserResponseService userResponseService;

    @PostMapping("/quiz/next")
    public CurrentResponseDTO saveCurrentResponseAndGetNext(@RequestBody CurrentAndNextResponseDTO currentAndNextResponseDTO) {
        // Delegate the logic to the service class
        return userResponseService.saveCurrentResponseAndGetNext(currentAndNextResponseDTO);
    }

    @PostMapping("/quiz/previous")
    public CurrentResponseDTO saveCurrentResponseAndGetPrevious(@RequestBody CurrentAndPreviousResponseDTO currentAndPreviousResponseDTO) {
        // Delegate the logic to the service class
        return userResponseService.saveCurrentResponseAndGetPrevious(currentAndPreviousResponseDTO);
    }

    @PostMapping("/userResponses")
    public ScoreDTO submitUserResponses(@RequestBody CurrentAndNextResponseDTO  currentAndNextResponseDTO)
    {// Delegate the logic to the service class
        return userResponseService.submitUserResponses(currentAndNextResponseDTO);
    }

    @GetMapping("/quiz/{quizId}/user/{userId}")
    public List<DisplayUserResponseDTO> getUserResponsesByQuizAndUser(@PathVariable("quizId") Integer quizId, @PathVariable("userId") Integer userId) {
        // Delegate the logic to the service class
        return userResponseService.getUserResponsesByQuizAndUser(quizId, userId);
    }
}


