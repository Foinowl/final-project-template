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
public class Category {
    private Long id;
    private String title;
    private Long completedCount;
    private Long uncompletedCount;

    private Long userId;
    private String userLogin;
}