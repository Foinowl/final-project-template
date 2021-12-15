package com.epam.rd.izh.repository.impl;

import com.epam.rd.izh.entity.Stat;
import com.epam.rd.izh.mapper.StatMapper;
import com.epam.rd.izh.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StatRepositoryImpl implements StatRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    StatMapper statMapper;

    @Override
    public Stat findById(Long id) {
        return jdbcTemplate.queryForObject("select * from stat where stat_id = ?", new Object[]{id}, statMapper);
    }
}
