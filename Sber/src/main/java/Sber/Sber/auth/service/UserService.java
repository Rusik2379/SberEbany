package Sber.Sber.auth.service;

import Sber.Sber.auth.request.InviteRequest;
import Sber.Sber.auth.response.AuthResponse;
import Sber.Sber.models.Company;
import Sber.Sber.models.User;
import Sber.Sber.repositories.CompanyRepository;
import Sber.Sber.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static Sber.Sber.models.enums.Role.ROLE_DIRECTOR;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public AuthResponse inviteUser(InviteRequest inviteRequest) {

        User user = userRepository.findByEmail(inviteRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getCompany()!= null || user.getRole() == ROLE_DIRECTOR) {
            return AuthResponse.builder()
                    .message("User уже состоит в компании")
                    .response(HttpStatus.FORBIDDEN)
                    .build();
        }
        else {
            Company company = companyRepository.findByCompanyname(inviteRequest.getCompanyname()).orElse(new Company(inviteRequest.getCompanyname()));
            user.setCompany(company);
            user.setCompanyname(inviteRequest.getCompanyname());
            userRepository.save(user);

            return AuthResponse.builder()
                    .message("User invite successfully")
                    .response(HttpStatus.OK)
                    .build();
        }
    }

    public List<UserRepository.UserProjection> listUserByCompany(String companyname) {
        return userRepository.findByCompanyname(companyname);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public void deleteAllUser() {
        userRepository.deleteAll();
    }
}