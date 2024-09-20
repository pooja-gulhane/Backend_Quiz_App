package Ideas.QuizApp.quiz_data.DTO.quiztaken;

public interface QuestionResponseProjection {
    String getUserResponseAns();
    Integer getUserResponseId();
    Boolean getIsCorrect();
    QuestionProjection getQuestion();

    interface QuestionProjection {
        Integer getQuestionId();
        String getQuestionCorrectAns();
        Integer getQuestionMarks();
    }
}
