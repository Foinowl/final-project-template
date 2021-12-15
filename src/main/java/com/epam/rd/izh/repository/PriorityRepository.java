package com.epam.rd.izh.repository;

import com.epam.rd.izh.entity.Priority;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriorityRepository {

    List<Priority> findAll();

    Priority insert(Priority priority);

    Priority update(Priority priority);

    boolean deleteById(Long id);

    Priority findById(Long id);

    List<Priority> findByTitle(String text);

    List<Priority> findAllByOrderByIdAsc();

}
