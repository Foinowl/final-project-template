package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.CategoryDto;
import com.epam.rd.izh.dto.CategorySearchDto;
import com.epam.rd.izh.entity.Category;
import com.epam.rd.izh.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final ResponseErrorValidation responseErrorValidation;

    public CategoryController(CategoryService categoryService, ResponseErrorValidation responseErrorValidation) {
        this.categoryService = categoryService;
        this.responseErrorValidation = responseErrorValidation;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> findAll() {
        try {
            return ResponseEntity.ok(categoryService
                    .findAll()
                    .stream()
                    .map(CategoryDto::fromCategory)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("not found records", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/all/user/{id}")
    public ResponseEntity<List<CategoryDto>> findAllByUserId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService
                    .findAllByUserId(id)
                    .stream()
                    .map(CategoryDto::fromCategory)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("not found records", HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody CategoryDto category, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        try{
            return ResponseEntity.ok(CategoryDto.fromCategory(categoryService.add(category)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("something wrong on the bd side", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody CategoryDto category, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        try{
            return ResponseEntity.ok(categoryService.update(category));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("something wrong on the bd side", HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {

        Category category = null;

        try{
            category = categoryService.findById(id);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(CategoryDto.fromCategory(category));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        try {
            categoryService.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<CategoryDto>> search(@RequestBody CategorySearchDto categorySearchDto){
        try {
            return ResponseEntity.ok(
                    categoryService.findByTitle(categorySearchDto.getText())
                            .stream()
                            .map(CategoryDto::fromCategory)
                            .collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("not found records", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
