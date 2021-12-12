package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.StatDto;
import com.epam.rd.izh.entity.Stat;
import com.epam.rd.izh.service.StatService;
import org.springframework.http.HttpStatus;
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

        try {
            return  ResponseEntity.ok(StatDto.fromStat(statService.findById(defaultId)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("something wrong on the bd side", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
