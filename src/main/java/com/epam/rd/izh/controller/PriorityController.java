package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.PriorityDto;
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
}
