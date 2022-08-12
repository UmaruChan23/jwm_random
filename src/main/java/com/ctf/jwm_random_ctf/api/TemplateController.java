package com.ctf.jwm_random_ctf.api;

import com.ctf.jwm_random_ctf.service.RandomService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/")
public class TemplateController {

    private final RandomService randomService;

    private String username = "";

    public TemplateController(RandomService userService) {
        this.randomService = userService;
    }

    @GetMapping("/")
    public String getMainPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("message", "");
        if (request.getCookies() == null) {
            response.addCookie(
                    new Cookie(
                            "id",
                            Long.toHexString(new SecureRandom().nextLong() + System.currentTimeMillis())
                    )
            );
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("message", "");
        if (request.getCookies() == null) {
            response.addCookie(
                    new Cookie(
                            "id",
                            Long.toHexString(new SecureRandom().nextLong() + System.currentTimeMillis())
                    )
            );
        }
        return "login";
    }

    @GetMapping("/reset")
    public String getResetPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("message", "");
        if (request.getCookies() == null) {
            response.addCookie(
                    new Cookie(
                            "id",
                            Long.toHexString(new SecureRandom().nextLong() + System.currentTimeMillis())
                    )
            );
        }
        return "enterLoginPage";
    }

    @GetMapping("/code")
    public String getCodePage(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("message", "");
        if (request.getCookies() == null) {
            response.addCookie(
                    new Cookie(
                            "id",
                            Long.toHexString(new SecureRandom().nextLong() + System.currentTimeMillis())
                    )
            );
        }
        return "codePage";
    }

    @PostMapping("/login")
    public String login(Model model) {
        model.addAttribute("message", "Неверный логин или пароль!");
        return "login";
    }



    @PostMapping("/reset")
    public String getCode(Model model,
                          HttpServletRequest request,
                          String username) throws ExecutionException {
        if (username.equals("Admin")) {
            this.username = username;
            var cookie = request.getCookies()[0];
            randomService.generateNewPassword(cookie);
            return "redirect:/code";
        }
        model.addAttribute("message", "Пользователь с таким именем не найден!");
        return "enterLoginPage";
    }

    @PostMapping("/code")
    public String getCode(Model model,
                          String password,
                          HttpServletRequest request) throws ExecutionException, IOException {
        var cookie = request.getCookies()[0];

        if (Objects.equals(this.username, "Admin")) {

            try {

                if (randomService.generateNewPassword(cookie) == Long.parseLong(password)) {
                    model.addAttribute("flag", Files.readString(Path.of("/flag.txt")));
                    return "main";
                }
                model.addAttribute("message", "Введён неверный код! На Ваш номер был отправлен новый код");
                return "codePage";
            } catch (NumberFormatException ex) {
                model.addAttribute("message", "Введён неверный код! На Ваш номер был отправлен новый код");
                return "codePage";
            }

        }
        return "enterLoginPage";
    }


}
