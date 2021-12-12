package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.TaskDto;
import com.epam.rd.izh.dto.TaskSearchDto;
import com.epam.rd.izh.entity.Task;
import com.epam.rd.izh.service.TaskService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> findAll() {
        return ResponseEntity.ok(
                taskService
                        .findAll()
                        .stream()
                        .map(TaskDto::fromTask)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/add")
    public ResponseEntity<TaskDto> add(@RequestBody TaskDto task) {
        return ResponseEntity.ok(TaskDto.fromTask(taskService.add(task)));

    }

    @PutMapping("/update")
    public ResponseEntity<TaskDto> update(@RequestBody TaskDto task) {

        taskService.update(task);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            taskService.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable Long id) {

        Task task = null;

        try{
            task = taskService.findById(id);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(TaskDto.fromTask(task));
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

        Page<Task> result = taskService.findByParams(text, completed, priorityId, categoryId, pageRequest);

        return ResponseEntity.ok(result);

    }
}
