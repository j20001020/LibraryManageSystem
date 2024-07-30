package com.example.librarymanagesystem.controller;

import com.example.librarymanagesystem.dto.ApiResponse;
import com.example.librarymanagesystem.dto.UserAccountDTO;
import com.example.librarymanagesystem.service.interfaces.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody UserAccountDTO userAccountDTO) {
        boolean isLogin = userAccountService.isLogin(userAccountDTO.getUsername(), userAccountDTO.getPassword());

        if (isLogin) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Login successfully!", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "Invalid username or password!", null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody UserAccountDTO userAccountDTO) {
        userAccountService.register(userAccountDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Register new user account successfully!", null));
    }
}
