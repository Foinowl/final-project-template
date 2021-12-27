package com.epam.rd.izh.repository;

import com.epam.rd.izh.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository {
    List<Category> findAll();

    Category insert(Category category);

    Category update(Category category);

    boolean deleteById(Long id);

    List<Category> findByTitle(String text);

    Category findById(Long id);

    List<Category> findAllByOrderByTitleAsc();

    List<Category> findAllByUserId(Long id);
}
