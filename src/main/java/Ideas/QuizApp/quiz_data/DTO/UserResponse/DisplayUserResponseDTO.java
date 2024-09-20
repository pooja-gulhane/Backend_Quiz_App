package Ideas.QuizApp.quiz_data.DTO.UserResponse;

public interface DisplayUserResponseDTO {
    String getUserResponseAns();
    Boolean getIsCorrect();
    QuestionDTO getQuestion();

    interface QuestionDTO {
        String getQuestionDescription();
        String getQuestionOption1();
        String getQuestionOption2();
        String getQuestionOption3();
        String getQuestionOption4();
        String getQuestionCorrectAns();
    }
}
