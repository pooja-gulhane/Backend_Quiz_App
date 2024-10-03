package Ideas.QuizApp.quiz_data.serviceTest.projectionutils;

import Ideas.QuizApp.quiz_data.dto.applicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.dto.question.DisplayQuestionDTO;
import Ideas.QuizApp.quiz_data.dto.quiz.QuizProjection;
import Ideas.QuizApp.quiz_data.dto.userResponse.CurrentResponseDTO;
import Ideas.QuizApp.quiz_data.dto.userResponse.DisplayUserResponseDTO;
import Ideas.QuizApp.quiz_data.dto.quiztaken.QuestionResponseProjection;
import Ideas.QuizApp.quiz_data.dto.quiztaken.UserQuizDetailsDTO;
import Ideas.QuizApp.quiz_data.entity.Question;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class MockUtils {
    public static CurrentResponseDTO mockCurrentResponseProjection(Integer userResponseId, String userResponseAns, Integer questionId, String questionDescription, String questionOption1, String questionOption2, String questionOption3, String questionOption4, Integer applicationUserId, String applicationUserEmail, String applicationUserPassword, String applicationUserRole, Integer quizId, Integer quizNoOfQuestions, Integer quizTotalMarks, Integer quizTimeAllocated, String quizName) {
        CurrentResponseDTO mockedCurrentResponseDTO = mock(CurrentResponseDTO.class);
        DisplayQuestionDTO mockedDisplayQuestionDTO = mock(DisplayQuestionDTO.class);
        UserDTO mockedUserDTO = mock(UserDTO.class);
        QuizProjection mockedQuizProjection = mock(QuizProjection.class);

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

        QuestionResponseProjection mockedQuestionResponseProjection = mock(QuestionResponseProjection.class);
        QuestionResponseProjection.QuestionProjection mockedQuestionProjection = mock(QuestionResponseProjection.QuestionProjection.class);

        // Mock the methods of QuestionProjection interface
        Mockito.lenient().when(mockedQuestionProjection.getQuestionId()).thenReturn(questionId);
        Mockito.lenient().when(mockedQuestionProjection.getQuestionCorrectAns()).thenReturn(questionCorrectAns);
        Mockito.lenient().when(mockedQuestionProjection.getQuestionMarks()).thenReturn(questionMarks);

        // Mock the methods of QuestionResponseProjection interface
        Mockito.lenient().when(mockedQuestionResponseProjection.getUserResponseAns()).thenReturn(userResponseAns);
        Mockito.lenient().when(mockedQuestionResponseProjection.getUserResponseId()).thenReturn(userResponseId);
        Mockito.lenient().when(mockedQuestionResponseProjection.getIsCorrect()).thenReturn(isCorrect);
        Mockito.lenient().when(mockedQuestionResponseProjection.getQuestion()).thenReturn(mockedQuestionProjection);

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
        DisplayUserResponseDTO mockedDisplayUserResponseDTO = mock(DisplayUserResponseDTO.class);

        // Mocking nested QuestionDTO interface
        DisplayUserResponseDTO.QuestionDTO mockedQuestionDTO = mock(DisplayUserResponseDTO.QuestionDTO.class);

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
        UserQuizDetailsDTO mockedUserQuizDetailsDTO = mock(UserQuizDetailsDTO.class);

        // Mock QuizProjection interface
        QuizProjection mockedQuizProjection = mock(QuizProjection.class);
        Mockito.lenient().when(mockedQuizProjection.getQuizId()).thenReturn(quizId);
        Mockito.lenient().when(mockedQuizProjection.getQuizName()).thenReturn(quizName);

        // Mock UserProjection interface
        UserQuizDetailsDTO.UserProjection mockedUserProjection = mock(UserQuizDetailsDTO.UserProjection.class);
        Mockito.lenient().when(mockedUserProjection.getApplicationUserId()).thenReturn(userId);
        Mockito.lenient().when(mockedUserProjection.getApplicationUserFirstName()).thenReturn(firstName);
        Mockito.lenient().when(mockedUserProjection.getApplicationUserLastName()).thenReturn(lastName);

        // Set up the mock for UserQuizDetailsDTO
        Mockito.lenient().when(mockedUserQuizDetailsDTO.getScoreValue()).thenReturn(scoreValue);
        Mockito.lenient().when(mockedUserQuizDetailsDTO.getQuiz()).thenReturn(mockedQuizProjection);
        Mockito.lenient().when(mockedUserQuizDetailsDTO.getApplicationUser()).thenReturn(mockedUserProjection);

        return mockedUserQuizDetailsDTO;
    }


    public static Question mockQuestionDTO(Integer questionId, String description, String option1, String option2, String option3, String option4, String correctAnswer, Integer marks) {
        Question mockQuestionDTO = mock(Question.class);
        Mockito.when(mockQuestionDTO.getQuestionId()).thenReturn(questionId);
        Mockito.when(mockQuestionDTO.getQuestionDescription()).thenReturn(description);
        Mockito.when(mockQuestionDTO.getQuestionOption1()).thenReturn(option1);
        Mockito.when(mockQuestionDTO.getQuestionOption2()).thenReturn(option2);
        Mockito.when(mockQuestionDTO.getQuestionOption3()).thenReturn(option3);
        Mockito.when(mockQuestionDTO.getQuestionOption4()).thenReturn(option4);
        Mockito.when(mockQuestionDTO.getQuestionCorrectAns()).thenReturn(correctAnswer);
        Mockito.when(mockQuestionDTO.getQuestionMarks()).thenReturn(marks);
        return mockQuestionDTO;
    }

    // Method to mock Question Entity
    public static DisplayQuestionDTO mockDisplayQuestionDTO(Integer questionId, String description, String option1, String option2, String option3, String option4) {
        DisplayQuestionDTO mockQuestion = mock(DisplayQuestionDTO.class);
        Mockito.when(mockQuestion.getQuestionId()).thenReturn(questionId);
        Mockito.when(mockQuestion.getQuestionDescription()).thenReturn(description);
        Mockito.when(mockQuestion.getQuestionOption1()).thenReturn(option1);
        Mockito.when(mockQuestion.getQuestionOption2()).thenReturn(option2);
        Mockito.when(mockQuestion.getQuestionOption3()).thenReturn(option3);
        Mockito.when(mockQuestion.getQuestionOption4()).thenReturn(option4);
        return mockQuestion;
    }

    public static List<UserQuizDetailsDTO> getMockUserQuizDetailsDTOs() {
        List<UserQuizDetailsDTO> userQuizDetailsDTOList = new ArrayList<>();

        UserQuizDetailsDTO dto = mock(UserQuizDetailsDTO.class);
        QuizProjection quizProjection = mock(QuizProjection.class);
        UserQuizDetailsDTO.UserProjection userProjection = mock(UserQuizDetailsDTO.UserProjection.class);

        // Set mock data
        Mockito.when(dto.getScoreValue()).thenReturn(85);
        Mockito.when(dto.getQuizTakenDate()).thenReturn(LocalDateTime.now());

        // Mock the QuizProjection
        Mockito.when(quizProjection.getQuizId()).thenReturn(1);
        Mockito.when(quizProjection.getQuizName()).thenReturn("Sample Quiz");
        Mockito.when(quizProjection.getQuizTotalMarks()).thenReturn(100);
        Mockito.when(dto.getQuiz()).thenReturn(quizProjection);

        // Mock the UserProjection
        Mockito.when(userProjection.getApplicationUserId()).thenReturn(1);
        Mockito.when(userProjection.getApplicationUserFirstName()).thenReturn("John");
        Mockito.when(userProjection.getApplicationUserLastName()).thenReturn("Doe");
        Mockito.when(dto.getApplicationUser()).thenReturn(userProjection);

        // Add dto to the list
        userQuizDetailsDTOList.add(dto);

        return userQuizDetailsDTOList;
    }

    public static QuestionResponseProjection mockQuestionResponseProjection() {
        QuestionResponseProjection mockQuestionResponseProjection = mock(QuestionResponseProjection.class);
        QuestionResponseProjection.QuestionProjection mockQuestionProjection = mock(QuestionResponseProjection.QuestionProjection.class);

        // Set up expected values for the mock
        Mockito.lenient().when(mockQuestionResponseProjection.getUserResponseAns()).thenReturn("String");
        Mockito.lenient().when(mockQuestionResponseProjection.getIsCorrect()).thenReturn(true);
        Mockito.lenient().when(mockQuestionProjection.getQuestionId()).thenReturn(1);
        Mockito.lenient().when(mockQuestionProjection.getQuestionCorrectAns()).thenReturn("String");
        Mockito.lenient().when(mockQuestionProjection.getQuestionMarks()).thenReturn(2);
        Mockito.lenient().when(mockQuestionResponseProjection.getQuestion()).thenReturn(mockQuestionProjection);

        return mockQuestionResponseProjection;
    }

}
