package Ideas.QuizApp.quiz_data.services;

import Ideas.QuizApp.quiz_data.DTO.UserResponse.*;
import Ideas.QuizApp.quiz_data.DTO.quiztaken.QuestionResponseProjection;
import Ideas.QuizApp.quiz_data.DTO.quiztaken.ScoreDTO;
import Ideas.QuizApp.quiz_data.entity.*;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserResponseService {

    @Autowired
    UserResponseRepository userResponseRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuizTakenRepository quizTakenRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    public CurrentResponseDTO saveCurrentResponseAndGetNext(CurrentAndNextResponseDTO currentAndNextResponseDTO) {
        // Now taking question, user, and quiz of current response.
        Question question = new Question();
        question.setQuestionId(currentAndNextResponseDTO.getCurrentResponse().getQuestion().getQuestionId());
        ApplicationUser user = new ApplicationUser();
        user.setApplicationUserId(currentAndNextResponseDTO.getCurrentResponse().getApplicationUser().getApplicationUserId());
        Quiz quiz = new Quiz();
        quiz.setQuizId(currentAndNextResponseDTO.getCurrentResponse().getQuiz().getQuizId());

        // Checking if response is given or not for that question.
        boolean ans = userResponseRepository.existsByQuizAndApplicationUserAndQuestion(quiz, user, question);

        if (ans) {
            // Response is present in table, so updating the response.
            CurrentResponseDTO currentResponseDTO = userResponseRepository.findByQuizAndApplicationUserAndQuestion(quiz, user, question);
            UserResponse userResponse = userResponseRepository.findById(currentResponseDTO.getUserResponseId())
                    .orElseThrow(() -> new RuntimeException("Response not found"));

            userResponse.setUserResponseAns(currentAndNextResponseDTO.getCurrentResponse().getUserResponseAns());
            userResponseRepository.save(userResponse);
        } else {
            // Inserting the response, as the response was not present.
            UserResponse userResponse = new UserResponse();
            userResponse.setQuestion(question);
            userResponse.setApplicationUser(user);
            userResponse.setQuiz(quiz);
            userResponse.setUserResponseAns(currentAndNextResponseDTO.getCurrentResponse().getUserResponseAns());

            userResponseRepository.save(userResponse);
        }

        // Processing next question.
        Question nextQuestion = new Question();
        nextQuestion.setQuestionId(currentAndNextResponseDTO.getNextResponse().getQuestion().getQuestionId());

        // Checking if response for next question is present or not.
        boolean nextAns = userResponseRepository.existsByQuizAndApplicationUserAndQuestion(quiz, user, nextQuestion);

        if (nextAns) {
            // Sending the response of the next question to UI.
            return userResponseRepository.findByQuizAndApplicationUserAndQuestion(quiz, user, nextQuestion);
        }

        throw new ResourceNotFound("Response doesn't exist");
    }

    public CurrentResponseDTO saveCurrentResponseAndGetPrevious(CurrentAndPreviousResponseDTO currentAndPreviousResponseDTO) {
        // Now taking question, user, and quiz of current response.
        Question question = new Question();
        question.setQuestionId(currentAndPreviousResponseDTO.getCurrentResponse().getQuestion().getQuestionId());
        ApplicationUser user = new ApplicationUser();
        user.setApplicationUserId(currentAndPreviousResponseDTO.getCurrentResponse().getApplicationUser().getApplicationUserId());
        Quiz quiz = new Quiz();
        quiz.setQuizId(currentAndPreviousResponseDTO.getCurrentResponse().getQuiz().getQuizId());

        // Checking if response is given or not for that question.
        boolean ans = userResponseRepository.existsByQuizAndApplicationUserAndQuestion(quiz, user, question);

        if (ans) {
            // Response is present in table, so updating the response.
            CurrentResponseDTO currentResponseDTO = userResponseRepository.findByQuizAndApplicationUserAndQuestion(quiz, user, question);
            UserResponse userResponse = userResponseRepository.findById(currentResponseDTO.getUserResponseId())
                    .orElseThrow(() -> new RuntimeException("Response not found"));

            userResponse.setUserResponseAns(currentAndPreviousResponseDTO.getCurrentResponse().getUserResponseAns());
            userResponseRepository.save(userResponse);
        } else {
            // Inserting the response, as the response was not present.
            UserResponse userResponse = new UserResponse();
            userResponse.setQuestion(question);
            userResponse.setApplicationUser(user);
            userResponse.setQuiz(quiz);
            userResponse.setUserResponseAns(currentAndPreviousResponseDTO.getCurrentResponse().getUserResponseAns());

            userResponseRepository.save(userResponse);
        }

        // Processing the previous question.
        Question previousQuestion = new Question();
        previousQuestion.setQuestionId(currentAndPreviousResponseDTO.getPreviousResponse().getQuestion().getQuestionId());

        // Checking if the response for the previous question is present or not.
        boolean previousAns = userResponseRepository.existsByQuizAndApplicationUserAndQuestion(quiz, user, previousQuestion);

        if (previousAns) {
            // Sending the response of the previous question to UI.
            CurrentResponseDTO currentResponseDTO =  userResponseRepository.findByQuizAndApplicationUserAndQuestion(quiz, user, previousQuestion);
            System.out.println("***********************************************************************");
            System.out.println(currentResponseDTO.getUserResponseAns());
            return currentResponseDTO;
        }

        throw new ResourceNotFound("Response doesn't exist");
    }

    public ScoreDTO submitUserResponses(CurrentAndNextResponseDTO currentAndNextResponseDTO) {

        saveCurrentResponseAndGetNext(currentAndNextResponseDTO);
        ApplicationUser user = new ApplicationUser();
        user.setApplicationUserId(currentAndNextResponseDTO.getCurrentResponse().getApplicationUser().getApplicationUserId());
        Quiz quiz = new Quiz();
        quiz.setQuizId(currentAndNextResponseDTO.getCurrentResponse().getQuiz().getQuizId());


        int score = 0;
        List<QuestionResponseProjection> questionResponseProjections = userResponseRepository.findByApplicationUserAndQuiz(user, quiz);
        for (QuestionResponseProjection questionResponseProjection : questionResponseProjections) {
            UserResponse userResponse = userResponseRepository.findById(questionResponseProjection.getUserResponseId())
                    .orElseThrow(() -> new RuntimeException("Response not existed"));
            userResponse.setIsCorrect(questionResponseProjection.getUserResponseAns().equals(questionResponseProjection.getQuestion().getQuestionCorrectAns()));
            userResponse.setUserResponseDateTime(LocalDateTime.now());

            userResponseRepository.save(userResponse);
            score += userResponse.getIsCorrect() ? questionResponseProjection.getQuestion().getQuestionMarks() : 0;
        }

        QuizTaken quizTaken = new QuizTaken();
        quizTaken.setApplicationUser(user);
        quizTaken.setQuiz(quiz);
        quizTaken.setScoreValue(score);
        quizTaken.setQuizTakenDate(LocalDateTime.now());
        quizTakenRepository.save(quizTaken);

        return new ScoreDTO(score);
    }


    public List<DisplayUserResponseDTO> getUserResponsesByQuizAndUser(Integer quizId, Integer userId) {
        // Check if Quiz exists
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isEmpty()) {
            throw new ResourceNotFound("Quiz Not Found");
        }

        // Check if User exists
        Optional<ApplicationUser> userOptional = applicationUserRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFound("User Not Found");
        }

        // Fetch user responses if both Quiz and User exist
        return userResponseRepository.findByQuizQuizIdAndApplicationUserApplicationUserId(quizId, userId);
    }
}

