package com.epam.rd.izh.repository.impl;

import com.epam.rd.izh.entity.Priority;
import com.epam.rd.izh.mapper.PriorityMapper;
import com.epam.rd.izh.repository.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PriorityRepositoryImpl implements PriorityRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PriorityMapper priorityMapper;

    @Override
    public List<Priority> findAll() {
        String sql = "select * from priority;";
        return jdbcTemplate.query(sql, priorityMapper);
    }

    @Override
    public boolean insert(Priority priority) {
        String sql = "insert into priority (title, color) VALUES(?, ?);";

        return jdbcTemplate.update(sql, priority.getTitle(), priority.getColor()) > 0;
    }

    @Override
    public boolean update(Priority priority) {
        String sql = "update priority set title = ?, color = ? where priority_id = ?;";

        return jdbcTemplate.update(sql, priority.getTitle(), priority.getColor(), priority.getId()) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "delete from priority where priority_id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public Priority findById(Long id) {
        String sql = "select * from priority where priority_id = ?;";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, priorityMapper);
    }

    @Override
    public List<Priority> findByTitle(String text) {
        String sql = "select * from priority " +
                "where (title is null or " +
                "title = '' or " +
                "lower(title) like lower(%?%)) " +
                "order by title asc;";

        return jdbcTemplate.query(sql, new Object[]{text}, priorityMapper);
    }

    @Override
    public List<Priority> findAllByOrderByIdAsc() {
        String sql = "select * from priority order by priority_id asc;";

        return jdbcTemplate.query(sql, priorityMapper);
    }
}
