package org.sparky.sparkyai.user.domain;

import java.util.List;
import java.util.Objects;

import org.sparky.sparkyai.user.dto.CreateUserDto;
import org.sparky.sparkyai.user.dto.UpdateUserDto;
import org.sparky.sparkyai.common.exception.ResourceConflictException;
import org.sparky.sparkyai.common.exception.ResourceNotFoundException;
import org.sparky.sparkyai.company.domain.Company;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.sparky.sparkyai.user.infrastructure.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User getUserByIdAndCompanyId(Long id, Long companyId) {
        return userRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<User> getUsersByCompanyId(Long companyId) {
        return userRepository.findByCompanyId(companyId);
    }

    public User createUser(CreateUserDto userDto, Company company) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new ResourceConflictException("User with username already exists");
        }

        User user = modelMapper.map(userDto, User.class);
        user.setCompany(company);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User createUser(CreateUserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user, UpdateUserDto userDto) {
        if (!Objects.equals(userDto.getUsername(), user.getUsername())) {
            if (userRepository.existsByUsername(userDto.getUsername())) {
                throw new ResourceConflictException("User with username already exists");
            }

            user.setUsername(userDto.getUsername());
        }
        return userRepository.save(user);
    }

}
