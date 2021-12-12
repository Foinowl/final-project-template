package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.StatDto;
import com.epam.rd.izh.entity.Stat;
import com.epam.rd.izh.service.StatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatController {
    private final StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }


    private final Long defaultId = 1l;

    @GetMapping("/stat")
    public ResponseEntity<StatDto> findById() {

        return  ResponseEntity.ok(StatDto.fromStat(statService.findById(defaultId)));
    }
}
