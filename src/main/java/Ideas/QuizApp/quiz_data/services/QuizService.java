package Ideas.QuizApp.quiz_data.services;

import Ideas.QuizApp.quiz_data.dto.quiz.QuizDTO;
import Ideas.QuizApp.quiz_data.dto.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.entity.Quiz;
import Ideas.QuizApp.quiz_data.entity.QuizTaken;
import Ideas.QuizApp.quiz_data.exception.QuizAlreadyExists;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.repository.ApplicationUserRepository;
import Ideas.QuizApp.quiz_data.repository.QuizRepository;
import Ideas.QuizApp.quiz_data.repository.QuizTakenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizTakenRepository quizTakenRepository;

    public QuizDTO createQuiz(QuizDTO quizDTO) {
        if (quizRepository.existsByQuizName(quizDTO.getQuizName())) {
            throw new QuizAlreadyExists();
        }

        Quiz quiz = new Quiz();
        quiz.setQuizNoOfQuestions(quizDTO.getQuizNoOfQuestions());
        quiz.setQuizTotalMarks(quizDTO.getQuizTotalMarks());
        quiz.setQuizTimeAllocated(quizDTO.getQuizTimeAllocated());
        quiz.setQuizName(quizDTO.getQuizName());
        quiz.setQuizImage(quizDTO.getQuizImage());

        Quiz savedQuiz = quizRepository.save(quiz);

        return toQuizDTO(savedQuiz);
    }

    public QuizDTO getQuizById(int quizId) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            return toQuizDTO(quiz);
        } else {
            throw new ResourceNotFound("Quiz by this ID not Found");
        }
    }

    public List<UserQuizDetailsDTO> getQuizzesByUser(Integer userId) {
        ApplicationUser user = new ApplicationUser();
        user.setApplicationUserId(userId);

        return quizTakenRepository.findByApplicationUser(user);
    }


    public List<QuizDTO> getQuizzesNotTakenByUser(Integer userId) {
        Optional<ApplicationUser> userOptional = applicationUserRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new ResourceNotFound("User with ID " + userId + " not found.");
        }

        List<Quiz> allQuizzes = (List<Quiz>) quizRepository.findAll();

        List<QuizTaken> quizzesTakenByUser = quizTakenRepository.findByApplicationUser_ApplicationUserId(userId);

        List<Integer> takenQuizIds = quizzesTakenByUser.stream()
                .map(quizAttempt -> quizAttempt.getQuiz().getQuizId())
                .collect(Collectors.toList());

        List<Quiz> quizzesNotTakenByUser = allQuizzes.stream()
                .filter(quiz -> !takenQuizIds.contains(quiz.getQuizId()))
                .collect(Collectors.toList());

        return quizzesNotTakenByUser.stream()
                .map(this::toQuizDTO)
                .collect(Collectors.toList());
    }

    public QuizDTO updateQuiz(Quiz quiz) {
        quiz.setQuizId(quiz.getQuizId());

        Optional<Quiz> existingQuiz = quizRepository.findById(quiz.getQuizId());
        if (existingQuiz.isPresent()) {
            return toQuizDTO(quizRepository.save(quiz));
        }
        throw new ResourceNotFound("Quiz Not Found");
    }

    private QuizDTO toQuizDTO(Quiz quiz) {
        return new QuizDTO(
                quiz.getQuizId(),
                quiz.getQuizNoOfQuestions(),
                quiz.getQuizTotalMarks(),
                quiz.getQuizTimeAllocated(),
                quiz.getQuizName(),
                quiz.getQuizImage()
        );
    }
}
