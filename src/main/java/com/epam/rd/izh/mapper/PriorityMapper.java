package com.epam.rd.izh.mapper;

import com.epam.rd.izh.entity.Priority;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PriorityMapper implements RowMapper<Priority> {
    @Override
    public Priority mapRow(ResultSet rs, int i) throws SQLException {
        return Priority.builder()
                .id(rs.getLong("priority_id"))
                .title(rs.getString("title"))
                .color(rs.getString("color"))
                .build();
    }
}
