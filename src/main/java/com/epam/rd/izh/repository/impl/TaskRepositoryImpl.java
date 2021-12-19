package com.epam.rd.izh.repository.impl;

import com.epam.rd.izh.entity.Task;
import com.epam.rd.izh.mapper.TaskMapper;
import com.epam.rd.izh.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TaskMapper taskMapper;

    @Override
    public List<Task> findAll() {

        String sql = "select " +
                "task_id as taskId, " +
                "completed as completed, " +
                "t.category_id as categoryId, " +
                "t.priority_id as priorityId, " +
                "t.user_id as userId, " +
                "p.title as titlePriority, " +
                "c.title as titleCategory, " +
                "u.login as loginUser, " +
                "p.color as color, " +
                "t.date as dateTask, " +
                "t.title as title " +
                "from task as t " +
                "left join i_user as u " +
                "on t.user_id = u.user_id " +
                "left join category as c " +
                "on c.category_id  = t.category_id " +
                "left join priority as p " +
                "on p.priority_id = t.priority_id " +
                "order by t.task_id asc";
        return jdbcTemplate.query(sql, taskMapper);
    }

    @Override
    public List<Task> findAllByUserId(Long id) {

        String sql = "select " +
                "task_id as taskId, " +
                "completed as completed, " +
                "t.category_id as categoryId, " +
                "t.priority_id as priorityId, " +
                "t.user_id as userId, " +
                "p.title as titlePriority, " +
                "c.title as titleCategory, " +
                "u.login as loginUser, " +
                "p.color as color, " +
                "t.date as dateTask, " +
                "t.title as title " +
                "from task as t " +
                "left join i_user as u " +
                "on t.user_id = u.user_id " +
                "left join category as c " +
                "on c.category_id  = t.category_id " +
                "left join priority as p " +
                "on p.priority_id = t.priority_id " +
                "where u.user_id = ? " +
                "order by t.task_id asc";
        return jdbcTemplate.query(sql, new Object[]{id}, taskMapper);
    }

    @Override
    public Task insert(Task task) {

        String sql = "insert into task (title, priority_id, category_id, user_id, date) VALUES(?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"task_id"});
            ps.setString(1, task.getTitle());
            ps.setLong(2, task.getIdPriority());
            ps.setLong(3, task.getIdCategory());
            ps.setLong(4, task.getIdUser());
            ps.setDate(5, Date.valueOf(task.getDate()));

            return ps;
        }, keyHolder);

        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public Task update(Task task) {
        String sql = "update task set title = ?, priority_id = ?, category_id = ?, date = ? where task_id = ?;";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"task_id"});
            ps.setString(1, task.getTitle());
            ps.setLong(2, task.getIdPriority());
            ps.setLong(3, task.getIdCategory());
            ps.setDate(4, Date.valueOf(task.getDate()));
            ps.setLong(5, task.getId());
            return ps;
        }, keyHolder);

        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public Task updateCompleted(Task task) {
        String sql = "update task set completed = ? where task_id = ?;";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"task_id"});
            ps.setInt(1, task.getCompleted());
            ps.setLong(2, task.getId());
            return ps;
        }, keyHolder);

        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update("delete from task where task_id = ?;", id) > 0;
    }

    @Override
    public Task findById(Long id) {
        String sql = "select " +
                "task_id as taskId, " +
                "completed as completed, " +
                "t.category_id as categoryId, " +
                "t.priority_id as priorityId, " +
                "t.user_id as userId, " +
                "p.title as titlePriority, " +
                "c.title as titleCategory, " +
                "u.login as loginUser, " +
                "p.color as color, " +
                "t.date as dateTask, " +
                "t.title as title " +
                "from task as t " +
                "left join i_user as u " +
                "on t.user_id = u.user_id " +
                "left join category as c " +
                "on c.category_id  = t.category_id " +
                "left join priority as p " +
                "on p.priority_id = t.priority_id " +
                "where t.task_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, taskMapper);

    }
}
