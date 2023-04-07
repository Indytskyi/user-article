package com.indytskyi.userservice.services.impl;

import com.indytskyi.userservice.models.User;
import com.indytskyi.userservice.models.enums.Color;
import com.indytskyi.userservice.repository.UserRepository;
import com.indytskyi.userservice.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsersWithAgeGreaterThan(Integer age) {
        return userRepository.findUserByAgeGreaterThan(age);
    }

    @Override
    public List<String> findNamesOfUsersWithMoreThan3Articles() {
        return userRepository.findNamesOfUsersWithMoreThan3Articles();
    }

    @Override
    public List<User> findUsersByArticlesColor(String color) {
        return userRepository.findUsersByArticlesColor(Color.valueOf(color));
    }


}