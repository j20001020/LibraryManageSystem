package com.example.librarymanagesystem.service.interfaces;

import com.example.librarymanagesystem.dto.UserAccountDTO;

public interface UserAccountService {

    boolean isLogin(String username, String password);

    void register(UserAccountDTO userAccountDTO);
}
