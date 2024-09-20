package Ideas.QuizApp.quiz_data.services;

import Ideas.QuizApp.quiz_data.DTO.Admin.AdminDTO;
import Ideas.QuizApp.quiz_data.DTO.ApplicationUser.UserDTO;
import Ideas.QuizApp.quiz_data.exception.ResourceNotFound;
import Ideas.QuizApp.quiz_data.repository.AdminRepository;
import Ideas.QuizApp.quiz_data.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    @Autowired
    private ApplicationUserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepository.existsByApplicationUserEmail(username)) {
            System.out.println(userRepository.findByApplicationUserEmail(username));
            UserDTO user = userRepository.findByApplicationUserEmail(username);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getApplicationUserEmail())
                    .password(user.getApplicationUserPassword())
                    .roles(user.getApplicationUserrole())
                    .build();
        }
        if (adminRepository.existsByAdminEmail(username)) {
            AdminDTO admin = adminRepository.findByAdminEmail(username);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(admin.getAdminEmail())
                    .password(admin.getAdminPassword())
                    .roles(admin.getAdminRole())
                    .build();
        }

        throw new ResourceNotFound("Email Not Found");
    }
}
