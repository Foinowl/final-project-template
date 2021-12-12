package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.CategoryDto;
import com.epam.rd.izh.dto.PriorityDto;
import com.epam.rd.izh.dto.PrioritySearchDto;
import com.epam.rd.izh.entity.Priority;
import com.epam.rd.izh.service.PriorityService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/priority")
public class PriorityController {

    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @GetMapping("/all")
    public List<PriorityDto> findAll() {

        return priorityService
                .findAll()
                .stream()
                .map(PriorityDto::fromPriority)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<PriorityDto> add(@RequestBody PriorityDto priority) {
        return ResponseEntity.ok(PriorityDto.fromPriority(priorityService.add(priority)));

    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody PriorityDto priority) {
        return ResponseEntity.ok(priorityService.update(priority));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PriorityDto> findById(@PathVariable Long id) {

        Priority priority = null;

        try{
            priority = priorityService.findById(id);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(PriorityDto.fromPriority(priority));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        try {
            priorityService.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<PriorityDto>> search(@RequestBody PrioritySearchDto prioritySearchDto){

        return ResponseEntity.ok(
                priorityService.findByTitle(prioritySearchDto.getText())
                        .stream()
                        .map(PriorityDto::fromPriority)
                        .collect(Collectors.toList())
        );
    }
}
