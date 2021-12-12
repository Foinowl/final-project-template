package com.epam.rd.izh.service;

import com.epam.rd.izh.dto.PriorityDto;
import com.epam.rd.izh.entity.Priority;
import com.epam.rd.izh.repository.PriorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PriorityService {

    private final PriorityRepository repository;

    public PriorityService(PriorityRepository repository) {
        this.repository = repository;
    }

    public List<Priority> findAll() {
        return repository.findAllByOrderByIdAsc();
    }

    public Priority add(PriorityDto priority) {
        return repository.insert(priority.toPriority());
    }

    public Priority update(PriorityDto priority){
        return repository.update(priority.toPriority());
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public Priority findById(Long id){
        return repository.findById(id);
    }

    public List<Priority> findByTitle(String text){
        return repository.findByTitle(text);
    }

}
