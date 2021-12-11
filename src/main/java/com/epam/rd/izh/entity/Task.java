package com.epam.rd.izh.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {
    private Long id;
    private String title;
    private Integer completed;
    private Long idPriority;
    private Long idCategory;
    private Long idUser;
}