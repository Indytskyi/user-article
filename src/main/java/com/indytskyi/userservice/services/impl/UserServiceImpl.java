package com.indytskyi.userservice.services.impl;

import static com.indytskyi.userservice.models.enums.Role.ADMIN;

import com.indytskyi.userservice.dtos.UserResponseDto;
import com.indytskyi.userservice.exception.LimitedPermissionException;
import com.indytskyi.userservice.exception.ObjectNotFoundException;
import com.indytskyi.userservice.exception.UserDuplicateEmailException;
import com.indytskyi.userservice.models.User;
import com.indytskyi.userservice.models.enums.Color;
import com.indytskyi.userservice.models.enums.Role;
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
                .orElseThrow(() -> new ObjectNotFoundException("User with email = " + email + "NOT FOUND"));
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDto> getAllUsersWithAgeGreaterThan(Integer age) {
        return userRepository.findUserByAgeGreaterThan(age)
                .stream()
                .map(this::mappedUsertoDto)
                .toList();
    }

    @Override
    public List<String> findNamesOfUsersWithMoreThan3Articles() {
        return userRepository.findNamesOfUsersWithMoreThan3Articles();
    }

    @Override
    public List<UserResponseDto> findUsersByArticlesColor(String color) {
        return userRepository.findUsersByArticlesColor(Color.valueOf(color))
                .stream()
                .map(this::mappedUsertoDto)
                .toList();

    }

    @Override
    public void checkIfUserWithNewEmailExist(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserDuplicateEmailException("A user with such email is already present");
        }
    }

    private UserResponseDto mappedUsertoDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .articles(user.getArticles())
                .build();
    }

}
