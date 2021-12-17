package com.epam.rd.izh.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {
    private Long id;
    private String title;
    private Integer completed;
    private LocalDate date;
    private Long idPriority;
    private Long idCategory;
    private Long idUser;

    private String titlePriority;
    private String titleCategory;
    private String loginUser;
    private String color;
}