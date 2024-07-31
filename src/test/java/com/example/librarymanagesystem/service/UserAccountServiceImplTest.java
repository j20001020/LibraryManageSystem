package com.example.librarymanagesystem.service;

import com.example.librarymanagesystem.dto.UserAccountDTO;
import com.example.librarymanagesystem.model.UserAccount;
import com.example.librarymanagesystem.repository.interfaces.UserAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceImplTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserAccountServiceImpl underTest;

    @Test
    void isLogin_successLogin_returnTrue() {

        // Arrange
        String username = "username";
        String password = "password";
        String salt = "salt";
        String hashedPassword = "hashedPassword";
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(username);
        userAccount.setPassword(hashedPassword);
        userAccount.setSalt(salt);

        when(userAccountRepository.findByUsername(username)).thenReturn(userAccount);
        when(passwordEncoder.matches(password + salt, hashedPassword)).thenReturn(true);

        // Act
        boolean result = underTest.isLogin(username, password);

        // Assert
        Assertions.assertEquals(true, result);
        verify(userAccountRepository).findByUsername(username);
        verify(passwordEncoder).matches(password + salt, hashedPassword);
    }

    @Test
    void isLogin_invalidPassword_failLogin_returnFalse() {

        // Arrange
        String username = "username";
        String invalidPassword = "invalidPassword";
        String salt = "salt";
        String hashedPassword = "hashedPassword";
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(username);
        userAccount.setPassword(hashedPassword);
        userAccount.setSalt(salt);

        when(userAccountRepository.findByUsername(username)).thenReturn(userAccount);
        when(passwordEncoder.matches(invalidPassword + salt, hashedPassword)).thenReturn(false);

        // Act
        boolean result = underTest.isLogin(username, invalidPassword);

        // Assert
        Assertions.assertEquals(false, result);
        verify(userAccountRepository).findByUsername(username);
        verify(passwordEncoder).matches(invalidPassword + salt, hashedPassword);
    }

    @Test
    void isLogin_invalidUsername_failLogin_returnFalse() {

        // Arrange
        String username = "username";
        String invalidUsername = "invalidUsername";
        String password = "password";
        String salt = "salt";
        String hashedPassword = "hashedPassword";
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(username);
        userAccount.setPassword(hashedPassword);
        userAccount.setSalt(salt);

        when(userAccountRepository.findByUsername(invalidUsername)).thenReturn(null);

        // Act
        boolean result = underTest.isLogin(invalidUsername, password);

        // Assert
        Assertions.assertEquals(false, result);
        verify(userAccountRepository).findByUsername(invalidUsername);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void register_shouldSaveNewUserAccount() {

        // Arrange
        String username = "testUser";
        String password = "testPassword";
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setUsername(username);
        userAccountDTO.setPassword(password);

        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");

        UserAccount newUserAccount = new UserAccount();

        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(newUserAccount);

        // Act
        underTest.register(userAccountDTO);

        // Assert
        ArgumentCaptor<UserAccount> userAccountCaptor = ArgumentCaptor.forClass(UserAccount.class);
        verify(userAccountRepository).save(userAccountCaptor.capture());

        UserAccount capturedUserAccount = userAccountCaptor.getValue();
        Assertions.assertEquals(userAccountDTO.getUsername(), capturedUserAccount.getUsername());
        Assertions.assertNotNull(capturedUserAccount.getPassword());
        Assertions.assertNotNull(capturedUserAccount.getSalt());

        verify(passwordEncoder).encode(anyString());
    }
}