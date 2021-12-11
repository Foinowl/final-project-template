package com.epam.rd.izh.repository.impl;

import com.epam.rd.izh.entity.Category;
import com.epam.rd.izh.mapper.CategoryMapper;
import com.epam.rd.izh.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public List<Category> findAll() {
        String sql = "select * from category;";
        return  jdbcTemplate.query(sql, categoryMapper);
    }

    @Override
    public boolean insert(Category category) {
        String sql = "insert into category (title) VALUES(?);";

        return jdbcTemplate.update(sql , category.getTitle()) > 0;
    }

    @Override
    public boolean update(Category category) {
        String sql = "update category set title = ? where category_id = ?;";

        return jdbcTemplate.update(sql, category.getTitle(), category.getId()) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update("delete from category where category_id = ?;") > 0;
    }

    @Override
    public List<Category> findByTitle(String text) {
        String sql = "select * from category " +
                "where (title is null or " +
                "title = '' or " +
                "lower(title) like lower(%?%)) " +
                "order by title asc;";
        return jdbcTemplate.query(sql, new Object[]{text}, categoryMapper);
    }

    @Override
    public Category findById(Long id) {
        String sql = "select * from category where category_id = ?;";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, categoryMapper);
    }

    @Override
    public List<Category> findAllByOrderByTitleAsc() {
        String sql = "select * from category order by title asc;";

        return jdbcTemplate.query(sql, categoryMapper);
    }
}
