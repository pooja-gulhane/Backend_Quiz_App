package Ideas.QuizApp.quiz_data.services;

import Ideas.QuizApp.quiz_data.DTO.Quiz.QuizDTO;
import Ideas.QuizApp.quiz_data.DTO.quiztaken.UserQuizDetailsDTO;
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

    // Service method to create a quiz
    public QuizDTO createQuiz(QuizDTO quizDTO) {
        // Check if a quiz with the same name already exists
        if (quizRepository.existsByQuizName(quizDTO.getQuizName())) {
            throw new QuizAlreadyExists();
        }

        // Convert QuizDTO to Quiz entity
        Quiz quiz = new Quiz();
        quiz.setQuizNoOfQuestions(quizDTO.getQuizNoOfQuestions());
        quiz.setQuizTotalMarks(quizDTO.getQuizTotalMarks());
        quiz.setQuizTimeAllocated(quizDTO.getQuizTimeAllocated());
        quiz.setQuizName(quizDTO.getQuizName());
        quiz.setQuizImage(quizDTO.getQuizImage());

        // Save the quiz entity
        Quiz savedQuiz = quizRepository.save(quiz);

        // Convert the saved Quiz entity back to QuizDTO
        return toQuizDTO(savedQuiz);
    }

    // Service method to retrieve a quiz by its ID
    public QuizDTO getQuizById(int quizId) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            return toQuizDTO(quiz);
        } else {
            throw new ResourceNotFound("Quiz by this ID not Found");
        }
    }

    // Service method to retrieve quizzes taken by a specific user
    public List<UserQuizDetailsDTO> getQuizzesByUser(Integer userId) {
        ApplicationUser user = new ApplicationUser();
        user.setApplicationUserId(userId);

        return quizTakenRepository.findByApplicationUser(user);
    }


    public List<QuizDTO> getQuizzesNotTakenByUser(Integer userId) {
        // Fetch the user by userId
        Optional<ApplicationUser> userOptional = applicationUserRepository.findById(userId);

        // If the user is not found, throw a custom exception
        if (userOptional.isEmpty()) {
            throw new ResourceNotFound("User with ID " + userId + " not found.");
        }

        // Fetch all quizzes
        List<Quiz> allQuizzes = (List<Quiz>) quizRepository.findAll();

        // Fetch quizzes taken by the user
        List<QuizTaken> quizzesTakenByUser = quizTakenRepository.findByApplicationUser_ApplicationUserId(userId);

        // Extract the quiz IDs that the user has already taken
        List<Integer> takenQuizIds = quizzesTakenByUser.stream()
                .map(quizAttempt -> quizAttempt.getQuiz().getQuizId())
                .collect(Collectors.toList());

        // Filter out quizzes already taken by the user
        List<Quiz> quizzesNotTakenByUser = allQuizzes.stream()
                .filter(quiz -> !takenQuizIds.contains(quiz.getQuizId()))
                .collect(Collectors.toList());

        // Convert List<Quiz> to List<QuizDTO> (assuming you have a method to convert)
        return quizzesNotTakenByUser.stream()
                .map(this::toQuizDTO)
                .collect(Collectors.toList());
    }



    public QuizDTO updateQuiz(int quizId, Quiz quiz) {
        // Set the quizId to ensure we are updating the right quiz
        quiz.setQuizId(quizId);

        // Check if the quiz exists before updating
        Optional<Quiz> existingQuiz = quizRepository.findById(quizId);
        if (existingQuiz.isPresent()) {
            return toQuizDTO(quizRepository.save(quiz));
        }
        throw new ResourceNotFound("Quiz Not Found");
    }

    // Helper method to convert Quiz Entity to QuizDTO
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
