package com.epam.rd.izh.service;

import com.epam.rd.izh.entity.Role;
import com.epam.rd.izh.repository.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Qualifier("roleRepositoryImpl")
    @Autowired
    RoleRepository repository;

    public Role getRoleById(Long id) {
        return repository.getRoleById(id);
    }

    public Long getRoleIdByTitle(String title) {
        return repository.getRoleIdByTitle(title);
    };

    public List<Role> getAllRoles() {
        return repository.getAllRoles();
    };
}
