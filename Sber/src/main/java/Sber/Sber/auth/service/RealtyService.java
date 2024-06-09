package Sber.Sber.auth.service;

import Sber.Sber.auth.request.CreateRealtyRequest;
import Sber.Sber.auth.request.LoginRequest;
import Sber.Sber.auth.request.SignUpRequest;
import Sber.Sber.auth.response.AuthResponse;
import Sber.Sber.models.Company;
import Sber.Sber.models.Realty;
import Sber.Sber.models.User;
import Sber.Sber.repositories.CompanyRepository;
import Sber.Sber.repositories.RealtyRepository;
import Sber.Sber.repositories.UserRepository;
import Sber.Sber.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class RealtyService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final RealtyRepository realtyRepository;

    public AuthResponse createRealty(CreateRealtyRequest createRealtyRequest){

        Company company = companyRepository.findByCompanyname(createRealtyRequest.getCompanyname())
                .orElseThrow(() -> new RuntimeException("Компания не найдена"));

        User user = userRepository.findById(createRealtyRequest.getId_user())
                .orElseThrow(() -> new RuntimeException("Автор не найден"));


        // Создаем новую недвижимость
        Realty realty = Realty.builder()
                .realtyname(createRealtyRequest.getRealtyname())
                .price(createRealtyRequest.getPrice())
                .floor(createRealtyRequest.getFloor())
                .square(createRealtyRequest.getSquare())
                .type(createRealtyRequest.getType())
                .adress(createRealtyRequest.getAdress())
                .year(createRealtyRequest.getYear())
                .status(createRealtyRequest.getStatus())
                .companyForRealty(company)
                .allfloor(createRealtyRequest.getAllfloor())
                .numberRooms(createRealtyRequest.getNumberRooms())
                .description(createRealtyRequest.getDescription())
                .userForRealty(user)
                .build();

        // сохраяем недвижимость в базе данных
        realtyRepository.save(realty);

        return AuthResponse.builder()
                .message("Realty created successfully")
                .response(HttpStatus.OK)
                .build();
    }
}
