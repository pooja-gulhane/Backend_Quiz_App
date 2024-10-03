package Ideas.QuizApp.quiz_data.controller;

import Ideas.QuizApp.quiz_data.dto.userResponse.*;
import Ideas.QuizApp.quiz_data.dto.quiztaken.ScoreDTO;
import Ideas.QuizApp.quiz_data.services.UserResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/quiz")
public class UserResponseController {

    @Autowired
    private UserResponseService userResponseService;

    @PostMapping("/next")
    public ResponseEntity<CurrentResponseDTO> saveCurrentResponseAndGetNext(@RequestBody CurrentAndNextResponseDTO currentAndNextResponseDTO) {
        userResponseService.saveCurrentResponse(currentAndNextResponseDTO.getCurrentResponse());
        CurrentResponseDTO currentResponseDTO = userResponseService.getNextResponseIfPresent(currentAndNextResponseDTO.getNextResponse());
        return ResponseEntity.ok(currentResponseDTO);
    }

    @PostMapping("/previous")
    public ResponseEntity<CurrentResponseDTO> saveCurrentResponseAndGetPrevious(@RequestBody CurrentAndPreviousResponseDTO currentAndPreviousResponseDTO) {
        userResponseService.saveCurrentResponse(currentAndPreviousResponseDTO.getCurrentResponse());
        CurrentResponseDTO currentResponseDTO = userResponseService.getPreviousResponseIfPresent(currentAndPreviousResponseDTO.getPreviousResponse());
        return ResponseEntity.ok(currentResponseDTO);
    }

    @PostMapping("/userResponses")
    public ResponseEntity<ScoreDTO> submitUserResponses(@RequestBody CurrentAndNextResponseDTO currentAndNextResponseDTO) {
        ScoreDTO scoreDTO = userResponseService.submitUserResponses(currentAndNextResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(scoreDTO);
    }

    @GetMapping("/{quizId}/user/{userId}")
    public ResponseEntity<List<DisplayUserResponseDTO>> getUserResponsesByQuizAndUser(@PathVariable("quizId") Integer quizId, @PathVariable("userId") Integer userId) {
        List<DisplayUserResponseDTO> userResponses = userResponseService.getUserResponsesByQuizAndUser(quizId, userId);
        return ResponseEntity.ok(userResponses);
    }
}





