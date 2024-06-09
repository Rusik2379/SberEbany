package Sber.Sber.Controllers;

import Sber.Sber.auth.request.CreateRealtyRequest;
import Sber.Sber.auth.service.RealtyService;
import Sber.Sber.repositories.RealtyRepository;
import Sber.Sber.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")


public class RealtyController {

    private final RealtyRepository realtyRepository;
    private final RealtyService realtyService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/realtycreate")
    public ResponseEntity<String> createRealty(@Valid @RequestBody CreateRealtyRequest createRealtyRequest){
        realtyService.createRealty(createRealtyRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Invitation sent to " + createRealtyRequest.getCompanyname()+ " set company name: " + createRealtyRequest.getRealtyname()) + " successfully");
    }
}
