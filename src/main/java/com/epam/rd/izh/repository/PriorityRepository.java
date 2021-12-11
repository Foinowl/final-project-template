package com.epam.rd.izh.repository;

import com.epam.rd.izh.entity.Priority;

import java.util.List;

public interface PriorityRepository {

    List<Priority> findAll();

    boolean insert(Priority priority);

    boolean update(Priority priority);

    boolean deleteById(Long id);

    Priority findById(Long id);

    List<Priority> findByTitle(String text);

    List<Priority> findAllByOrderByIdAsc();

}
