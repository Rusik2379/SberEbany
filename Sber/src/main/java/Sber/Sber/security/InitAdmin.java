package Sber.Sber.security;

import Sber.Sber.models.Company;
import Sber.Sber.models.User;
import Sber.Sber.repositories.CompanyRepository;
import Sber.Sber.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static Sber.Sber.models.enums.Role.ROLE_ADMIN;


@Slf4j
@Component
@RequiredArgsConstructor
public class InitAdmin implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            log.info("No user found, creating admin user");

            Company company = Company.builder()
                    .companyname("example")
                    .build();

            company = companyRepository.save(company);

            userRepository.save(
                    User.builder()
                            .firstname("Tamer")
                            .lastname("Bilici")
                            .middlename("Bilici")
                            .email("admin@example.com")
                            .password(new BCryptPasswordEncoder().encode("admin"))
                            .role(ROLE_ADMIN)
                            .company(company)
                            .companyname(company.getCompanyname())
                            .build());
        } else {
            log.warn("Admin user already exists");
        }
    }
}