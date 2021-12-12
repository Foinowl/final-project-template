package com.epam.rd.izh.dto;

import com.epam.rd.izh.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDto {
    private Long id;
    @NotEmpty(message = "empty field")
    private String title;
    @NotEmpty(message = "empty field")
    private Integer completed;
    @NotEmpty(message = "empty field")
    private Long idPriority;
    @NotEmpty(message = "empty field")
    private Long idCategory;
    @NotEmpty(message = "empty field")
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
