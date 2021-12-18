package com.epam.rd.izh.service;

import com.epam.rd.izh.dto.TaskDto;
import com.epam.rd.izh.entity.Task;
import com.epam.rd.izh.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(@Qualifier("taskRepositoryImpl") TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll() {
        return repository.findAll();
    }

    public List<Task> findAllTaskByUserId(Long id) {
        return repository.findAllByUserId(id);
    }

    public Task add(TaskDto task) {
        return repository.insert(task.toTask());
    }

    public Task update(TaskDto task){
        return repository.update(task.toTask());
    }

    public Task updateByCompleted(TaskDto task) {
        return repository.updateCompleted(task.toTask());
    }

    public boolean deleteById(Long id){
        return repository.deleteById(id);
    }

    public Page<Task> findByParams(String text, Integer completed, Long priorityId, Long categoryId, PageRequest paging){
        return repository.findByParams(text, completed, priorityId, categoryId, paging);
    }

    public Task findById(Long id){
        return repository.findById(id);
    }
}
