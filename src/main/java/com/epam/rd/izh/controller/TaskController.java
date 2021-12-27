package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.TaskDto;
import com.epam.rd.izh.entity.Task;
import com.epam.rd.izh.service.TaskService;
import com.epam.rd.izh.service.UserService;
import com.epam.rd.izh.util.SecurityUtil;
import com.epam.rd.izh.validations.ResponseErrorValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final ResponseErrorValidation responseErrorValidation;
    private final UserService userService;


    public TaskController(TaskService taskService, ResponseErrorValidation responseErrorValidation, UserService userService) {
        this.taskService = taskService;
        this.responseErrorValidation = responseErrorValidation;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> findAll() {
        try {
            return ResponseEntity.ok(
                    taskService
                            .findAllTaskByUserId(
                                    userService
                                            .getUserIdByLogin(
                                                    SecurityUtil
                                                            .getCurrentUser()
                                                            .getUsername()
                                            )
                            )
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
            return ResponseEntity.ok(taskService.add(task));
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
            boolean complete = task.getCompleted() != null;

            if (!complete) {
                return new ResponseEntity(taskService.update(task), HttpStatus.OK);
            } else {
                return new ResponseEntity(taskService.updateByCompleted(task), HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("something wrong on the bd side", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        try {
            boolean isDelete = taskService.deleteById(id);
            return new ResponseEntity(isDelete, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable Long id) {

        TaskDto task = null;

        try {
            task = taskService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(task);
    }
}
