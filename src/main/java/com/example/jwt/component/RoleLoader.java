package com.example.jwt.component;

import com.example.jwt.entity.Role;
import com.example.jwt.entity.Roles;
import com.example.jwt.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RoleLoader implements CommandLineRunner {

  @Autowired private RoleRepository roleRepository;

  @Override
  public void run(String... args) throws Exception {

    roleRepository
        .findByRoleName(Roles.ROLE_USER)
        .orElseGet(() -> roleRepository.save(new Role(null, Roles.ROLE_USER)));
    roleRepository
        .findByRoleName(Roles.ROLE_ADMIN)
        .orElseGet(() -> roleRepository.save(new Role(null, Roles.ROLE_ADMIN)));
  }
}
