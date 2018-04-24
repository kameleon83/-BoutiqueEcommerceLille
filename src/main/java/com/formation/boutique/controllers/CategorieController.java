package com.formation.boutique.controllers;

import com.formation.boutique.entities.Categorie;
import com.formation.boutique.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CategorieController {
    private final CategorieService categorieService;


    @Autowired
    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }


    @GetMapping("/categories")
    public ModelAndView getAll() {
        return new ModelAndView("pages/home")
                .addObject("fragments", "fragments/categories/index")
                .addObject("title", "categories.title")
                .addObject("str", String.join("", categorieService.categorieListTree()));
    }

    @GetMapping("/categories/create")
    public ModelAndView getForm() {
        return new ModelAndView("pages/home")
                .addObject("cat", new Categorie())
                .addObject("title", "categories.title")
                .addObject("action", "create")
                .addObject("method", "post")
                .addObject("fragments", "fragments/categories/form")
                .addObject("categories", String.join("", categorieService.categorieListSelect()));
    }


    @PostMapping("/categories/create")
    public String postForm(@Valid @ModelAttribute(name = "cat") Categorie categorie, BindingResult bindingResult,
                           ModelMap model, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("fragments", "fragments/categories/form");
            model.addAttribute("title", "categories.title");
            model.addAttribute("action", "update");
            model.addAttribute("method", "put");
            model.addAttribute("categories", categorieService.getAll());
            return "pages/home";
        }
        categorieService.save(categorie);
        attributes.addFlashAttribute("message", "L'enregistrement s'est bien passé");
        return "redirect:/categories/create";
    }

    @GetMapping("/categories/update/{code}")
    public ModelAndView updateForm(@PathVariable Long code) {
                Categorie cat = categorieService.getOne(code);
        return new ModelAndView("pages/home")
                .addObject("cat", cat)
                .addObject("title", "categories.title")
                .addObject("action", "update")
                .addObject("method", "put")
                .addObject("fragments", "fragments/categories/form")
                .addObject("categories", String.join("", categorieService.categorieListSelect()));
    }
    @PutMapping("/categories/update")
    public String putForm(@Valid @ModelAttribute(name = "cat") Categorie categorie, BindingResult bindingResult,
                          ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("fragments", "fragments/categories/form");
            model.addAttribute("title", "categories.title");
            model.addAttribute("action", "update");
            model.addAttribute("method", "put");
            model.addAttribute("categories", categorieService.getAll());
            return "pages/home";
        }
        categorieService.save(categorie);
        return "redirect:/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        categorieService.delete(categorieService.getOne(id));
        redirectAttributes.addFlashAttribute("message", "La suppression s'est bien passée");
        return "redirect:/categories";
    }
}
