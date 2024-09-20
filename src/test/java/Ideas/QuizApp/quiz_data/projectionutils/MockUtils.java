package Ideas.QuizApp.quiz_data.projectionutils;

import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.DTO.Question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.DTO.Quiz.QuizProjection;
import Ideas.QuizApp.quiz_data.DTO.UserResponse.CurrentResponseDTO;
import Ideas.QuizApp.quiz_data.DTO.UserResponse.DisplayUserResponseDTO;
import Ideas.QuizApp.quiz_data.DTO.quiztaken.QuestionResponseProjection;
import Ideas.QuizApp.quiz_data.DTO.quiztaken.UserQuizDetailsDTO;
import org.mockito.Mockito;

public class MockUtils {
    public static CurrentResponseDTO mockCurrentResponseProjection(Integer userResponseId, String userResponseAns, Integer questionId, String questionDescription, String questionOption1, String questionOption2, String questionOption3, String questionOption4, Integer applicationUserId, String applicationUserEmail, String applicationUserPassword, String applicationUserRole, Integer quizId, Integer quizNoOfQuestions, Integer quizTotalMarks, Integer quizTimeAllocated, String quizName) {
        CurrentResponseDTO mockedCurrentResponseDTO = Mockito.mock(CurrentResponseDTO.class);
        DisplayQuestionDTO mockedDisplayQuestionDTO = Mockito.mock(DisplayQuestionDTO.class);
        UserDTO mockedUserDTO = Mockito.mock(UserDTO.class);
        QuizProjection mockedQuizProjection = Mockito.mock(QuizProjection.class);

        Mockito.lenient().when(mockedQuizProjection.getQuizId()).thenReturn(quizId);
        Mockito.lenient().when(mockedQuizProjection.getQuizNoOfQuestions()).thenReturn(quizNoOfQuestions);
        Mockito.lenient().when(mockedQuizProjection.getQuizTotalMarks()).thenReturn(quizTotalMarks);
        Mockito.lenient().when(mockedQuizProjection.getQuizTimeAllocated()).thenReturn(quizTotalMarks);
        Mockito.lenient().when(mockedQuizProjection.getQuizName()).thenReturn(quizName);

        Mockito.lenient().when(mockedUserDTO.getApplicationUserId()).thenReturn(applicationUserId);
        Mockito.lenient().when(mockedUserDTO.getApplicationUserEmail()).thenReturn(applicationUserEmail);
        Mockito.lenient().when(mockedUserDTO.getApplicationUserPassword()).thenReturn(applicationUserPassword);
        Mockito.lenient().when(mockedUserDTO.getApplicationUserrole()).thenReturn(applicationUserRole);

        Mockito.lenient().when(mockedDisplayQuestionDTO.getQuestionId()).thenReturn(questionId);
        Mockito.lenient().when(mockedDisplayQuestionDTO.getQuestionDescription()).thenReturn(questionDescription);
        Mockito.lenient().when(mockedDisplayQuestionDTO.getQuestionOption1()).thenReturn(questionOption1);
        Mockito.lenient().when(mockedDisplayQuestionDTO.getQuestionOption2()).thenReturn(questionOption2);
        Mockito.lenient().when(mockedDisplayQuestionDTO.getQuestionOption3()).thenReturn(questionOption3);
        Mockito.lenient().when(mockedDisplayQuestionDTO.getQuestionOption4()).thenReturn(questionOption4);

        Mockito.lenient().when(mockedCurrentResponseDTO.getUserResponseId()).thenReturn(userResponseId);
        Mockito.lenient().when(mockedCurrentResponseDTO.getUserResponseAns()).thenReturn(userResponseAns);
        Mockito.lenient().when(mockedCurrentResponseDTO.getQuestion()).thenReturn(mockedDisplayQuestionDTO);
        Mockito.lenient().when(mockedCurrentResponseDTO.getQuiz()).thenReturn(mockedQuizProjection);
        Mockito.lenient().when(mockedCurrentResponseDTO.getApplicationUser()).thenReturn(mockedUserDTO);

        return mockedCurrentResponseDTO;
    }

    public static QuestionResponseProjection mockQuestionResponseProjection(
            String userResponseAns,
            Integer userResponseId,
            Boolean isCorrect,
            Integer questionId,
            String questionCorrectAns,
            Integer questionMarks) {

        QuestionResponseProjection mockedQuestionResponseProjection = Mockito.mock(QuestionResponseProjection.class);
        QuestionResponseProjection.QuestionProjection mockedQuestionProjection = Mockito.mock(QuestionResponseProjection.QuestionProjection.class);

        // Mock the methods of QuestionProjection interface
        Mockito.when(mockedQuestionProjection.getQuestionId()).thenReturn(questionId);
        Mockito.when(mockedQuestionProjection.getQuestionCorrectAns()).thenReturn(questionCorrectAns);
        Mockito.when(mockedQuestionProjection.getQuestionMarks()).thenReturn(questionMarks);

        // Mock the methods of QuestionResponseProjection interface
        Mockito.when(mockedQuestionResponseProjection.getUserResponseAns()).thenReturn(userResponseAns);
        Mockito.when(mockedQuestionResponseProjection.getUserResponseId()).thenReturn(userResponseId);
        Mockito.when(mockedQuestionResponseProjection.getIsCorrect()).thenReturn(isCorrect);
        Mockito.when(mockedQuestionResponseProjection.getQuestion()).thenReturn(mockedQuestionProjection);

        return mockedQuestionResponseProjection;
    }

    public static DisplayUserResponseDTO mockDisplayUserResponseDTO(
            String userResponseAns,
            Boolean isCorrect,
            String questionDescription,
            String questionOption1,
            String questionOption2,
            String questionOption3,
            String questionOption4,
            String questionCorrectAns) {

        // Mocking DisplayUserResponseDTO
        DisplayUserResponseDTO mockedDisplayUserResponseDTO = Mockito.mock(DisplayUserResponseDTO.class);

        // Mocking nested QuestionDTO interface
        DisplayUserResponseDTO.QuestionDTO mockedQuestionDTO = Mockito.mock(DisplayUserResponseDTO.QuestionDTO.class);

        // Mock methods for QuestionDTO
        Mockito.lenient().when(mockedQuestionDTO.getQuestionDescription()).thenReturn(questionDescription);
        Mockito.lenient().when(mockedQuestionDTO.getQuestionOption1()).thenReturn(questionOption1);
        Mockito.lenient().when(mockedQuestionDTO.getQuestionOption2()).thenReturn(questionOption2);
        Mockito.lenient().when(mockedQuestionDTO.getQuestionOption3()).thenReturn(questionOption3);
        Mockito.lenient().when(mockedQuestionDTO.getQuestionOption4()).thenReturn(questionOption4);
        Mockito.lenient().when(mockedQuestionDTO.getQuestionCorrectAns()).thenReturn(questionCorrectAns);

        // Mock methods for DisplayUserResponseDTO
        Mockito.lenient().when(mockedDisplayUserResponseDTO.getUserResponseAns()).thenReturn(userResponseAns);
        Mockito.lenient().when(mockedDisplayUserResponseDTO.getIsCorrect()).thenReturn(isCorrect);
        Mockito.lenient().when(mockedDisplayUserResponseDTO.getQuestion()).thenReturn(mockedQuestionDTO);

        return mockedDisplayUserResponseDTO;
    }

    // Mock method for UserQuizDetailsDTO
    public static UserQuizDetailsDTO mockUserQuizDetailsDTO(
            Integer scoreValue,
            Integer quizId,
            String quizName,
            Integer userId,
            String firstName,
            String lastName) {

        // Mock UserQuizDetailsDTO
        UserQuizDetailsDTO mockedUserQuizDetailsDTO = Mockito.mock(UserQuizDetailsDTO.class);

        // Mock QuizProjection interface
        UserQuizDetailsDTO.QuizProjection mockedQuizProjection = Mockito.mock(UserQuizDetailsDTO.QuizProjection.class);
        Mockito.when(mockedQuizProjection.getQuizId()).thenReturn(quizId);
        Mockito.when(mockedQuizProjection.getQuizName()).thenReturn(quizName);

        // Mock UserProjection interface
        UserQuizDetailsDTO.UserProjection mockedUserProjection = Mockito.mock(UserQuizDetailsDTO.UserProjection.class);
        Mockito.when(mockedUserProjection.getApplicationUserId()).thenReturn(userId);
        Mockito.when(mockedUserProjection.getApplicationUserFirstName()).thenReturn(firstName);
        Mockito.when(mockedUserProjection.getApplicationUserLastName()).thenReturn(lastName);

        // Set up the mock for UserQuizDetailsDTO
        Mockito.when(mockedUserQuizDetailsDTO.getScoreValue()).thenReturn(scoreValue);
        Mockito.lenient().when(mockedUserQuizDetailsDTO.getQuiz()).thenReturn(mockedQuizProjection);
        Mockito.lenient().when(mockedUserQuizDetailsDTO.getApplicationUser()).thenReturn(mockedUserProjection);

        return mockedUserQuizDetailsDTO;
    }
}
