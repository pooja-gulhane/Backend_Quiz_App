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

    public void saveCurrentResponse(SubmitUserResponseDTO currentResponse) {
        Question question = new Question();
        question.setQuestionId(currentResponse.getQuestion().getQuestionId());
        ApplicationUser user = new ApplicationUser();
        user.setApplicationUserId(currentResponse.getApplicationUser().getApplicationUserId());
        Quiz quiz = new Quiz();
        quiz.setQuizId(currentResponse.getQuiz().getQuizId());

        // Checking if response is given or not for that question.
        boolean ans = userResponseRepository.existsByQuizAndApplicationUserAndQuestion(quiz, user, question);

        if (ans) {
            CurrentResponseDTO currentResponseDTO = userResponseRepository.findByQuizAndApplicationUserAndQuestion(quiz, user, question);
            UserResponse userResponse = userResponseRepository.findById(currentResponseDTO.getUserResponseId())
                    .orElseThrow(() -> new RuntimeException("Response not found"));

            userResponse.setUserResponseAns(currentResponse.getUserResponseAns());
            userResponseRepository.save(userResponse);
        } else {
            UserResponse userResponse = new UserResponse();
            userResponse.setQuestion(question);
            userResponse.setApplicationUser(user);
            userResponse.setQuiz(quiz);
            userResponse.setUserResponseAns(currentResponse.getUserResponseAns());

            userResponseRepository.save(userResponse);
        }
    }

    public CurrentResponseDTO getNextResponseIfPresent(SubmitUserResponseDTO nextResponse) {
        ApplicationUser user = new ApplicationUser();
        user.setApplicationUserId(nextResponse.getApplicationUser().getApplicationUserId());
        Quiz quiz = new Quiz();
        quiz.setQuizId(nextResponse.getQuiz().getQuizId());
        Question nextQuestion = new Question();
        nextQuestion.setQuestionId(nextResponse.getQuestion().getQuestionId());

        boolean nextAns = userResponseRepository.existsByQuizAndApplicationUserAndQuestion(quiz, user, nextQuestion);

        if (nextAns) {
            return userResponseRepository.findByQuizAndApplicationUserAndQuestion(quiz, user, nextQuestion);
        }

        throw new ResourceNotFound("Response doesn't exist");
    }

    public CurrentResponseDTO getPreviousResponseIfPresent(SubmitUserResponseDTO previousResponse) {
        ApplicationUser user = new ApplicationUser();
        user.setApplicationUserId(previousResponse.getApplicationUser().getApplicationUserId());
        Quiz quiz = new Quiz();
        quiz.setQuizId(previousResponse.getQuiz().getQuizId());
        Question previousQuestion = new Question();
        previousQuestion.setQuestionId(previousResponse.getQuestion().getQuestionId());

        boolean previousAns = userResponseRepository.existsByQuizAndApplicationUserAndQuestion(quiz, user, previousQuestion);

        if (previousAns) {
            return userResponseRepository.findByQuizAndApplicationUserAndQuestion(quiz, user, previousQuestion);
        }

        throw new ResourceNotFound("Response doesn't exist");
    }

    public ScoreDTO submitUserResponses(CurrentAndNextResponseDTO currentAndNextResponseDTO) {
        saveCurrentResponse(currentAndNextResponseDTO.getCurrentResponse());
        getNextResponseIfPresent(currentAndNextResponseDTO.getNextResponse());

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

