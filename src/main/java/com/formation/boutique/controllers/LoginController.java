package com.formation.boutique.controllers;

import com.formation.boutique.entities.Client;
import com.formation.boutique.entities.Login;
import com.formation.boutique.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;

@Controller
public class LoginController {

    private final ClientService clientService;

    @Autowired
    public LoginController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients/login")
    public ModelAndView login() {
        return new ModelAndView("pages/home")
                .addObject("login", new Login())
                .addAllObjects(this.hashModel());
    }

    @PostMapping("/clients/login")
    public String postLogin(@Valid @ModelAttribute(name = "login") Login login,
                            BindingResult bindingResult,
                            ModelMap model, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        Client client = clientService.login(login.getEmail(), login.getPassword());
        if (bindingResult.hasErrors() || client == null) {
            model.addAttribute("message", "Veuillez vérifier votre email et mot de passe");
            model.addAllAttributes(this.hashModel());
            return "pages/home";
        }

        startSession(httpSession);
        httpSession.setAttribute("client", client);
        redirectAttributes.addFlashAttribute("message", "Bienvenue " + client.getCivilite() + " " + client.getNom());
        return "redirect:/";
    }

    @GetMapping("/clients/logout")
    public String logout(HttpSession httpSession, RedirectAttributes redirectAttributes) {
        if (httpSession.getAttribute("eBoutique") == null) {
            redirectAttributes.addFlashAttribute("message", "tu n'es pas loggé");
        } else {
            redirectAttributes.addFlashAttribute("message", "tu es bien déconnecté");
        }
        httpSession.invalidate();
        return "redirect:/";
    }

    public HashMap<String, Object> hashModel() {
        HashMap<String, Object> hasmap = new HashMap<>();
        hasmap.put("action", "login");
        hasmap.put("method", "post");
        hasmap.put("title", "{clients.title}");
        hasmap.put("fragments", "fragments/clients/login");
        return hasmap;
    }

    public void startSession(HttpSession httpSession) {
        String key = "eBoutique";
        if (httpSession.getAttribute(key) == null) {
            httpSession.setAttribute(key, new Date());
        }
        httpSession.setMaxInactiveInterval(60 * 60 * 12);
    }
}
