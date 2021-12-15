package com.epam.rd.izh.service;

import com.epam.rd.izh.entity.Stat;
import com.epam.rd.izh.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StatService {

    private final StatRepository repository;

    @Autowired
    public StatService(@Qualifier("statRepositoryImpl") StatRepository repository) {
        this.repository = repository;
    }


    public Stat findById(Long id){
        return repository.findById(id);
    }

}
