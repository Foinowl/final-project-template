package com.epam.rd.izh.service;

import com.epam.rd.izh.entity.Role;
import com.epam.rd.izh.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleService {

    @Autowired
    RoleRepository repository;

    public Role getRoleById(Long id) {
        return repository.getRoleById(id);
    }
}
