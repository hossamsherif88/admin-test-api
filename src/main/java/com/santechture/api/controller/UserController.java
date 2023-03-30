package com.santechture.api.controller;


import com.santechture.api.dto.GeneralResponse;
import com.santechture.api.exception.BusinessExceptions;
import com.santechture.api.jwt.JwtService;
import com.santechture.api.service.UserService;
import com.santechture.api.validation.AddUserRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "user")
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;

    public UserController(UserService userService,JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<GeneralResponse> list(Pageable pageable){
        return userService.list(pageable);
    }
    @PostMapping
    public ResponseEntity<GeneralResponse> addNewUser(@RequestBody AddUserRequest request) throws BusinessExceptions {
        SecurityContextHolder.getContext().setAuthentication(null);
        return userService.addNewUser(request);
    }
}
