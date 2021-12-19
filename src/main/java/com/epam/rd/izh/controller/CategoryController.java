package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.CategoryDto;
import com.epam.rd.izh.dto.CategorySearchDto;
import com.epam.rd.izh.service.CategoryService;
import com.epam.rd.izh.service.UserService;
import com.epam.rd.izh.util.SecurityUtil;
import com.epam.rd.izh.validations.ResponseErrorValidation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;

    public CategoryController(CategoryService categoryService, UserService userService, ResponseErrorValidation responseErrorValidation) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.responseErrorValidation = responseErrorValidation;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> findAllByUserId() {
        try {
            return ResponseEntity.ok(categoryService
                    .findAllByUserId(userService.getUserIdByLogin(SecurityUtil.getCurrentUser().getUsername())));
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
            return ResponseEntity.ok(categoryService.add(category));
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

        CategoryDto category = null;

        try{
            category = categoryService.findById(id);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(category);
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
                    categoryService.findByTitle(categorySearchDto.getText()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("not found records", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
