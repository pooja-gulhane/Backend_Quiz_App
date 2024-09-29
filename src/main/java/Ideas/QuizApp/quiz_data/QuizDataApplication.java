package Ideas.QuizApp.quiz_data;

import Ideas.QuizApp.quiz_data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizDataApplication.class, args);
	}
}
