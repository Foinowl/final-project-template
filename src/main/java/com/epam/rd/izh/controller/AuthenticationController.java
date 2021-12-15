package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.AuthorizedUserDto;
import com.epam.rd.izh.dto.LoginUserDto;
import com.epam.rd.izh.entity.Role;

import javax.validation.Valid;

import com.epam.rd.izh.service.RoleService;
import com.epam.rd.izh.service.UserService;
import com.epam.rd.izh.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * В аргументы контроллеров, которые обрабатывают запросы, можно указать дополнительные входные параметры: Например:
 * HttpServletRequest и HttpServletResponse. Данные объекты автоматически заполняться данными о реквесте и респонсе. Эти
 * данные можно использовать, например достать куки, сессию, хедеры итд.
 */

@Controller
public class AuthenticationController {

  @Autowired
  UserService userService;

  @Autowired
  RoleService roleService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  ResponseErrorValidation responseErrorValidation;

  /**
   * Метод, отвечающий за логику авторизации пользователя.
   * /login - определяет URL, по которому пользователь должен перейти, чтобы запустить данный метод-обработчик.
   */
  @GetMapping("/login")
  public String login(Model model, @RequestParam(required = false) String error) {
    if (error != null) {
      /**
       * Model представляет из себя Map коллекцию ключ-значения, распознаваемую View элементами MVC.
       * Добавляется String "invalid login or password!", с ключем "error_login_placeholder".
       * При создании View шаблона плейсхолдер ${error_login_placeholder} будет заменен на переданное значение.
       *
       * В класс Model можно передавать любые объекты, необходимые для генерации View.
       */
      model.addAttribute("error_login_placeholder", "invalid login or password!");
    }

    if(!model.containsAttribute("authorizationForm")){
      model.addAttribute("authorizationForm", new LoginUserDto());
    }
    /**
     * Контроллер возвращает String название JSP страницы.
     * В application.properties есть следующие строки:
     * spring.mvc.view.prefix=/WEB-INF/pages/
     * spring.mvc.view.suffix=.jsp
     * Spring MVC, используя суффикс и префикс, создаст итоговый путь к JSP: /WEB-INF/pages/login.jsp
     */
    return "login";
  }

  /**
   * Метод, отвечающий за логику регистрации пользователя.
   */
  @GetMapping("/registration")
  public String viewRegistration(Model model) {
    Map<String, String> mapRoles = roleService
            .getAllRoles()
            .stream()
            .collect(Collectors.toMap(Role::getTitle, Role::getTitle));

    model.addAttribute("roles", mapRoles);
    model.addAttribute("errors", new HashMap<>());


    if(!model.containsAttribute("registrationForm")){
      model.addAttribute("registrationForm", new AuthorizedUserDto());
    }
    return "register";
  }

  /**
   * Метод, отвечающий за подтверждение регистрации пользователя и сохранение данных в репозиторий или DAO.
   */
  @PostMapping("/registration/proceed")
  public String processRegistration(@Valid @ModelAttribute("registrationForm") AuthorizedUserDto registeredUser,
      BindingResult bindingResult, RedirectAttributes redirectAttributes) {


    if (bindingResult.hasErrors()) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        redirectAttributes.addFlashAttribute("errors", errors.getBody());
      redirectAttributes.addFlashAttribute("registrationForm", registeredUser);

      return "redirect:/registration";
      }

    userService.addAuthorizedUser(userService.getAuthorizedUser(registeredUser));
    /**
     * В случае успешной регистрации редирект можно настроить на другой энд пойнт.
     */
    return "redirect:/login";
  }
}