package com.formation.boutique.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientController {

    @GetMapping("/clients")
    public ModelAndView home() {
        return new ModelAndView("pages/home")
                .addObject("title", "{clients.titile}")
                .addObject("fragments", "fragments/clients/index");
    }
}
