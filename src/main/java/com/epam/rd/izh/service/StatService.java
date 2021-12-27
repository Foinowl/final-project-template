package com.epam.rd.izh.service;

import com.epam.rd.izh.dto.StatDto;
import com.epam.rd.izh.entity.Stat;
import com.epam.rd.izh.repository.StatRepository;
import com.epam.rd.izh.util.SecurityUtil;
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


    public StatDto findByUserId(Long id){

        return StatToStatDto(repository.findById(id));
    }

    public boolean createStatForUser(Long id) {
        return repository.createStat(id);
    }


    private StatDto StatToStatDto(Stat stat) {
        StatDto statDto = StatDto.fromStat(stat);
        statDto.setLoginUser(SecurityUtil.getCurrentUser().getUsername());
        return statDto;
    }
}
