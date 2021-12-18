package com.epam.rd.izh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskSearchDto {
    private String title;
    private Integer completed;
    private Long idPriority;
    private Long idCategory;

    private Integer pageNumber;
    private Integer pageSize;
}

