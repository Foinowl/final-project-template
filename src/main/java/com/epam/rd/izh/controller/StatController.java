package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.StatDto;
import com.epam.rd.izh.service.StatService;
import com.epam.rd.izh.service.UserService;
import com.epam.rd.izh.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatController {
    private final StatService statService;
    private final UserService userService;

    public StatController(StatService statService, UserService userService) {
        this.statService = statService;
        this.userService = userService;
    }

    @GetMapping("/stat")
    public ResponseEntity<Object> findById() {

        try {
            return ResponseEntity.ok(
                    statService.findByUserId(userService.getUserIdByLogin(SecurityUtil.getCurrentUser().getUsername()))
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("something wrong on the bd side", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
