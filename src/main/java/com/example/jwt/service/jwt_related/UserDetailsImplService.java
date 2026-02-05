
package com.example.jwt.service.jwt_related;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jwt.entity.User;
import com.example.jwt.entity.jwt_related.UserDetailsImpl;
import com.example.jwt.repository.UserRepository;

@Service
public class UserDetailsImplService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new RuntimeException("Didnt find a user");
        }
        User user = optional.get();
        return new UserDetailsImpl(user);
    }

}
