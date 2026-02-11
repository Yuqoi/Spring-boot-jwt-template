package com.example.jwt.service;

import com.example.jwt.entity.Role;
import com.example.jwt.entity.Roles;
import com.example.jwt.repository.RoleRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  @Autowired private RoleRepository roleRepository;

  public Role findRoleByName(Roles role) throws Exception {
    Optional<Role> optionalRole = roleRepository.findByRoleName(role);
    if (optionalRole.isEmpty()) {
      throw new Exception("Role not found");
    }
    return optionalRole.get();
  }
}
