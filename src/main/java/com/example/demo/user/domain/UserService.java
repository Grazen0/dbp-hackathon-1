package com.example.demo.user.domain;

import java.util.List;

import com.example.demo.user.dto.CreateUserDto;
import com.example.demo.user.dto.UpdateUserDto;
import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.company.domain.Company;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.user.infrastructure.UserRepository;

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
        User user = createUserWithoutSaving(userDto, company);
        return userRepository.save(user);
    }

    public User createUserWithoutSaving(CreateUserDto userDto, Company company) {
        User user = modelMapper.map(userDto, User.class);
        user.setCompany(company);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    public User updateUser(User user, UpdateUserDto userDto) {
        user.setUsername(userDto.getUsername());
        user.setRole(userDto.getRole());
        return userRepository.save(user);
    }

}
