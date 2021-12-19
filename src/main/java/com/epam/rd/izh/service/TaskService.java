package com.epam.rd.izh.service;

import com.epam.rd.izh.dto.TaskDto;
import com.epam.rd.izh.entity.AuthorizedUser;
import com.epam.rd.izh.entity.Priority;
import com.epam.rd.izh.entity.Task;
import com.epam.rd.izh.repository.CategoryRepository;
import com.epam.rd.izh.repository.PriorityRepository;
import com.epam.rd.izh.repository.TaskRepository;
import com.epam.rd.izh.repository.UserRepository;
import com.epam.rd.izh.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository repository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PriorityRepository priorityRepository;

    public TaskService(@Qualifier("taskRepositoryImpl") TaskRepository repository, UserRepository userRepository, CategoryRepository categoryRepository, PriorityRepository priorityRepository) {
        this.repository = repository;

        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.priorityRepository = priorityRepository;
    }

    public List<TaskDto> findAllTaskByUserId(Long id) {

        return toTaskDtoList(repository.findAllByUserId(id));
    }

    public TaskDto add(TaskDto task) {

        return toTaskDto(repository.insert(fromTaskDto(task)));
    }

    public TaskDto update(TaskDto task) {

        return toTaskDto(repository.update(fromTaskDto(task)));
    }

    public TaskDto updateByCompleted(TaskDto task) {
        return toTaskDto(repository.updateCompleted(fromTaskDto(task)));
    }

    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }

    public TaskDto findById(Long id) {

        return toTaskDto(repository.findById(id));
    }

    private Task fromTaskDto(TaskDto task) {
        AuthorizedUser user = userRepository.getUserByLogin(SecurityUtil.getCurrentUser().getUsername());
        Task task1 = task.toTask();
        task1.setIdUser(user.getId());
        return task1;
    }

    private List<TaskDto> toTaskDtoList(List<Task> task) {
        List<TaskDto> list = task
                .stream()
                .map(TaskDto::fromTask)
                .map(this::setParam)
                .collect(Collectors.toList());
        return list;
    }

    private TaskDto toTaskDto(Task task) {
        TaskDto taskDto = TaskDto.fromTask(task);
        taskDto.setLoginUser(SecurityUtil.getCurrentUser().getUsername());
        return taskDto;
    }

    private TaskDto setParam(TaskDto taskDto) {
        taskDto.setLoginUser(Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername());
        taskDto.setTitleCategory(categoryRepository.findById(taskDto.getIdCategory()).getTitle());
        Priority priority = priorityRepository.findById(taskDto.getIdPriority());
        taskDto.setTitlePriority(priority.getTitle());
        taskDto.setColor(priority.getColor());
        return taskDto;
    }
}
