package com.epam.rd.izh.controller;

import com.epam.rd.izh.model.PageImplBean;
import com.epam.rd.izh.dto.*;
import com.epam.rd.izh.service.*;
import com.epam.rd.izh.util.SecurityUtil;
import com.epam.rd.izh.util.StringConstants;
import com.epam.rd.izh.util.UtilMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class IndexController {


    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    StatService statService;

    @Autowired
    PriorityService priorityService;

    @GetMapping("/")
    public String login(Authentication authentication) {

        String url = UtilMethod.determineTargetUrl(authentication);

        return "redirect:" + url;
    }

    @GetMapping("/admin-dashboard")
    public String admin(Model model, @RequestParam(name="page", required=false) Integer page) {
        AuthorizedUserDto authorizedUserDto = userService.getAuthorizedUserDto(SecurityUtil.getCurrentUser().getUsername());

        if (Objects.isNull(page)) {
            page = StringConstants.DEFAULT_PAGE;
        }
        page -= 1;


        Sort sort = Sort.by(Sort.Direction.ASC, StringConstants.USER_SORT_BY_COL);

        PageRequest pageRequest = PageRequest.of(page, StringConstants.PER_PAGE_SIZE, sort);

        PageImplBean list = userService.getAllUsers(authorizedUserDto.getLogin(), pageRequest);

        model.addAttribute("user", authorizedUserDto);
        model.addAttribute("userPage", list);
        return "admin";
    }

    @GetMapping("/user-dashboard")
    public String dashboardTodo(Model model) {
        UserDto userDto = UserDto.fromAuthorizedUser(
                Objects.requireNonNull(userService
                        .getUserByLogin(SecurityUtil.getCurrentUser().getUsername()))
        );

        List<TaskDto> taskDtoList = null;
        List<CategoryDto> categoryDtoList = null;
        StatDto statDto = null;
        List<PriorityDto> priorityDtoList = null;
        try {
            taskDtoList = taskService.findAllTaskByUserId(userDto.getId());

            categoryDtoList = categoryService.findAllByUserId(userDto.getId());

            priorityDtoList = priorityService
                    .findAll()
                    .stream()
                    .map(PriorityDto::fromPriority)
                    .collect(Collectors.toList());

            statDto = statService.findByUserId(userDto.getId());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        model.addAttribute("user", userDto);
        model.addAttribute("taskList", taskDtoList);
        model.addAttribute("categoryList", categoryDtoList);
        model.addAttribute("stat", statDto);
        model.addAttribute("priorityList", priorityDtoList);
        return "todo";
    }

}
