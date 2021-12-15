package com.epam.rd.izh.repository;

import com.epam.rd.izh.entity.Stat;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository {

    Stat findById(Long id);
}
