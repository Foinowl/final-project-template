package com.epam.rd.izh.controller;

import static com.epam.rd.izh.util.StringConstants.ENG_GREETING;

import com.epam.rd.izh.dto.*;
import com.epam.rd.izh.entity.Task;
import com.epam.rd.izh.service.*;
import com.epam.rd.izh.util.UtilMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
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
  public String login(Authentication authentication, Model model) {

    String url = UtilMethod.determineTargetUrl(authentication);

    return "redirect:"+url;
  }

  @GetMapping("/admin-dashboard")
  public String admin(Authentication authentication, Model model) {
    String login = authentication.getName();

    AuthorizedUserDto authorizedUserDto = userService.getAuthorizedUserDto(login);

    List<UserDto> userDtoList = userService.getAllAuthorizedUsers(authorizedUserDto.getLogin())
            .stream()
            .filter(user -> !user.getRole().equals("ADMIN"))
            .map(UserDto::fromAuthorizedUser)
            .collect(Collectors.toList());

    model.addAttribute("user", authorizedUserDto);
    model.addAttribute("userList", userDtoList);
    return "admin";
  }

  @GetMapping("/user-dashboard")
  public String dashboardTodo(Authentication authentication, Model model) {
    String login = authentication.getName();

    AuthorizedUserDto authorizedUserDto = userService.getAuthorizedUserDto(login);

    List<TaskDto> taskDtoList = null;
    List<CategoryDto> categoryDtoList = null;
    StatDto statDto = null;
    List<PriorityDto> priorityDtoList = null;
    try {
      taskDtoList = taskService.findAllTaskByUserId(authorizedUserDto.getId())
              .stream()
              .map(TaskDto::fromTask)
              .collect(Collectors.toList());

      categoryDtoList = categoryService.findAll()
              .stream()
              .filter(category -> login.equals(category.getUserLogin()))
              .map(CategoryDto::fromCategory)
              .collect(Collectors.toList());

      priorityDtoList = priorityService
              .findAll()
              .stream()
              .map(PriorityDto::fromPriority)
              .collect(Collectors.toList());

      statDto = StatDto.fromStat(statService.findById(authorizedUserDto.getId()));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    model.addAttribute("user", authorizedUserDto);
    model.addAttribute("taskList", taskDtoList);
    model.addAttribute("categoryList", categoryDtoList);
    model.addAttribute("stat", statDto);
    model.addAttribute("priorityList", priorityDtoList);
    return "todo";
  }

}
