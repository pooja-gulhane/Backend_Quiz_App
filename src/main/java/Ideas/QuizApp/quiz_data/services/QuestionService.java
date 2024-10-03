package Ideas.QuizApp.quiz_data.services;

import Ideas.QuizApp.quiz_data.dto.question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.dto.question.QuestionDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import Ideas.QuizApp.quiz_data.entity.Question;
import Ideas.QuizApp.quiz_data.entity.Quiz;
import Ideas.QuizApp.quiz_data.entity.UserResponse;
import Ideas.QuizApp.quiz_data.exception.QuestionLimitExceededException;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.repository.QuestionRepository;
import Ideas.QuizApp.quiz_data.repository.QuizRepository;
import Ideas.QuizApp.quiz_data.repository.UserResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserResponseRepository userResponseRepository;

    @Autowired
    private QuizRepository quizRepository;

    public List<Question> addQuestions(List<Question> questions) {
        if (questions == null || questions.isEmpty()) {
            throw new IllegalArgumentException("No questions provided.");
        }

        Integer quizId = questions.get(0).getQuiz().getQuizId();
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);

        if (!quizOptional.isPresent()) {
            throw new ResourceNotFound("Quiz Not Found");
        }

        Quiz quiz = quizOptional.get();
        Integer currentQuestionCount = questionRepository.findCountByQuizId(quizId);

        if (quiz.getQuizNoOfQuestions() - currentQuestionCount >= questions.size()) {
            return (List<Question>) questionRepository.saveAll(questions);
        }
        throw new QuestionLimitExceededException();
    }

    public QuestionDTO updateQuestion(Question updatedQuestion) {
        Optional<Question> existingQuestionOptional = questionRepository.findById(updatedQuestion.getQuestionId());

        if (existingQuestionOptional.isPresent()) {
            Question existingQuestion = existingQuestionOptional.get();

            existingQuestion.setQuestionDescription(updatedQuestion.getQuestionDescription());
            existingQuestion.setQuestionOption1(updatedQuestion.getQuestionOption1());
            existingQuestion.setQuestionOption2(updatedQuestion.getQuestionOption2());
            existingQuestion.setQuestionOption3(updatedQuestion.getQuestionOption3());
            existingQuestion.setQuestionOption4(updatedQuestion.getQuestionOption4());
            existingQuestion.setQuestionCorrectAns(updatedQuestion.getQuestionCorrectAns());
            existingQuestion.setQuestionMarks(updatedQuestion.getQuestionMarks());

            return buildUpdateQuestionDTO(questionRepository.save(existingQuestion));
        }
        throw new ResourceNotFound("Question Not Found");
    }

    private QuestionDTO buildUpdateQuestionDTO(Question question) {
        return new QuestionDTO(
                question.getQuestionId(),
                question.getQuestionDescription(),
                question.getQuestionOption1(),
                question.getQuestionOption2(),
                question.getQuestionOption3(),
                question.getQuestionOption4(),
                question.getQuestionCorrectAns(),
                question.getQuestionMarks()
        );
    }

    public List<DisplayQuestionDTO> getQuestionsByQuizId(int quizId, int userId) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (quiz.isPresent()) {
            List<DisplayQuestionDTO> questions = questionRepository.findByQuiz(quizId);
            for(DisplayQuestionDTO questionDTO: questions) {
                Question question = new Question();
                question.setQuestionId(questionDTO.getQuestionId());
                ApplicationUser applicationUser = new ApplicationUser();
                applicationUser.setApplicationUserId(userId);
                Quiz setQuiz = new Quiz();
                setQuiz.setQuizId(quizId);

                boolean isResponsePresent = userResponseRepository.existsByQuizAndApplicationUserAndQuestion(setQuiz, applicationUser, question);
                if(!isResponsePresent) {
                    UserResponse userResponse = new UserResponse(0, question, applicationUser, "", 0, setQuiz, false);
                    userResponseRepository.save(userResponse);
                }
            }
            return questionRepository.findByQuiz(quizId);
        }
        throw new ResourceNotFound("Quiz Not Found");
    }
}

