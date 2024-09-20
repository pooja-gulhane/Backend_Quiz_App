package Ideas.QuizApp.quiz_data.DTO.Quiz;

public interface QuizProjection {
    Integer getQuizId();
    Integer getQuizNoOfQuestions();
    Integer getQuizTotalMarks();
    Integer getQuizTimeAllocated();
    String getQuizName();
}
