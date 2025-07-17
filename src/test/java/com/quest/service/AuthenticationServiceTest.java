package com.quest.service;

import com.quest.entity.User;
import com.quest.repository.UserRepository;
import com.quest.util.PasswordEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    private UserRepository userRepository;
    private AuthenticationService authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        authService = new AuthenticationService(userRepository);
    }

    @Test
    void givenEmptyFields_whenIsFieldEmpty_thenReturnsTrue() {
        assertTrue(authService.isFieldEmpty("", "test"));
    }

    @Test
    void givenAllNonEmptyFields_whenIsFieldEmpty_thenReturnsFalse() {
        assertFalse(authService.isFieldEmpty("test1", "test2"));
    }

    @Test
    void givenNullField_whenIsFieldEmpty_thenReturnsTrue() {
        assertTrue(authService.isFieldEmpty("abc", null));
    }

    @Test
    void givenShortPassword_whenIsPasswordValid_thenReturnsFalse() {
        assertFalse(authService.isPasswordValid("123"));
    }

    @Test
    void givenValidPassword_whenIsPasswordValid_thenReturnsTrue() {
        assertTrue(authService.isPasswordValid("SecurePass123"));
    }

    @Test
    void givenEmptyPassword_whenIsPasswordValid_thenReturnsFalse() {
        assertFalse(authService.isPasswordValid(""));
    }

    @Test
    void givenCorrectPassword_whenIsPasswordCorrect_thenReturnsTrue() {
        String rawPass = "mypassword";
        String hashed = PasswordEncryptor.hashPassword(rawPass);
        User user = new User("login", "User", hashed);

        when(userRepository.findUserByLogin("login")).thenReturn(Optional.of(user));

        assertTrue(authService.isPasswordCorrect("login", rawPass));
    }

    @Test
    void givenIncorrectPassword_whenIsPasswordCorrect_thenReturnsFalse() {
        String rawPass = "wrong";
        String hashed = PasswordEncryptor.hashPassword("realpass");
        User user = new User("login", "User", hashed);

        when(userRepository.findUserByLogin("login")).thenReturn(Optional.of(user));

        assertFalse(authService.isPasswordCorrect("login", rawPass));
    }

    @Test
    void givenNonexistentLogin_whenIsPasswordCorrect_thenReturnsFalse() {
        when(userRepository.findUserByLogin("nosuch")).thenReturn(Optional.empty());

        assertFalse(authService.isPasswordCorrect("nosuch", "pass"));
    }

    @Test
    void givenExistingLogin_whenGetUserByLogin_thenReturnsUser() {
        User user = new User("test", "Name", "hash");
        when(userRepository.findUserByLogin("test")).thenReturn(Optional.of(user));

        User result = authService.getUserByLogin("test");

        assertEquals(user, result);
    }

    @Test
    void givenNonexistentLogin_whenGetUserByLogin_thenReturnsNull() {
        when(userRepository.findUserByLogin("none")).thenReturn(Optional.empty());

        assertNull(authService.getUserByLogin("none"));
    }

    @Test
    void givenExistingUser_whenIsUserExists_thenReturnsTrue() {
        when(userRepository.isUserExists("login")).thenReturn(true);

        assertTrue(authService.isUserExists("login"));
    }

    @Test
    void givenNonexistingUser_whenIsUserExists_thenReturnsFalse() {
        when(userRepository.isUserExists("nouser")).thenReturn(false);

        assertFalse(authService.isUserExists("nouser"));
    }

    @Test
    void givenValidUserData_whenSaveValidUser_thenUserIsSavedWithHashedPassword() {
        authService.saveValidUser("Name", "login", "password");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User saved = userCaptor.getValue();
        assertEquals("Name", saved.getName());
        assertEquals("login", saved.getLog());
        assertTrue(PasswordEncryptor.checkPassword("password", saved.getPass()));
    }
}