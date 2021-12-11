package com.epam.rd.izh.dto;

import com.epam.rd.izh.entity.Stat;
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
public class StatDto {
    private Long id;
    private Long completedTotal;
    private Long uncompletedTotal;

    public Stat toStat() {
        return Stat.builder()
                .id(id)
                .completedTotal(completedTotal)
                .uncompletedTotal(uncompletedTotal)
                .build();
    }

    public static StatDto fromStat(Stat stat) {
        return StatDto.builder()
                .id(stat.getId())
                .completedTotal(stat.getCompletedTotal())
                .uncompletedTotal(stat.getUncompletedTotal())
                .build();
    }
}
