package Ideas.QuizApp.quiz_data.serviceTest.userResponseService;


import Ideas.QuizApp.quiz_data.dto.applicationUser.ApplicationUserRegisterDTO;
import Ideas.QuizApp.quiz_data.dto.question.QuestionDTO;
import Ideas.QuizApp.quiz_data.dto.quiz.QuizDTO;
import Ideas.QuizApp.quiz_data.dto.userResponse.*;
import Ideas.QuizApp.quiz_data.dto.quiztaken.QuestionResponseProjection;
import Ideas.QuizApp.quiz_data.dto.quiztaken.ScoreDTO;
import Ideas.QuizApp.quiz_data.entity.*;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.serviceTest.projectionutils.MockUtils;
import Ideas.QuizApp.quiz_data.repository.*;
import Ideas.QuizApp.quiz_data.services.UserResponseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserResponseServiceTest {

    @Mock
    QuestionRepository questionRepository;

    @Mock
    QuizTakenRepository quizTakenRepository;

    @Mock
    QuizRepository quizRepository;

    @Mock
    ApplicationUserRepository applicationUserRepository;


    @Mock
    UserResponseRepository userResponseRepository;
    @InjectMocks
    UserResponseService userResponseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveCurrent() {
        CurrentAndPreviousResponseDTO mockedCurrentAndPreviousResponseDTO = buildCurrentAndPreviousResponseDTO();
        UserResponse mockedUserResponse = buildUserResponse();
        CurrentResponseDTO currentResponseDTO = mockCurrentResponseDTO();

        when(userResponseRepository.existsByQuizAndApplicationUserAndQuestion(any(Quiz.class), any(ApplicationUser.class), any(Question.class))).thenReturn(true);
        when(userResponseRepository.findByQuizAndApplicationUserAndQuestion(any(Quiz.class), any(ApplicationUser.class), any(Question.class))).thenReturn(currentResponseDTO);
        when(userResponseRepository.findById(any())).thenReturn(Optional.of(mockedUserResponse));
        when(userResponseRepository.save(mockedUserResponse)).thenReturn(mockedUserResponse);

        userResponseService.saveCurrentResponse(mockedCurrentAndPreviousResponseDTO.getCurrentResponse());

        verify(userResponseRepository, times(1)).save(any(UserResponse.class));
    }

    @Test
    void shouldGetNext() {
        CurrentAndNextResponseDTO mockedCurrentAndNextResponseDTO = buildCurrentAndNextResponseDTO();
        CurrentResponseDTO mockedCurrentResponseDTO = mockCurrentResponseDTO();

        when(userResponseRepository.existsByQuizAndApplicationUserAndQuestion(any(Quiz.class), any(ApplicationUser.class), any(Question.class))).thenReturn(true);
        when(userResponseRepository.findByQuizAndApplicationUserAndQuestion(any(Quiz.class), any(ApplicationUser.class), any(Question.class))).thenReturn(mockedCurrentResponseDTO);

        CurrentResponseDTO responseDTO = userResponseService.getNextResponseIfPresent(mockedCurrentAndNextResponseDTO.getNextResponse());

        assertNotNull(responseDTO);
        assertEquals(mockedCurrentResponseDTO, responseDTO);
        assertEquals("Integer", responseDTO.getUserResponseAns());
        assertEquals("EVS", responseDTO.getQuiz().getQuizName());
    }

    @Test
    void shouldGetPrev() {
        CurrentAndPreviousResponseDTO mockedCurrentAndPreviousResponseDTO = buildCurrentAndPreviousResponseDTO();
        CurrentResponseDTO mockedCurrentResponseDTO = mockCurrentResponseDTO();

        when(userResponseRepository.existsByQuizAndApplicationUserAndQuestion(any(Quiz.class), any(ApplicationUser.class), any(Question.class))).thenReturn(true);
        when(userResponseRepository.findByQuizAndApplicationUserAndQuestion(any(Quiz.class), any(ApplicationUser.class), any(Question.class))).thenReturn(mockedCurrentResponseDTO);

        CurrentResponseDTO responseDTO = userResponseService.getPreviousResponseIfPresent(mockedCurrentAndPreviousResponseDTO.getPreviousResponse());

        assertNotNull(responseDTO);
        assertEquals(mockedCurrentResponseDTO, responseDTO);
        assertEquals("Integer", responseDTO.getUserResponseAns());
        assertEquals("EVS", responseDTO.getQuiz().getQuizName());
    }



    @Test
    public void shouldSubmitUserResponses() {
        CurrentAndNextResponseDTO mockResponseDTO = buildCurrentAndNextResponseDTO();

        setupMockUserResponseRepository();
        setupMockQuizTakenRepository();

        ScoreDTO result = userResponseService.submitUserResponses(mockResponseDTO);

        verifyInteractionsWithRepositories();
        assertNotNull(result);
        assertEquals(2, result.getScoreValue());
    }


    private void setupMockUserResponseRepository() {
        when(userResponseRepository.existsByQuizAndApplicationUserAndQuestion(any(Quiz.class), any(ApplicationUser.class), any(Question.class)))
                .thenReturn(true);

        CurrentResponseDTO mockCurrentResponseDTO = mockCurrentResponseDTO();
        when(userResponseRepository.findByQuizAndApplicationUserAndQuestion(any(Quiz.class), any(ApplicationUser.class), any(Question.class)))
                .thenReturn(mockCurrentResponseDTO);

        QuestionResponseProjection questionResponseProjection =MockUtils.mockQuestionResponseProjection();
        when(userResponseRepository.findByApplicationUserAndQuiz(any(ApplicationUser.class), any(Quiz.class)))
                .thenReturn(List.of(questionResponseProjection));

        UserResponse buildUserResponse =buildUserResponse();
        when(userResponseRepository.findById(anyInt()))
                .thenReturn(Optional.of(buildUserResponse));


        when(userResponseRepository.save(any(UserResponse.class)))
                .thenReturn(buildUserResponse);
    }


    private void setupMockQuizTakenRepository() {
        when(quizTakenRepository.save(any(QuizTaken.class)))
                .thenReturn(buildMockQuizTaken());
    }

    private void verifyInteractionsWithRepositories() {
        verify(userResponseRepository, times(2)).save(any(UserResponse.class));
        verify(quizTakenRepository, times(1)).save(any(QuizTaken.class));
    }


    @Test
    public void shouldGetUserResponsesByQuizAndUser() {
        Integer quizId = 1;
        Integer userId = 1;
        mockRepositories(quizId, userId);

        DisplayUserResponseDTO mockedResponse = createMockedUserResponse();

        when(userResponseRepository.findByQuizQuizIdAndApplicationUserApplicationUserId(quizId, userId))
                .thenReturn(List.of(mockedResponse));

        List<DisplayUserResponseDTO> result = userResponseService.getUserResponsesByQuizAndUser(quizId, userId);

        verifyUserResponse(result);
    }

    @Test
    public void shouldGetUserResponsesByQuizAndUser_QuizNotFound() {
        Integer quizId = 1;
        Integer userId = 1;
        when(quizRepository.findById(quizId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> {
            userResponseService.getUserResponsesByQuizAndUser(quizId, userId);
        });

        verify(quizRepository, times(1)).findById(quizId);
    }

    @Test
    public void shouldGetUserResponsesByQuizAndUser_UserNotFound() {
        Integer quizId = 1;
        Integer userId = 1;
        Quiz quiz = new Quiz();
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        when(applicationUserRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class, () -> {
            userResponseService.getUserResponsesByQuizAndUser(quizId, userId);
        });

        verify(quizRepository, times(1)).findById(quizId);
        verify(applicationUserRepository, times(1)).findById(userId);
    }



    private void mockRepositories(Integer quizId, Integer userId) {
        Quiz quiz = new Quiz();
        ApplicationUser user = new ApplicationUser();

        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
        when(applicationUserRepository.findById(userId)).thenReturn(Optional.of(user));
    }

    private DisplayUserResponseDTO createMockedUserResponse() {
        return MockUtils.mockDisplayUserResponseDTO(
                "String", true, "What is", "Option 1", "Option 2", "Option 3", "Option 4", "Option 1");
    }

    private void verifyUserResponse(List<DisplayUserResponseDTO> result) {
        assertNotNull(result);
        assertEquals(1, result.size());

        DisplayUserResponseDTO responseDTO = result.get(0);
        assertEquals("String", responseDTO.getUserResponseAns());
        assertTrue(responseDTO.getIsCorrect());

        verifyQuestionDetails(responseDTO.getQuestion());
    }

    private void verifyQuestionDetails(DisplayUserResponseDTO.QuestionDTO questionDTO) {
        assertEquals("What is", questionDTO.getQuestionDescription());
        assertEquals("Option 1", questionDTO.getQuestionOption1());
        assertEquals("Option 2", questionDTO.getQuestionOption2());
        assertEquals("Option 3", questionDTO.getQuestionOption3());
        assertEquals("Option 4", questionDTO.getQuestionOption4());
        assertEquals("Option 1", questionDTO.getQuestionCorrectAns());
    }



    //build
    private CurrentResponseDTO mockCurrentResponseDTO() {
        Integer userResponseId = 1;
        String userResponseAns = "Integer";
        Integer questionId = 1;
        String questionDescription = "What is";
        String questionOption1 = "String";
        String questionOption2 = "Boolean";
        String questionOption3 = "Integer";
        String questionOption4 = "Double";
        Integer applicationUserId = 1;
        String applicationUserEmail = "pooja@gmail.com";
        String applicationUserPassword = "pooja@123";
        String applicationUserRole = "Student";
        Integer quizId = 1;
        Integer quizNoOfQuestions = 10;
        Integer quizTotalMarks = 20;
        Integer quizTimeAllocated = 20;
        String quizName = "EVS";
        return MockUtils.mockCurrentResponseProjection(
                userResponseId, userResponseAns, questionId, questionDescription, questionOption1,
                questionOption2, questionOption3, questionOption4, applicationUserId, applicationUserEmail,
                applicationUserPassword, applicationUserRole, quizId, quizNoOfQuestions, quizTotalMarks,
                quizTimeAllocated, quizName);
    }


private QuestionDTO buildQuestionDTO() {
    return QuestionDTO.builder()
            .questionId(1)
            .questionDescription("What is ")
            .questionOption1("String")
            .questionOption2("Boolean")
            .questionOption3("Integer")
            .questionOption4("Double")
            .questionCorrectAns("Integer")
            .questionMarks(2)
            .build();
}


private ApplicationUserRegisterDTO buildApplicationUserRegisterDTO() {
    return ApplicationUserRegisterDTO.builder()
            .applicationUserId(1)
            .applicationUserFirstName("Pooja")
            .applicationUserLastName("Gulhane")
            .applicationUserEmail("pooja@gmail.com")
            .applicationUserPassword("pooja@123")
            .applicationUserRole("STUDENT")
            .build();
}


    private QuizDTO buildQuizDTO() {
        return QuizDTO.builder()
                .quizId(1)
                .quizNoOfQuestions(10)
                .quizTotalMarks(20)
                .quizTimeAllocated(10)
                .quizName("EVS")
                .quizImage(null)
                .build();
    }


    private UserResponse buildUserResponse() {
        return UserResponse.builder()
                .userResponseId(1)
                .question(null)
                .applicationUser(null)
                .quiz(null)
                .userResponseDateTime(0)
                .userResponseAns("Integer")
                .isCorrect(true)
                .build();
    }


    private QuizTaken buildMockQuizTaken() {
        ApplicationUser applicationUser = ApplicationUser.builder()
                .applicationUserId(1)
                .build();

        Quiz quiz = Quiz.builder()
                .quizId(1)
                .build();

        return QuizTaken.builder()
                .quizTakenId(1)
                .applicationUser(applicationUser)
                .quiz(quiz)
                .scoreValue(10)
                .quizTakenDate(null)
                .build();
    }


    private SubmitUserResponseDTO buildSubmitUserResponseDTO() {
        return SubmitUserResponseDTO.builder()
                .question(buildQuestionDTO())
                .applicationUser(buildApplicationUserRegisterDTO())
                .userResponseAns("String")
                .quiz(buildQuizDTO())
                .build();
    }


    private CurrentAndNextResponseDTO buildCurrentAndNextResponseDTO() {
        return CurrentAndNextResponseDTO.builder()
                .currentResponse(buildSubmitUserResponseDTO())
                .nextResponse(buildSubmitUserResponseDTO())
                .build();
    }


    private CurrentAndPreviousResponseDTO buildCurrentAndPreviousResponseDTO() {
        return CurrentAndPreviousResponseDTO.builder()
                .currentResponse(buildSubmitUserResponseDTO())
                .previousResponse(buildSubmitUserResponseDTO())
                .build();
    }

}
