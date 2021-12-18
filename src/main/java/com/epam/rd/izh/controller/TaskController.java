package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.TaskDto;
import com.epam.rd.izh.dto.TaskSearchDto;
import com.epam.rd.izh.entity.Task;
import com.epam.rd.izh.service.TaskService;
import com.epam.rd.izh.validations.ResponseErrorValidation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final ResponseErrorValidation responseErrorValidation;


    public TaskController(TaskService taskService, ResponseErrorValidation responseErrorValidation) {
        this.taskService = taskService;
        this.responseErrorValidation = responseErrorValidation;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> findAll() {
        try {
            return ResponseEntity.ok(
                    taskService
                            .findAll()
                            .stream()
                            .map(TaskDto::fromTask)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("not found records", HttpStatus.NOT_ACCEPTABLE);

        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody TaskDto task, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            return ResponseEntity.ok(TaskDto.fromTask(taskService.add(task)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("something wrong on the bd side", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody TaskDto task, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        try {
            return new ResponseEntity(TaskDto.fromTask(taskService.update(task)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("something wrong on the bd side", HttpStatus.NOT_ACCEPTABLE);

        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        boolean response = taskService.deleteById(id);
        if (response) {
            return new ResponseEntity(response, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable Long id) {

        Task task = null;

        try {
            task = taskService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(TaskDto.fromTask(task));
    }


    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search(@RequestBody TaskSearchDto taskSearchDto) {

        String text = taskSearchDto.getTitle() != null ? taskSearchDto.getTitle() : null;

        Integer completed = taskSearchDto.getCompleted() != null ? taskSearchDto.getCompleted() : null;

        Long priorityId = taskSearchDto.getIdPriority() != null ? taskSearchDto.getIdPriority() : null;
        Long categoryId = taskSearchDto.getIdCategory() != null ? taskSearchDto.getIdCategory() : null;

        String sortColumn = taskSearchDto.getSortColumn() != null ? taskSearchDto.getSortColumn() : null;
        String sortDirection = taskSearchDto.getSortDirection() != null ? taskSearchDto.getSortDirection() : null;

        Integer pageNumber = taskSearchDto.getPageNumber() != null ? taskSearchDto.getPageNumber() : null;
        Integer pageSize = taskSearchDto.getPageSize() != null ? taskSearchDto.getPageSize() : null;


        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, sortColumn);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Task> result = null;
        try {
            result = taskService.findByParams(text, completed, priorityId, categoryId, pageRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("something wrong on the bd side", HttpStatus.NOT_ACCEPTABLE);

        }

        return ResponseEntity.ok(result);

    }
}
