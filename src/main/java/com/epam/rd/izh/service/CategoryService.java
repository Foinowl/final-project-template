package com.epam.rd.izh.service;

import com.epam.rd.izh.dto.CategoryDto;
import com.epam.rd.izh.entity.AuthorizedUser;
import com.epam.rd.izh.entity.Category;
import com.epam.rd.izh.repository.CategoryRepository;
import com.epam.rd.izh.repository.UserRepository;
import com.epam.rd.izh.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final UserRepository userRepository;

    public CategoryService(@Qualifier("categoryRepositoryImpl") CategoryRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public CategoryDto add(CategoryDto category) {

        return toCategoryDto(repository.insert(fromCategoryDto(category)));
    }

    public List<CategoryDto> findAllByUserId(Long id) {

        List<Category> categoryList = repository.findAllByUserId(id);
        return toCategoryDtoList(categoryList);
    }

    public CategoryDto update(CategoryDto category) {
        return toCategoryDto(repository.update(fromCategoryDto(category)));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<CategoryDto> findByTitle(String text) {
        AuthorizedUser user = userRepository.getUserByLogin(SecurityUtil.getCurrentUser().getUsername());
        List<Category> categoryList = repository.findByTitle(text)
                .stream()
                .filter(category -> Objects.equals(category.getIdUser(), user.getId()))
                .collect(Collectors.toList());
        return toCategoryDtoList(categoryList);
    }

    private Category fromCategoryDto(CategoryDto category) {
        AuthorizedUser user = userRepository.getUserByLogin(SecurityUtil.getCurrentUser().getUsername());
        Category category1 = category.toCategory();
        category1.setIdUser(user.getId());
        return category1;
    }

    private List<CategoryDto> toCategoryDtoList(List<Category> category) {
        List<CategoryDto> list = category
                .stream()
                .map(CategoryDto::fromCategory)
                .collect(Collectors.toList());
        list.forEach(categoryDto -> categoryDto.setUserLogin(Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername()));
        return list;
    }

    private CategoryDto toCategoryDto(Category category) {
        CategoryDto categoryDto = CategoryDto.fromCategory(category);
        categoryDto.setUserLogin(SecurityUtil.getCurrentUser().getUsername());
        return categoryDto;
    }
}
