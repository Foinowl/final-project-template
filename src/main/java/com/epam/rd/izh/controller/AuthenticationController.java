package com.epam.rd.izh.controller;

import com.epam.rd.izh.dto.AuthorizedUserDto;
import com.epam.rd.izh.dto.LoginUserDto;
import com.epam.rd.izh.entity.Role;

import javax.validation.Valid;

import com.epam.rd.izh.service.RoleService;
import com.epam.rd.izh.service.SecurityService;
import com.epam.rd.izh.service.StatService;
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


    @Autowired
    SecurityService securityService;

    @Autowired
    StatService statService;


    /**
     * Метод, отвечающий за логику авторизации пользователя.
     * /login - определяет URL, по которому пользователь должен перейти, чтобы запустить данный метод-обработчик.
     */
    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("error_login_placeholder", "invalid login or password!");
        }

        if (!model.containsAttribute("authorizationForm")) {
            model.addAttribute("authorizationForm", new LoginUserDto());
        }

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

        if (!model.containsAttribute("registrationForm")) {
            model.addAttribute("registrationForm", new AuthorizedUserDto());
        }
        if (!model.containsAttribute("errors")) {
            model.addAttribute("errors", new HashMap<>());
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

        statService.createStatForUser(registeredUser.getId());
        securityService.autoLogin(registeredUser.getLogin(), registeredUser.getPassword());
        return "redirect:/";
    }
}