package ru.davyd.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.davyd.NauJava.entities.User;
import ru.davyd.NauJava.service.UserService;

/**
 * Контроллер для обработки регистрации пользователей
 * Предоставляет эндпоинты для отображения формы регистрации и обработки данных регистрации
 */
@Controller
public class RegistrationController {

    /**
     * Сервис для работы с пользователями
     * Используется для добавления новых пользователей в базу данных
     */
    private final UserService userService;

    /**
     * Конструктор контроллера
     * Внедряет зависимость сервиса через конструктор
     *
     * @param userService Сервис для работы с пользователями
     */
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Эндпоинт для отображения формы регистрации
     * Добавляет новый объект User в модель для отображения на форме
     *
     * @param model Модель для передачи данных на HTML-страницу
     * @return Имя HTML-шаблона для отображения формы регистрации
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Эндпоинт для обработки данных регистрации
     * Создает нового пользователя на основе данных из формы и добавляет его в базу данных
     * После успешной регистрации перенаправляет пользователя на страницу логина
     *
     * @param username Имя пользователя из формы регистрации
     * @param email Email пользователя из формы регистрации
     * @param password Пароль пользователя из формы регистрации
     * @param isAdmin Флаг, указывающий, является ли пользователь администратором
     * @return Redirect на страницу логина
     */
    @PostMapping("/register")
    public String registerUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(required = false, defaultValue = "false") boolean isAdmin
    ) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setAdmin(isAdmin);

        userService.addUser(user);
        return "redirect:/login";
    }
}

