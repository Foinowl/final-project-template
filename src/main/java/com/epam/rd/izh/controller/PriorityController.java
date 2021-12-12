package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.CategoryDto;
import com.epam.rd.izh.dto.PriorityDto;
import com.epam.rd.izh.dto.PrioritySearchDto;
import com.epam.rd.izh.entity.Priority;
import com.epam.rd.izh.service.PriorityService;
import com.epam.rd.izh.validations.ResponseErrorValidation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/priority")
public class PriorityController {

    private final PriorityService priorityService;
    private final ResponseErrorValidation responseErrorValidation;


    public PriorityController(PriorityService priorityService, ResponseErrorValidation responseErrorValidation) {
        this.priorityService = priorityService;
        this.responseErrorValidation = responseErrorValidation;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PriorityDto>> findAll() {
        try {
            return ResponseEntity.ok(priorityService
                    .findAll()
                    .stream()
                    .map(PriorityDto::fromPriority)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("not found records", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody PriorityDto priority, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            return ResponseEntity.ok(PriorityDto.fromPriority(priorityService.add(priority)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("something wrong on the bd side", HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody PriorityDto priority, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            return ResponseEntity.ok(priorityService.update(priority));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("something wrong on the bd side", HttpStatus.NOT_ACCEPTABLE);
        }
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

        try {
            return ResponseEntity.ok(
                    priorityService.findByTitle(prioritySearchDto.getText())
                            .stream()
                            .map(PriorityDto::fromPriority)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("not found records", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
