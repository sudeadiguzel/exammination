package com.cloud.examsystem.user.service;

import com.cloud.examsystem.user.entity.User;
import com.cloud.examsystem.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User getByUsername(String username){
        return userRepository.getByUsername(username).get();
    }

    public List<User> getAllStudents(){
        return userRepository.findAllByInstructorIsFalse();
    }
}
