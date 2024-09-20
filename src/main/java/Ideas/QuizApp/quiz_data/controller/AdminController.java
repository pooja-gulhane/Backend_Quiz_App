package Ideas.QuizApp.quiz_data.controller;


import Ideas.QuizApp.quiz_data.DTO.Admin.AdminDTO;
import Ideas.QuizApp.quiz_data.entity.Admin;
import Ideas.QuizApp.quiz_data.exception.EmailAlreadyRegisteredException;
import Ideas.QuizApp.quiz_data.exception.InvalidCredentialsException;
import Ideas.QuizApp.quiz_data.repository.AdminRepository;
import Ideas.QuizApp.quiz_data.roles.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Admin registerAdmin(@RequestBody Admin admin) {
        if (adminRepository.existsByAdminEmail(admin.getAdminEmail())) {
            throw new EmailAlreadyRegisteredException();
        }

        admin.setAdminPassword(passwordEncoder.encode(admin.getAdminPassword()));

        if(admin.getAdminRole()  == null || admin.getAdminRole().isEmpty()) {
            admin.setAdminRole(Roles.ROLE_ADMIN);
        }

        return (adminRepository.save(admin));
    }

    @PostMapping("/login")
    public Admin loginAdmin(@RequestBody AdminDTO adminDTO) {
        Optional<Admin> existingUser = adminRepository.findByAdminEmailAndAdminPassword(adminDTO.getAdminEmail(), adminDTO.getAdminPassword());

        if (existingUser.isPresent()) {
            return (existingUser.get());
        } else {
            throw new InvalidCredentialsException();
        }
    }
}
