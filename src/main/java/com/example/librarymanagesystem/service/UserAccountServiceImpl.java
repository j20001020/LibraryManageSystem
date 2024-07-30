package com.example.librarymanagesystem.service;

import com.example.librarymanagesystem.dto.UserAccountDTO;
import com.example.librarymanagesystem.model.UserAccount;
import com.example.librarymanagesystem.repository.interfaces.UserAccountRepository;
import com.example.librarymanagesystem.service.interfaces.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isLogin(String username, String password) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);

        if (userAccount == null) {
            return false;
        }

        return passwordEncoder.matches(password + userAccount.getSalt(), userAccount.getPassword());
    }

    @Override
    public void register(UserAccountDTO userAccountDTO) {
        UserAccount newUserAccount = new UserAccount();

        mapDtoToUserAccount(newUserAccount, userAccountDTO);

        userAccountRepository.save(newUserAccount);
    }

    private void mapDtoToUserAccount(UserAccount newUserAccount, UserAccountDTO userAccountDTO) {
        String salt = generateSalt();
        newUserAccount.setUsername(userAccountDTO.getUsername());
        newUserAccount.setPassword(hashPassword(userAccountDTO.getPassword(), salt));
        newUserAccount.setSalt(salt);
    }

    private String hashPassword(String password, String salt) {
        return passwordEncoder.encode(password + salt);
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
