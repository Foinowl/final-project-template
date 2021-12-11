package com.epam.rd.izh.mapper;

import com.epam.rd.izh.entity.Category;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int i) throws SQLException {
        return Category.builder()
                .id(rs.getLong("category_id"))
                .title(rs.getString("title"))
                .completedCount(rs.getLong("completed_count"))
                .uncompletedCount(rs.getLong("uncompleted_count"))
                .build();
    }
}
