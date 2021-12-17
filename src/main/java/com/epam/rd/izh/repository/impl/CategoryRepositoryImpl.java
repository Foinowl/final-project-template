package com.epam.rd.izh.repository.impl;

import com.epam.rd.izh.entity.Category;
import com.epam.rd.izh.mapper.CategoryMapper;
import com.epam.rd.izh.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public List<Category> findAll() {
        String sql = "select " +
                "category_id, " +
                "title, " +
                "completed_count, " +
                "uncompleted_count, " +
                "u.login as userLogin, " +
                "u.user_id as userId " +
                "from category as c left join i_user as u " +
                "on u.user_id = c.user_id;";
        return jdbcTemplate.query(sql, categoryMapper);
    }

    @Override
    public Category insert(Category category) {
        String sql = "insert into category (title, user_id) VALUES(?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"category_id"});
            ps.setString(1, category.getTitle());
            ps.setLong(2, category.getIdUser());
            return ps;
        }, keyHolder);


        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public Category update(Category category) {
        String sql = "update category set title = ? where category_id = ?;";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"category_id"});
            ps.setString(1, category.getTitle());
            ps.setLong(2, category.getId());
            return ps;
        }, keyHolder);

        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update("delete from category where category_id = ?;") > 0;
    }

    @Override
    public List<Category> findByTitle(String text) {
        String sql = "select " +
                "category_id, " +
                "title, " +
                "completed_count, " +
                "uncompleted_count, " +
                "u.login as userLogin, " +
                "u.user_id as userId " +
                "from category category as c left join i_user as u on c.user_id = u.user_id" +
                "where (c.title is null or " +
                "c.title = '' or " +
                "lower(c.title) like lower(%?%)) " +
                "order by c.title asc;";
        return jdbcTemplate.query(sql, new Object[]{text}, categoryMapper);
    }

    @Override
    public Category findById(Long id) {
        String sql = "select " +
                "category_id, " +
                "title, " +
                "completed_count, " +
                "uncompleted_count, " +
                "u.login as userLogin, " +
                "u.user_id as userId " +
                "from category as c left join i_user as u on c.user_id = u.user_id where category_id = ?;";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, categoryMapper);
    }

    @Override
    public List<Category> findAllByOrderByTitleAsc() {
        String sql = "select " +
                "category_id, " +
                "title, " +
                "completed_count, " +
                "uncompleted_count, " +
                "u.login as userLogin, " +
                "u.user_id as userId " +
                "from category as c left join i_user as u on c.user_id = u.user_id order by c.title asc;";


        return jdbcTemplate.query(sql, categoryMapper);
    }

    @Override
    public List<Category> findAllByUserId(Long id) {
        String sql = "select " +
                "category_id, " +
                "title, " +
                "completed_count, " +
                "uncompleted_count, " +
                "u.login as userLogin, " +
                "u.user_id as userId " +
                "from category as c left join i_user as u " +
                "on u.user_id = c.user_id " +
                "where u.user_id = ?";
        return jdbcTemplate.query(sql, new Object[] {id}, categoryMapper);
    }
}
