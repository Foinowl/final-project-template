package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.CategoryDto;
import com.epam.rd.izh.dto.CategorySearchDto;
import com.epam.rd.izh.entity.Category;
import com.epam.rd.izh.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<CategoryDto> findAll() {
        return categoryService
                .findAll()
                .stream()
                .map(CategoryDto::fromCategory)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDto> add(@RequestBody CategoryDto category) {
        return ResponseEntity.ok(CategoryDto.fromCategory(categoryService.add(category)));
    }


    @PutMapping("/update")
    public ResponseEntity update(@RequestBody CategoryDto category) {
        return ResponseEntity.ok(categoryService.update(category));
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
        return ResponseEntity.ok(
                categoryService.findByTitle(categorySearchDto.getText())
                        .stream()
                        .map(CategoryDto::fromCategory)
                        .collect(Collectors.toList())
        );
    }
}
