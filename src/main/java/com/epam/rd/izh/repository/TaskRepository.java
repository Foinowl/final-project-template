package com.epam.rd.izh.repository;

import com.epam.rd.izh.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository {

    List<Task> findAll();

    List<Task> findAllByUserId(Long id);

    Task insert(Task task);

    Task update(Task task);

    boolean deleteById(Long id);

    Task findById(Long id);

    Task updateCompleted(Task task);
}
