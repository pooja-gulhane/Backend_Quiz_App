package Ideas.QuizApp.quiz_data.dto.quiz;

public interface QuizProjection {
    Integer getQuizId();
    Integer getQuizNoOfQuestions();
    Integer getQuizTotalMarks();
    Integer getQuizTimeAllocated();
    String getQuizName();
    String getQuizImage();
}
