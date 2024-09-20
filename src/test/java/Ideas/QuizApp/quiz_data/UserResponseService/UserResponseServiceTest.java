package Ideas.QuizApp.quiz_data.UserResponseService;


import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.ApplicationUserRegisterDTO;
import Ideas.QuizApp.quiz_data.DTO.Question.QuestionDTO;
import Ideas.QuizApp.quiz_data.DTO.Quiz.QuizDTO;
import Ideas.QuizApp.quiz_data.DTO.UserResponse.CurrentAndNextResponseDTO;
import Ideas.QuizApp.quiz_data.DTO.UserResponse.CurrentResponseDTO;
import Ideas.QuizApp.quiz_data.DTO.UserResponse.DisplayUserResponseDTO;
import Ideas.QuizApp.quiz_data.DTO.UserResponse.SubmitUserResponseDTO;
import Ideas.QuizApp.quiz_data.DTO.quiztaken.QuestionResponseProjection;
import Ideas.QuizApp.quiz_data.DTO.quiztaken.ScoreDTO;
import Ideas.QuizApp.quiz_data.entity.*;
import Ideas.QuizApp.quiz_data.projectionutils.MockUtils;
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
import static org.mockito.ArgumentMatchers.*;
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
    void testSaveCurrentResponseAndGetNext() {
        CurrentAndNextResponseDTO mockedCurrentAndNextResponseDTO = buildCurrentAndNextResponseDTO();

        when(userResponseRepository.existsByQuizAndApplicationUserAndQuestion(any(Quiz.class), any(ApplicationUser.class), any(Question.class))).thenReturn(true);
        CurrentResponseDTO mockedCurrentResponseDTO = MockUtils.mockCurrentResponseProjection(1, "Integer", 1, "What is", "String", "Boolean", "Integer","Double",1,"pooja@gmail.com","pooja@123","Student",1,10,20,10,"EVS");

        when(userResponseRepository.findByQuizAndApplicationUserAndQuestion(any(Quiz.class),any(ApplicationUser.class),any(Question.class))).thenReturn(mockedCurrentResponseDTO);
        UserResponse mockedUserResponse = buildUserResponse();
        when(userResponseRepository.findById(any(Integer.class))).thenReturn(Optional.of(mockedUserResponse));

        when(userResponseRepository.save(mockedUserResponse)).thenReturn(mockedUserResponse);

        CurrentResponseDTO actualCurrentResponseDTO = userResponseService.saveCurrentResponseAndGetNext(mockedCurrentAndNextResponseDTO);

        assertNotNull(actualCurrentResponseDTO);

        assertEquals(mockedCurrentResponseDTO.getUserResponseId(),actualCurrentResponseDTO.getUserResponseId());
        assertEquals(mockedCurrentResponseDTO.getUserResponseAns(),actualCurrentResponseDTO.getUserResponseAns());
        assertEquals(mockedCurrentResponseDTO.getApplicationUser(),actualCurrentResponseDTO.getApplicationUser());
        assertEquals(mockedCurrentResponseDTO.getQuiz(),actualCurrentResponseDTO.getQuiz());
        assertEquals(mockedCurrentResponseDTO.getQuestion(),actualCurrentResponseDTO.getQuestion());
    }

    private QuestionDTO buildQuestionDTO() {
        return new QuestionDTO(1, "What is ", "String", "Boolean", "Integer", "Double", "Integer", 2);
    }

    private ApplicationUserRegisterDTO buildApplicationUserRegisterDTO() {
        return new ApplicationUserRegisterDTO(1, "Pooja", "Gulhane", "pooja@gmail.com", "pooja@123", "STUDENT");
    }

    private QuizDTO buildQuizDTO() {
        return new QuizDTO(1, 10, 20, 10, "EVS",null);
    }

    private UserResponse buildUserResponse()
    {
        return new UserResponse(1,null,null,"Integer",0,null,true);
    }

    private SubmitUserResponseDTO buildSubmitUserResponseDTO() {
        QuestionDTO questionDTO = buildQuestionDTO();
        ApplicationUserRegisterDTO applicationUserRegisterDTO = buildApplicationUserRegisterDTO();
        QuizDTO quizDTO = buildQuizDTO();
        return new SubmitUserResponseDTO(questionDTO, applicationUserRegisterDTO, "Integer", quizDTO);
    }

    private CurrentAndNextResponseDTO buildCurrentAndNextResponseDTO() {
        SubmitUserResponseDTO currentResponse = buildSubmitUserResponseDTO();
        SubmitUserResponseDTO nextResponse = buildSubmitUserResponseDTO();
        return new CurrentAndNextResponseDTO(currentResponse, nextResponse);
    }

    @Test
    public void testSubmitUserResponses_ShouldCalculateScoreAndSaveQuizTaken() {
        // Arrange
        Integer userId = 1;
        Integer quizId = 1;
        ApplicationUser user = new ApplicationUser();
        user.setApplicationUserId(userId);
        Quiz quiz = new Quiz();
        quiz.setQuizId(quizId);

        CurrentAndNextResponseDTO mockedCurrentAndNextResponseDTO = buildCurrentAndNextResponseDTO();

        when(userResponseRepository.existsByQuizAndApplicationUserAndQuestion(any(Quiz.class), any(ApplicationUser.class), any(Question.class))).thenReturn(true);
        CurrentResponseDTO mockedCurrentResponseDTO = MockUtils.mockCurrentResponseProjection(1, "Integer", 1, "What is", "String", "Boolean", "Integer","Double",1,"pooja@gmail.com","pooja@123","Student",1,10,20,10,"EVS");
        when(userResponseRepository.findByQuizAndApplicationUserAndQuestion(any(Quiz.class),any(ApplicationUser.class),any(Question.class))).thenReturn(mockedCurrentResponseDTO);

        QuestionResponseProjection questionResponseProjection = MockUtils.mockQuestionResponseProjection("Integer", 1, true, 1, "Integer", 2);
        QuestionResponseProjection.QuestionProjection mockedProjection = questionResponseProjection.getQuestion();
        assertEquals(questionResponseProjection.getIsCorrect(), true);
        assertEquals(questionResponseProjection.getQuestion().getQuestionId(),1);
        assertEquals(questionResponseProjection.getQuestion().getQuestionMarks(),2);
        assertEquals(questionResponseProjection.getQuestion().getQuestionCorrectAns(),"Integer");
        assertEquals(questionResponseProjection.getUserResponseId(),1);
        assertEquals(questionResponseProjection.getUserResponseAns(),"Integer");
        assertEquals(questionResponseProjection.getQuestion(), mockedProjection);

        when(userResponseRepository.findByApplicationUserAndQuiz(any(ApplicationUser.class), any(Quiz.class)))
                .thenReturn(List.of(questionResponseProjection));

        UserResponse userResponse = new UserResponse();
        userResponse.setUserResponseId(1);
        when(userResponseRepository.findById(anyInt())).thenReturn(Optional.of(userResponse));
        when(userResponseRepository.save(any(UserResponse.class))).thenReturn(userResponse);


        QuizTaken quizTaken = new QuizTaken();
        quizTaken.setQuizTakenId(1);
        when(quizTakenRepository.save(any(QuizTaken.class))).thenReturn(quizTaken);

        //List<SubmitUserResponseDTO> result = userResponseService.submitUserResponses(userId, quizId);
        CurrentAndNextResponseDTO currentAndNextResponseDTO=  buildCurrentAndNextResponseDTO();
        ScoreDTO result = userResponseService.submitUserResponses(currentAndNextResponseDTO);

        verify(userResponseRepository, times(2)).save(any(UserResponse.class));
        verify(quizTakenRepository, times(1)).save(any(QuizTaken.class));
    }

    @Test
    public void testGetUserResponsesByQuizAndUser_ShouldReturnUserResponses() {
        Integer quizId = 1;
        Integer userId = 1;

        when(quizRepository.findById(quizId)).thenReturn(Optional.of(new Quiz()));
        when(applicationUserRepository.findById(userId)).thenReturn(Optional.of(new ApplicationUser()));

        DisplayUserResponseDTO mockDisplayUserResponseDTO = MockUtils.mockDisplayUserResponseDTO(
                "Integer",                // userResponseAns
                true,                     // isCorrect
                "What is",                // questionDescription
                "Option 1",               // questionOption1
                "Option 2",               // questionOption2
                "Option 3",               // questionOption3
                "Option 4",               // questionOption4
                "Option 1"                // correctAns
        );

        when(userResponseRepository.findByQuizQuizIdAndApplicationUserApplicationUserId(quizId, userId))
                .thenReturn(List.of(mockDisplayUserResponseDTO));

        List<DisplayUserResponseDTO> result = userResponseService.getUserResponsesByQuizAndUser(quizId, userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        DisplayUserResponseDTO responseDTO = result.get(0);

        assertEquals("Integer", responseDTO.getUserResponseAns());
        assertTrue(responseDTO.getIsCorrect());

        DisplayUserResponseDTO.QuestionDTO questionDTO = responseDTO.getQuestion();
        assertEquals("What is", questionDTO.getQuestionDescription());
        assertEquals("Option 1", questionDTO.getQuestionOption1());
        assertEquals("Option 2", questionDTO.getQuestionOption2());
        assertEquals("Option 3", questionDTO.getQuestionOption3());
        assertEquals("Option 4", questionDTO.getQuestionOption4());
        assertEquals("Option 1", questionDTO.getQuestionCorrectAns());
    }
}
