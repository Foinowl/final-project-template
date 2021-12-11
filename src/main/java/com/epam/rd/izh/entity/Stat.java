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
public class Stat {
    private Long id;
    private Long completedTotal;
    private Long uncompletedTotal;
}
