package com.epam.rd.izh.repository.impl;

import com.epam.rd.izh.entity.Role;
import com.epam.rd.izh.mapper.RoleMapper;
import com.epam.rd.izh.repository.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public Role getRoleById(Long id) {
        return jdbcTemplate.queryForObject("select * from role where role_id = ? ;", new Object[]{id}, roleMapper);
    }

    @Override
    public Long getRoleIdByTitle(String title) {
        String sql = "select role.role_id from role where title = ?";

        return jdbcTemplate.queryForObject(sql, new Object[] {title}, Long.class);
    }

    @Override
    public List<Role> getAllRoles() {
        String sql = "select * from role;";
        return (List<Role>) jdbcTemplate.query(sql, roleMapper);
    }
}
