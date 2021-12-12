package com.epam.rd.izh.repository.impl;

import com.epam.rd.izh.entity.Role;
import com.epam.rd.izh.mapper.RoleMapper;
import com.epam.rd.izh.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class RoleRepositoryImpl implements RoleRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public Role getRoleById(Long id) {
        return jdbcTemplate.queryForObject("select * from role where role_id = ? ;", new Object[]{id}, roleMapper);
    }
}
