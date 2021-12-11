package com.epam.rd.izh.repository;

import com.epam.rd.izh.entity.Stat;

public interface StatRepository {

    Stat findById(Long id);
}
