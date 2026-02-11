package com.example.jwt.service.jwt;

import com.example.jwt.entity.User;
import com.example.jwt.entity.jwt_related.UserDetailsImpl;
import com.example.jwt.exception.UserNotFoundException;
import com.example.jwt.repository.UserRepository;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImplService implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(@NonNull final String email)
      throws UsernameNotFoundException {
    final Optional<User> optional = userRepository.findByEmail(email);
    if (optional.isEmpty()) {
      throw new UserNotFoundException("Didnt find a user");
    }
    final User user = optional.get();
    return new UserDetailsImpl(user);
  }
}
