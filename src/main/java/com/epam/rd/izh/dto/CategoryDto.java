package com.epam.rd.izh.dto;


import com.epam.rd.izh.entity.Category;
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
public class CategoryDto {
    private Long id;
    private String title;
    private Long completedCount;
    private Long uncompletedCount;

    public Category toCategory() {
        return Category.builder()
                .id(id)
                .title(title)
                .completedCount(completedCount)
                .uncompletedCount(uncompletedCount)
                .build();
    }

    public static CategoryDto fromCategory(Category category) {
        return  CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .completedCount(category.getCompletedCount())
                .uncompletedCount(category.getUncompletedCount())
                .build();
    }
}
