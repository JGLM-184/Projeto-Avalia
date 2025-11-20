package br.edu.fatecguarulhos.projetoavalia.config;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        int statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        if (statusCode == 403) {
            return "erro403";
        }

        return "redirect:/inicio";
    }
}
