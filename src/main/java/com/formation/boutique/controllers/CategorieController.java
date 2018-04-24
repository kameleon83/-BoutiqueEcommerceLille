package com.formation.boutique.controllers;

import com.formation.boutique.entities.Categorie;
import com.formation.boutique.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
                .addAllObjects(addModelForm("fragments/categories/form", "create", "post"))
                .addObject("categories", String.join("", categorieService.categorieListSelect()));
    }


    @PostMapping("/categories/create")
    public String postForm(@Valid @ModelAttribute(name = "cat") Categorie categorie, BindingResult bindingResult,
                           ModelMap model, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(addModelForm("fragments/categories/form","create", "post"));
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
                .addAllObjects(addModelForm("fragments/categories/form", "update", "put"))
                .addObject("categories", String.join("", categorieService.categorieListSelect()));
    }
    @PutMapping("/categories/update")
    public String putForm(@Valid @ModelAttribute(name = "cat") Categorie categorie, BindingResult bindingResult,
                          ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(addModelForm("fragments/categories/form","update", "put"));
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

    private Map<String, Object> addModelForm(String fragments, String action,String method){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("fragments", fragments);
        attributes.put("title", "categories.title");
        attributes.put("action", action);
        attributes.put("method", method);
        attributes.put("categories", String.join("", categorieService.categorieListSelect()));
        return attributes;
    }

}
