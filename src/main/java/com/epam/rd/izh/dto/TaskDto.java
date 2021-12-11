package com.epam.rd.izh.dto;

import com.epam.rd.izh.entity.Task;
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
public class TaskDto {
    private Long id;
    private String title;
    private Integer completed;
    private Long idPriority;
    private Long idCategory;
    private Long idUser;

    public Task toTask() {
        return Task.builder()
                .id(id)
                .title(title)
                .completed(completed)
                .idPriority(idPriority)
                .idCategory(idCategory)
                .idUser(idUser)
                .build();
    }

    public static TaskDto fromTask(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .completed(task.getCompleted())
                .idPriority(task.getIdPriority())
                .idCategory(task.getIdCategory())
                .idUser(task.getIdUser())
                .build();
    }
}
