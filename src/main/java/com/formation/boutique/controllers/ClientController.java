package com.formation.boutique.controllers;

import com.formation.boutique.entities.Client;
import com.formation.boutique.enumerations.Civilite;
import com.formation.boutique.enumerations.Droit;
import com.formation.boutique.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public ModelAndView home() {
        return new ModelAndView("pages/home")
                .addObject("title", "{clients.title}")
                .addObject("clients", clientService.getAll())
                .addObject("fragments", "fragments/clients/index");
    }

    @GetMapping("/clients/create")
    public ModelAndView getCreate() {
        return new ModelAndView("pages/home")
                .addObject("client", new Client())
                .addAllObjects(hashModel("create", "post"));
    }

    @PostMapping("/clients/create")
    public String postForm(@Valid @ModelAttribute(name = "client") Client client, BindingResult result, ModelMap model, RedirectAttributes attributes) {
        if (result.hasErrors() || !checkPassword(client)) {
            model.addAllAttributes(hashModel("create", "post"));
            return "pages/home";
        }

        clientService.save(client);

        attributes.addFlashAttribute("message", "L'utilisateur " + client.getEmail() + " est bien enregistré");

        return "redirect:/clients";
    }

    @GetMapping("/clients/update/{email}")
    public ModelAndView getUpdate(@PathVariable String email) {
        return new ModelAndView("pages/home")
                .addObject("client", clientService.getOne(email))
                .addAllObjects(hashModel("update", "put"));
    }


    @PutMapping("/clients/update")
    public String putForm(@Valid @ModelAttribute(name = "client") Client client,
                          BindingResult result,
                          ModelMap model,
                          RedirectAttributes attributes,
                          @RequestParam(value = "changePassword", required = false) Boolean changePassword) {
        System.out.println(changePassword);
        if (result.hasErrors() || !checkPassword(client)) {
            model.addAllAttributes(hashModel("update", "put"));
            return "pages/home";
        }

        if (changePassword != null && changePassword) {
            clientService.save(client);
        } else {
            String password = clientService.getOne(client.getEmail()).getPassword();
            client.setChangePassword(password);
            client.setPassword(null);
            client.setPasswordVerif(null);
            clientService.save(client);
        }
        System.out.println(client.toString());

        attributes.addFlashAttribute("message", "L'utilisateur " + client.getEmail() + " a bien été mis à jour");

        return "redirect:/clients";
    }

    @GetMapping("/clients/delete/{email}")
    public String putForm(@PathVariable String email, ModelMap model, RedirectAttributes attributes) {

        clientService.delete(clientService.getOne(email));

        attributes.addFlashAttribute("message", "L'utilisateur " + email + " a bien été mis à jour");

        return "redirect:/clients";
    }

    private Boolean checkPassword(Client client) {
        return client.getPassword().equals(client.getPasswordVerif());
    }

    private HashMap<String, Object> hashModel(String action, String method) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("password", "password.not.same");
        hashMap.put("countUser", clientService.getUserCount());
        hashMap.put("passwordVerif", "password.not.same");
        hashMap.put("title", "{clients.title}");
        hashMap.put("action", action);
        hashMap.put("method", method);
        hashMap.put("droit", Droit.values());
        hashMap.put("civilite", Civilite.values());
        hashMap.put("fragments", "fragments/clients/form");
        return hashMap;
    }

}
