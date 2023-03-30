package com.santechture.api.service;

import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.dto.admin.AdminDto;
import com.santechture.api.entity.Admin;
import com.santechture.api.exception.BusinessExceptions;
import com.santechture.api.exception.CustomException;
import com.santechture.api.jwt.JwtService;
import com.santechture.api.repository.AdminRepository;
import com.santechture.api.validation.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AdminService {

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AdminService(AdminRepository adminRepository,JwtService jwtService,AuthenticationManager authenticationManager,
                        PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<GeneralResponse> login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Optional<Admin> admin = adminRepository.findByUsernameIgnoreCase(request.getUsername());
        if(!admin.isPresent())
            throw new CustomException("Admin with name '"+request.getUsername()+"' not found", HttpStatus.OK);

        /*if(Objects.isNull(admin) || !admin.getPassword().equals(request.getPassword())){
            throw new BusinessExceptions("login.credentials.not.match");
        }*/
        String jwtToken = jwtService.generateToken(new AdminDto(admin.get()));
        return new GeneralResponse().response(new AdminDto(admin.get()),jwtToken);
    }
}
