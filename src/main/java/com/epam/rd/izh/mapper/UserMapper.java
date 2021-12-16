package com.epam.rd.izh.mapper;

import com.epam.rd.izh.entity.AuthorizedUser;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class UserMapper implements RowMapper<AuthorizedUser> {

    @Override
    public AuthorizedUser mapRow(ResultSet rs, int i) throws SQLException {
        return AuthorizedUser.builder()
                .id(rs.getLong("user_id"))
                .dateBirth(LocalDate.parse(rs.getString("date_birth")))
                .firstName(rs.getString("first_name"))
                .middleName(rs.getString("middle_name"))
                .lastName(rs.getString("last_name"))
                .login(rs.getString("login"))
                .password(rs.getString("passwords"))
                .role(rs.getString("title"))
                .build();
    }
}


