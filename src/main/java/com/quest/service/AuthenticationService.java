package com.quest.service;

import com.quest.entity.User;
import com.quest.repository.UserRepository;
import com.quest.util.PasswordEncryptor;
import com.quest.util.ValidationUtil;

public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isFieldEmpty(String... fields) {
        return ValidationUtil.isFieldEmpty(fields);
    }

    public boolean isPasswordValid(String password) {
        return ValidationUtil.isPasswordValid(password);
    }

    public boolean isPasswordCorrect(String login, String password) {
        return userRepository.findUserByLogin(login)
                .map(user -> PasswordEncryptor.checkPassword(password, user.getPass()))
                .orElse(false);
    }

    public User getUserByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .orElse(null);
    }

    public boolean isUserExists(String login) {
        return userRepository.isUserExists(login);
    }

    public void saveValidUser(String name, String login, String pass) {
        String hashedPass = PasswordEncryptor.hashPassword(pass);
        User user = new User(login, name, hashedPass);
        userRepository.save(user);
    }
}
