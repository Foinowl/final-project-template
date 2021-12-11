package com.epam.rd.izh.mapper;

import com.epam.rd.izh.entity.Task;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TaskMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int i) throws SQLException {
        return Task.builder()
                .id(rs.getLong("task_id"))
                .completed(rs.getInt("completed"))
                .idCategory(rs.getLong("category_id"))
                .idPriority(rs.getLong("priority_id"))
                .idUser(rs.getLong("user_id"))
                .build();
    }
}
