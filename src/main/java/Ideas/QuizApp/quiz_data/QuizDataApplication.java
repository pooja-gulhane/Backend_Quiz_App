package Ideas.QuizApp.quiz_data;

import Ideas.QuizApp.quiz_data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizDataApplication implements CommandLineRunner {

	@Autowired
	ApplicationUserRepository applicationUserRepository;

	@Autowired
	QuizRepository quizRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	UserResponseRepository userResponseRepository;

	public static void main(String[] args) {
		SpringApplication.run(QuizDataApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		// 		TODO:	Insert
//		Product product = new Product(0, "pencil", 30.0f);
//		Product demo = productRepository.save(product);
//		System.out.println("Product Saved.");
//		System.out.println(demo);

// 		TODO:	Update
//		Product product = new Product(1, "Pen", 15.75f);
//		Product demo = productRepository.save(product);
//		System.out.println("Product Updated.");
//		System.out.println(demo);

// 		TODO:	View
//		List<Product> products = (List<Product>) productRepository.findAll();
//		for (Product product : products) {
//			System.out.println(product);
//		}

// 		TODO:	Delete
//		productRepository.deleteById(2);
//		System.out.println("Product Deleted.");

		// 		TODO:	Insert USER
//		ApplicationUser user = new ApplicationUser(0, "Zoe","Dalvi", "zoedalvi15@gmail.com","zoe123");
//		applicationUserRepository.save(user);
//		ApplicationUser user1 = new ApplicationUser(0, "John","Head", "johnhead2002@gmail.com","john123");
//		applicationUserRepository.save(user1);

		//		TODO: INSERT QUIZ
//		Quiz quiz2 = new Quiz(0, 10, 10, 20, "C++ Quiz"); // Use null for auto-generated ID
//		Quiz savedQuiz = quizRepository.save(quiz2);



		//		TODO: DELETEBYID
//		quizRepository.deleteById(1);


		//		TODO : INSERT QUESTION
//		List<Question> questions = List.of(
//				new Question(0, "What is the default value of a boolean variable in Java?", "true", "false", "0", "null", "false", 2, new Quiz()),
//				new Question(0, "Which of the following is not a Java primitive type?", "int", "String", "char", "boolean", "String", 2, savedQuiz),
//				new Question(0, "What is the output of System.out.println(2 + 3 + \"Hello\");?", "5Hello", "Hello5", "23Hello", "Hello23", "5Hello", 2, savedQuiz),
//				new Question(0, "Which keyword is used to create a class in Java?", "class", "create", "define", "object", "class", 2, savedQuiz),
//				new Question(0, "Which of the following is used to handle exceptions in Java?", "try-catch", "if-else", "switch-case", "for-loop", "try-catch", 2, savedQuiz),
//				new Question(0, "What is the size of an int in Java?", "8 bits", "16 bits", "32 bits", "64 bits", "32 bits", 2, savedQuiz),
//				new Question(0, "Which of these is a valid declaration of a method in Java?", "void method(int a)", "method void(int a)", "method(int a) void", "int method(void a)", "void method(int a)", 2, savedQuiz),
//				new Question(0, "What does the static keyword mean?", "The method can be overridden", "The method belongs to the class rather than instances", "The method can be accessed only within the class", "The method is abstract", "The method belongs to the class rather than instances", 2, savedQuiz),
//				new Question(0, "Which of the following is a superclass of all classes in Java?", "Object", "Class", "Main", "System", "Object", 2, savedQuiz),
//				new Question(0, "What is the correct way to create an array in Java?", "int[] arr = new int(5);", "int arr[] = new int[5];", "int arr = new int[5];", "int arr[] = new int(5);", "int arr[] = new int[5];", 2, savedQuiz)
//		);
//
//		for (Question question : questions) {
//			questionRepository.save(question);
//		}
//
//		Question q1 = new Question(0, "What does cout do in C++?",
//				"Reads input", "Writes output", "Defines a class", "Handles errors",
//				"Writes output", 1, new Quiz(2,null,null,null,null));
//		questionRepository.save(q1);
//
//		Question q2 = new Question(0, "Which symbol is used to denote comments in C++?",
//				"#", "//", "--", "<!--",
//				"//", 1, new Quiz(2,null,null,null,null));
//		questionRepository.save(q2);
//
//		Question q3 = new Question(0, "What is the default access specifier for class members in C++?",
//				"public", "private", "protected", "default",
//				"private", 1, new Quiz(2, null,null,null,null));
//		questionRepository.save(q3);
//
//		Question q4 = new Question(0, "How do you declare a pointer in C++?",
//				"int ptr;", "int ptr;*", "*int ptr;", "int& ptr;",
//				"int ptr;", 1, new Quiz(2, null,null,null,null));
//		questionRepository.save(q4);
//
//		Question q5 = new Question(0, "Which header file is required for input/output operations in C++?",
//				"<iostream>", "<stdio.h>", "<conio.h>", "<math.h>",
//				"<iostream>", 1, new Quiz(2, null,null,null,null));
//		questionRepository.save(q5);
//
//		Question q6 = new Question(0, "What is the correct way to declare a constant in C++?",
//				"const int x;", "int const x;", "constant int x;", "int x = constant;",
//				"const int x;", 1, new Quiz(2, null,null,null,null));
//		questionRepository.save(q6);
//
//		Question q7 = new Question(0, "Which operator is used to access members of a structure in C++?",
//				"::", ".", "->", "&",
//				".", 1, new Quiz(2, null,null,null,null));
//		questionRepository.save(q7);
//
//		Question q8 = new Question(0, "What is the purpose of the main() function in C++?",
//				"To declare variables", "To define classes", "To start program execution", "To include libraries",
//				"To start program execution", 1, new Quiz(2, null,null,null,null));
//		questionRepository.save(q8);
//
//		Question q9 = new Question(0, "Which keyword is used to define a class in C++?",
//				"define", "class", "struct", "object",
//				"class", 1, new Quiz(2, null,null,null,null));
//		questionRepository.save(q9);
//
//		Question q10 = new Question(0, "How do you allocate memory dynamically for an array in C++?",
//				"int* arr = malloc(5 * sizeof(int));", "int arr = new int[5];", "int arr = new int[5];", "int arr = alloc(5 * sizeof(int));",
//				"int arr = new int[5];", 1, new Quiz(2, null,null,null,null));
//		questionRepository.save(q10);

//		TODO: DELETE QUESTIONS
//		questionRepository.deleteById(6);

//
//		TODO: INSERT USERRESPONSE
//		UserResponse response1 = new UserResponse(52, new ApplicationUser(2,null,null,null,null), new Question(52,null,null,null,null,null,null,null,null), "Writes output",  LocalDateTime.now(),new Quiz(2,null,null,null,null)); // Correct answer
//		userResponseRepository.save(response1);
//
//		UserResponse response2 = new UserResponse(53, new ApplicationUser(2,null,null,null,null), new Question(53,null,null,null,null,null,null,null,null), "//",LocalDateTime.now(),new Quiz(2,null,null,null,null)); // Correct answer
//		userResponseRepository.save(response2);
//
//		UserResponse response3 = new UserResponse(54,new ApplicationUser(2,null,null,null,null), new Question(54,null,null,null,null,null,null,null,null), "protected",LocalDateTime.now(),new Quiz(2,null,null,null,null)); // Incorrect answer
//		userResponseRepository.save(response3);
//
//		UserResponse response4 = new UserResponse(55, new ApplicationUser(2,null,null,null,null), new Question(55,null,null,null,null,null,null,null,null), "int ptr;",LocalDateTime.now(),new Quiz(2,null,null,null,null)); // Correct answer
//		userResponseRepository.save(response4);
//
//		UserResponse response5 = new UserResponse(56, new ApplicationUser(2,null,null,null,null), new Question(56,null,null,null,null,null,null,null,null), "<iostream>",LocalDateTime.now(),new Quiz(2,null,null,null,null)); // Incorrect answer
//		userResponseRepository.save(response5);
//
//		UserResponse response6 = new UserResponse(57, new ApplicationUser(2,null,null,null,null), new Question(57,null,null,null,null,null,null,null,null), "const int x;",LocalDateTime.now(),new Quiz(2,null,null,null,null)); // Correct answer
//		userResponseRepository.save(response6);
//
//		UserResponse response7 = new UserResponse(58, new ApplicationUser(2,null,null,null,null), new Question(58,null,null,null,null,null,null,null,null), "::",LocalDateTime.now(),new Quiz(2,null,null,null,null)); // Incorrect answer
//		userResponseRepository.save(response7);
//
//		UserResponse response8 = new UserResponse(59, new ApplicationUser(2,null,null,null,null), new Question(59,null,null,null,null,null,null,null,null), "To start program execution",LocalDateTime.now(),new Quiz(2,null,null,null,null));
//		userResponseRepository.save(response8);
//
//		UserResponse response9 = new UserResponse(60, new ApplicationUser(2,null,null,null,null), new Question(60,null,null,null,null,null,null,null,null), "class",LocalDateTime.now(),new Quiz(2,null,null,null,null));
//		userResponseRepository.save(response9);
//
//		UserResponse response10 = new UserResponse(61, new ApplicationUser(2,null,null,null,null), new Question(61,null,null,null,null,null,null,null,null), "int* arr = malloc(5 * sizeof(int));",LocalDateTime.now(),new Quiz(2,null,null,null,null));
//		userResponseRepository.save(response10);

//		UserResponse response10 = new UserResponse(1, new ApplicationUser(1,null,null,null,null), new Question(1,null,null,null,null,null,null,null,null), "false",null,new Quiz(1,null,null,null,null));
//		userResponseRepository.save(response10);

//		UserResponse response11 = new UserResponse(2, new ApplicationUser(1,null,null,null,null), new Question(2,null,null,null,null,null,null,null,null), "32 bits",null,new Quiz(1,null,null,null,null));
//		userResponseRepository.save(response11);
//
//		UserResponse response12 = new UserResponse(0, new ApplicationUser(1,null,null,null,null), new Question(3,null,null,null,null,null,null,null,null), "32 bits",LocalDateTime.now());
//		userResponseRepository.save(response12);




		// TODO: INSERT SCORE
//		Score s1 = new Score(0, new ApplicationUser(1,null,null,null,null),new Quiz(1,null,null,null,null),6);
//		scoreRepository.save(s1);

//		Score s2 = new Score(0, new ApplicationUser(2,null,null,null,null),new Quiz(2,null,null,null,null),7);
//		scoreRepository.save(s2);

		//TODO: DELETESCORE
//		scoreRepository.deleteById(2);


		//TODO: CALCULATESCORE
//		CalculateScoreDTO scoreDTO = scoreRepository.calculateScore(2, 2);
//		System.out.println("Calculated Score: " + scoreDTO.getScoreValue());

		//TODO: INSERT INTO SCORE TABLE
////      scoreRepository.save(new Score(0,new ApplicationUser(2,null,null,null,null), new Quiz(2, null,null,null,null), scoreDTO.getScoreValue()));

		//TODO: DISPLAY QUESTION RANDOMLY FOR QUIZ
//		List<DisplayQuestionDTO> Question = questionRepository.findByQuiz(1);
//		for(DisplayQuestionDTO q: Question)
//		{
//			System.out.println("Id=" + q.getQuestionId());
//			System.out.println("Description=" + q.getQuestionDescription());
//			System.out.println("Option1=" + q.getQuestionOption1());
//			System.out.println("Option2=" + q.getQuestionOption2());
//			System.out.println("Option3=" + q.getQuestionOption3());
//			System.out.println("Option4=" + q.getQuestionOption4());
//		}

		//TODO : DISPLAY USER HISTORY OF QUIZ

//		List<DisplayUserHistoryDTO> displayUserHistoryDTO = scoreRepository.findByApplicationUser(new ApplicationUser(1,null,null,null,null));
//		for(DisplayUserHistoryDTO d: displayUserHistoryDTO )
//		{
//			System.out.println("Quiz Name=" + d.getQuiz().getQuizName());
//			System.out.println("First Name=" + d.getApplicationUser().getApplicationUserFirstName());
//			System.out.println("Last Name=" + d.getApplicationUser().getApplicationUserLastName());
//			System.out.println("Score= " + d.getScoreValue());
//		}

		//TODO : IN HISTORY GET USER MARKED ANSWER

//		List<DisplayUserResponseDTO> displayUserResponseDTO = userResponseRepository.findByQuiz(new Quiz(1,null,null,null,null));
//		for(DisplayUserResponseDTO d: displayUserResponseDTO )
//		{
//			System.out.println(d.getQuestion().getQuestionDescription());
//			System.out.println(d.getQuestion().getQuestionOption1());
//			System.out.println(d.getQuestion().getQuestionOption2());
//			System.out.println(d.getQuestion().getQuestionOption3());
//			System.out.println(d.getQuestion().getQuestionOption4());
//			System.out.println(d.getUserResponseAns());
//			System.out.println(d.getQuestion().getQuestionCorrectAns());
//		}
	}
}
