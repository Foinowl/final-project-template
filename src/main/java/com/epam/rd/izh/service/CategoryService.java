package com.epam.rd.izh.service;

import com.epam.rd.izh.entity.Category;
import com.epam.rd.izh.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }


    public List<Category> findAll() {
        return repository.findAll();
    }

    public boolean add(Category category) {
        return repository.insert(category);
    }

    public boolean update(Category category){
        return repository.update(category);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public List<Category> findByTitle(String text){
        return repository.findByTitle(text);
    }

    public Category findById(Long id){
        return repository.findById(id);
    }

    public List<Category> findAllByOrderByTitleAsc(){
        return repository.findAllByOrderByTitleAsc();
    }

}
