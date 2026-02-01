package com.yuqoi.jwt_template.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuqoi.jwt_template.dto.UserDto;
import com.yuqoi.jwt_template.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  public UserRepository userRepository;

  public List<UserDto> findUsers() {
    return userRepository.findUsers();
  }
}
