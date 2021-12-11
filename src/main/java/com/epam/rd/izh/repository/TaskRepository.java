package com.epam.rd.izh.repository;

import com.epam.rd.izh.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskRepository {

    List<Task> findAll();

    boolean insert(Task task);

    boolean update(Task task);

    boolean deleteById(Long id);

    Page<Task> findByParams(String title, Integer completed, Long priorityId, Long categoryId, Pageable pageable);

    Task findById(Long id);
}
