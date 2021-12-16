package com.epam.rd.izh.controller;

import static com.epam.rd.izh.util.StringConstants.ENG_GREETING;

import com.epam.rd.izh.dto.Message;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  @GetMapping("/")
  public String login(Authentication authentication, Model model) {
    Message greetingMessage = new Message();
    greetingMessage.setMessage(ENG_GREETING + authentication.getName());

    model.addAttribute("message", greetingMessage.getMessage());
    return "index";
  }

  @GetMapping("/admin-dashboard")
  public String admin(Authentication authentication, Model model) {
    Message greetingMessage = new Message();
    greetingMessage.setMessage(ENG_GREETING + authentication.getName());

    model.addAttribute("message", greetingMessage.getMessage());
    return "admin";
  }

  @GetMapping("/user-dashboard")
  public String dashboardTodo(Authentication authentication, Model model) {
    Message greetingMessage = new Message();
    greetingMessage.setMessage(ENG_GREETING + authentication.getName());

    model.addAttribute("message", greetingMessage.getMessage());
    return "todo";
  }

}
