package com.epam.rd.izh.repository;

import com.epam.rd.izh.entity.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();

    boolean insert(Category category);

    boolean update(Category category);

    boolean deleteById(Long id);

    List<Category> findByTitle(String text);

    Category findById(Long id);

    List<Category> findAllByOrderByTitleAsc();
}
