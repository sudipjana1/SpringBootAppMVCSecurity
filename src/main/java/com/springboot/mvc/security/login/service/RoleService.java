package com.springboot.mvc.security.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.mvc.security.login.model.Role;
import com.springboot.mvc.security.login.repository.RoleRepository;

import java.util.List;

@Service("roleService")
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

  

    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }

}