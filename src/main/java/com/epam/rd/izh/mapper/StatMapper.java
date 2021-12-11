package com.epam.rd.izh.mapper;

import com.epam.rd.izh.entity.Stat;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StatMapper implements RowMapper<Stat> {
    @Override
    public Stat mapRow(ResultSet rs, int i) throws SQLException {
        return Stat.builder()
                .id(rs.getLong("stat_id"))
                .completedTotal(rs.getLong("completed_total"))
                .uncompletedTotal(rs.getLong("uncompleted_total"))
                .build();
    }
}
