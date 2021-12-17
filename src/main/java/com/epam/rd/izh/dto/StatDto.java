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

    private Long userId;
    private String userTitle;

    public Stat toStat() {
        return Stat.builder()
                .id(id)
                .completedTotal(completedTotal)
                .uncompletedTotal(uncompletedTotal)
                .userId(userId)
                .userTitle(userTitle)
                .build();
    }

    public static StatDto fromStat(Stat stat) {
        return StatDto.builder()
                .id(stat.getId())
                .completedTotal(stat.getCompletedTotal())
                .uncompletedTotal(stat.getUncompletedTotal())
                .userId(stat.getUserId())
                .userTitle(stat.getUserTitle())
                .build();
    }
}
