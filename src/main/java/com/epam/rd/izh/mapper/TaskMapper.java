package com.epam.rd.izh.mapper;

import com.epam.rd.izh.entity.Task;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class TaskMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int i) throws SQLException {
        return Task.builder()
                .id(rs.getLong("taskId"))
                .completed(rs.getInt("completed"))
                .idCategory(rs.getLong("categoryId"))
                .idPriority(rs.getLong("priorityId"))
                .idUser(rs.getLong("userId"))
                .titleCategory(rs.getString("titleCategory"))
                .titlePriority(rs.getString("titlePriority"))
                .loginUser(rs.getString("loginUser"))
                .color(rs.getString("color"))
                .date(rs.getDate("dateTask").toLocalDate())
                .build();
    }
}
