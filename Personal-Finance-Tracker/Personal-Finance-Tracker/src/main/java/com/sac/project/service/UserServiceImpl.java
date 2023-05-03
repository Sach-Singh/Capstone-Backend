package com.sac.project.service;

import com.sac.project.domain.User;
import com.sac.project.dto.UserDTO;
import com.sac.project.repository.UserRepository;
import com.sac.project.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    

    @Override
    public Status registerUser(UserDTO userDto) {
        // Check if the email already exists in the database
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            return Status.USER_ALREADY_EXISTS;
        }

        // Map the DTO to a User object and save it to the database
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);

        return Status.SUCCESS;
    }

    @Override
    public Status loginUser(UserDTO userDto) {
        // Find the user with the specified email and password
        Optional<User> userOptional = userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setLoggedIn(true);
            userRepository.save(user);
            return Status.SUCCESS;
        }

        return Status.FAILURE;
    }

    @Override
    public Status logUserOut(UserDTO userDto) {
        // Find the user with the specified email
        Optional<User> userOptional = userRepository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setLoggedIn(false);
            userRepository.save(user);
            return Status.SUCCESS;
        }

        return Status.FAILURE;
    }
}