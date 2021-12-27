package com.epam.rd.izh.mapper;

import com.epam.rd.izh.entity.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int i) throws SQLException {
        return Role.builder()
                .id(rs.getLong("role_id"))
                .title(rs.getString("title"))
                .build();
    }
}
