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
        return jdbcTemplate.queryForObject("select " +
                        "stat_id, " +
                        "completed_total, " +
                        "uncompleted_total, " +
                        "u.login as login, " +
                        "stat.user_id as user_id " +
                        "from " +
                        "stat left join i_user " +
                        "as u " +
                        "on u.user_id = stat.user_id where stat.user_id = ?",
                new Object[]{id}, statMapper);
    }

    @Override
    public boolean createStat(Long id) {
        return jdbcTemplate.update("insert into stat (user_id) values(?)", new Object[]{id}) > 0;
    }
}
