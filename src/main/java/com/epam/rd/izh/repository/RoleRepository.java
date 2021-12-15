package com.epam.rd.izh.repository;

import com.epam.rd.izh.entity.Role;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface RoleRepository {
    Role getRoleById(Long id);

    Long getRoleIdByTitle(String title);

    List<Role> getAllRoles();
}
