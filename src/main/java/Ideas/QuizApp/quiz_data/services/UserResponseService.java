package Ideas.QuizApp.quiz_data.services;

import Ideas.QuizApp.quiz_data.dto.userResponse.*;
import Ideas.QuizApp.quiz_data.dto.quiztaken.QuestionResponseProjection;
import Ideas.QuizApp.quiz_data.dto.quiztaken.ScoreDTO;
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

        boolean ans = userResponseRepository.existsByQuizAndApplicationUserAndQuestion(quiz, user, question);

        if (ans) {
            CurrentResponseDTO currentResponseDTO = userResponseRepository.findByQuizAndApplicationUserAndQuestion(quiz, user, question);
            UserResponse userResponse = userResponseRepository.findById(currentResponseDTO.getUserResponseId())
                    .orElseThrow(() -> new RuntimeException("Response not found"));

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

        ApplicationUser user = extractUser(currentAndNextResponseDTO);
        Quiz quiz = extractQuiz(currentAndNextResponseDTO);

        int score = calculateScore(user, quiz);
        saveQuizTaken(user, quiz, score);

        return new ScoreDTO(score);
    }

    private ApplicationUser extractUser(CurrentAndNextResponseDTO currentAndNextResponseDTO) {
        ApplicationUser user = new ApplicationUser();
        user.setApplicationUserId(currentAndNextResponseDTO.getCurrentResponse().getApplicationUser().getApplicationUserId());
        return user;
    }

    private Quiz extractQuiz(CurrentAndNextResponseDTO currentAndNextResponseDTO) {
        Quiz quiz = new Quiz();
        quiz.setQuizId(currentAndNextResponseDTO.getCurrentResponse().getQuiz().getQuizId());
        return quiz;
    }

    private int calculateScore(ApplicationUser user, Quiz quiz) {
        int score = 0;
        List<QuestionResponseProjection> questionResponseProjections = userResponseRepository.findByApplicationUserAndQuiz(user, quiz);

        for (QuestionResponseProjection questionResponseProjection : questionResponseProjections) {
            score += evaluateUserResponse(questionResponseProjection);
        }
        return score;
    }

    private int evaluateUserResponse(QuestionResponseProjection questionResponseProjection) {
        UserResponse userResponse = userResponseRepository.findById(questionResponseProjection.getUserResponseId())
                .orElseThrow(() -> new RuntimeException("Response not existed"));

        boolean isCorrect = questionResponseProjection.getUserResponseAns().equals(questionResponseProjection.getQuestion().getQuestionCorrectAns());
        userResponse.setIsCorrect(isCorrect);
        userResponse.setUserResponseDateTime(LocalDateTime.now());

        userResponseRepository.save(userResponse);

        return isCorrect ? questionResponseProjection.getQuestion().getQuestionMarks() : 0;
    }

    private void saveQuizTaken(ApplicationUser user, Quiz quiz, int score) {
        QuizTaken quizTaken = new QuizTaken();
        quizTaken.setApplicationUser(user);
        quizTaken.setQuiz(quiz);
        quizTaken.setScoreValue(score);
        quizTaken.setQuizTakenDate(LocalDateTime.now());
        quizTakenRepository.save(quizTaken);
    }



    public List<DisplayUserResponseDTO> getUserResponsesByQuizAndUser(Integer quizId, Integer userId) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isEmpty()) {
            throw new ResourceNotFound("Quiz Not Found");
        }

        Optional<ApplicationUser> userOptional = applicationUserRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFound("User Not Found");
        }

        return userResponseRepository.findByQuizQuizIdAndApplicationUserApplicationUserId(quizId, userId);
    }
}

