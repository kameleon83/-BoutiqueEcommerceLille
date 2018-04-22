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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    @GetMapping("/articles")
    public ModelAndView home() {
        return new ModelAndView("pages/home")
                .addObject("articles", articleService.getAll())
                .addObject("title", "articles.title")
                .addObject("fragments", "fragments/articles/index");
    }

    @GetMapping("/articles/create")
    public ModelAndView getCreate() {
        return new ModelAndView("pages/home")
                .addObject("article", new Article())
                .addObject("title", "articles.title")
                .addObject("fragments", "fragments/articles/form")
                .addObject("categories", categorieService.categorieListSelect());
    }

    @PostMapping("/articles/create")
    public String postCreate(@Valid @ModelAttribute Article article, BindingResult articleResult,
//                             @Valid @ModelAttribute List<Image> images, BindingResult imageResult,
                             @RequestParam("uploadingFiles") MultipartFile[] uploadingFiles, RedirectAttributes redirectAttributes,
                             ModelMap model) throws IOException {
        if (articleResult.hasErrors()) {
            model.addAttribute("title", "articles.title");
            model.addAttribute("fragments", "fragments/articles/form");
            model.addAttribute("categories", categorieService.categorieListSelect());
            return "pages/home";
        }
        articleService.save(article);
        // Vérifier si il existe déjà
        for (MultipartFile uploadedFile : uploadingFiles) {
            if (ImageController.checkIfImage(uploadedFile.getOriginalFilename())) {
                File file = new File(ImageController.UPLOAD_FOLDER + uploadedFile.getOriginalFilename());
                uploadedFile.transferTo(file);
                Image img = new Image();
                img.setArticle(article);
                img.setLien(uploadedFile.getOriginalFilename());
                imageService.save(img);
            }
        }
        return "redirect:/articles";
    }
}
