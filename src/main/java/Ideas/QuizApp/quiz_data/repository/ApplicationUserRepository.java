package Ideas.QuizApp.quiz_data.repository;

import Ideas.QuizApp.quiz_data.dto.applicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.entity.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Integer> {

    Optional<ApplicationUser> findByApplicationUserEmailAndApplicationUserPassword(String applicationUserEmail, String applicationUserPassword);

    Boolean existsByApplicationUserEmail(String applicationUserEmail);
    UserDTO findByApplicationUserEmail(String applicationUserEmail);

}
