package Ideas.QuizApp.quiz_data.repository;

import Ideas.QuizApp.quiz_data.DTO.Admin.AdminDTO;
import Ideas.QuizApp.quiz_data.entity.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
    AdminDTO findByAdminEmail(String adminEmail);

    boolean existsByAdminEmail(String adminEmail);

    Optional<Admin> findByAdminEmailAndAdminPassword(String adminEmail, String adminPassword);
}