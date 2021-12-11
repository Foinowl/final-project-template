package com.epam.rd.izh.repository.impl;

import com.epam.rd.izh.entity.Task;
import com.epam.rd.izh.mapper.PriorityMapper;
import com.epam.rd.izh.mapper.TaskMapper;
import com.epam.rd.izh.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TaskRepositoryImpl implements TaskRepository
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TaskMapper taskMapper;

    @Override
    public List<Task> findAll() {
        String sql = "select * from task;";

        return jdbcTemplate.query(sql, taskMapper);
    }

    @Override
    public boolean insert(Task task) {
        String sql = "insert into task (title, completed, priority_id, category_id, user_id) VALUES(?, ?, ?, ?, ?);";

        return jdbcTemplate.update(sql,
                task.getTitle(),
                task.getCompleted(),
                task.getIdPriority(),
                task.getIdCategory(),
                task.getIdUser(),
                taskMapper) > 0;
    }

    @Override
    public boolean update(Task task) {
        String sql = "update task set title = ?, " +
                "completed = ?, priority_id = ?, category_id = ?" +
                "where task_id = ?;";

        return jdbcTemplate.update(sql,
                task.getTitle(),
                task.getCompleted(),
                task.getIdPriority(),
                task.getIdCategory(),
                task.getId()) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update("delete from task where task_id = ?;", id) > 0;

    }

    @Override
    public Page<Task> findByParams(String title, Integer completed, Long priorityId, Long categoryId, Pageable pageable) {
        String sql = "select * from task " +
                "where (lower(title) like %?%) and " +
                "completed = ?, " +
                "priority_id = ?, " +
                "category_id = ? " +
                "order by task_id ? limit ? offset ? ;";

        String dir = pageable.getSort().toList().get(0).getDirection().name();

        List<Task> taskList= jdbcTemplate.query(sql, new Object[]{
                title, completed, priorityId, categoryId, dir, pageable.getPageSize(), pageable.getOffset()
        }, taskMapper);

        return new PageImpl<Task>(taskList, pageable, count());
    }

    @Override
    public Task findById(Long id) {
        return jdbcTemplate.queryForObject("select * from task where task_id = ?", new Object[]{id}, taskMapper);

    }

    private int count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM task", Integer.class);
    }

}
