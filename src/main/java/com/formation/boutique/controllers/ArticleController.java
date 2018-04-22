package com.formation.boutique.controllers;

import javax.validation.Valid;

import com.formation.boutique.entities.Article;
import com.formation.boutique.entities.Categorie;
import com.formation.boutique.entities.Image;
import com.formation.boutique.services.ArticleService;
import com.formation.boutique.services.CategorieService;
import com.formation.boutique.services.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ImageService imageService;
    private final CategorieService categorieService;

    @Autowired
    public ArticleController(ArticleService articleService, ImageService imageService,
            CategorieService categorieService) {
        this.articleService = articleService;
        this.imageService = imageService;
        this.categorieService = categorieService;
    }

    @GetMapping("/form")
    public String getForm(ModelMap model) {
        model.addAttribute("title", "Bonjour");
        model.addAttribute("h1", "Hello world");
        model.addAttribute("cat", new Categorie());
        model.addAttribute("article", new Article());

        return "pages/formulaire.html";
    }

    @GetMapping("/articles/create")
    public ModelAndView getCreate() {
        return new ModelAndView("pages/articles/form").addObject("article", new Article());
    }

    @PostMapping("/articles/create")
    public String postCreate(@Valid @ModelAttribute Article article, BindingResult articleResult,
            @Valid @ModelAttribute Categorie categorie, BindingResult categorieResult,
            @Valid @ModelAttribute Image image, BindingResult imageResult, ModelMap model) {
        if (articleResult.hasErrors() || categorieResult.hasErrors() || imageResult.hasErrors()) {
            return "pages/articles/form";
        }

        return "redirect:/articles";
    }
}
