package com.epam.rd.izh.dto;

import com.epam.rd.izh.entity.Priority;
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
public class PriorityDto {
    private Long id;
    private String title;
    private String color;

    public Priority toPriority() {
        return Priority.builder()
                .id(id)
                .title(title)
                .color(color)
                .build();
    }

    public static PriorityDto fromPriority(Priority priority) {
        return PriorityDto.builder()
                .id(priority.getId())
                .title(priority.getTitle())
                .color(priority.getColor())
                .build();
    }
}