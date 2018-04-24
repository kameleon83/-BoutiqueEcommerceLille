package com.formation.boutique.controllers;

import com.formation.boutique.entities.Article;
import com.formation.boutique.entities.Image;
import com.formation.boutique.services.ArticleService;
import com.formation.boutique.services.CategorieService;
import com.formation.boutique.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.activation.MimetypesFileTypeMap;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ImageService imageService;
    private final CategorieService categorieService;
    private String UPLOAD_FOLDER = System.getProperty("user.dir") + "/src/main/resources/static/img/";

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

    @GetMapping("/articles/{code}")
    public ModelAndView getArticle(@PathVariable Long code) {
        return new ModelAndView("pages/home")
                .addObject("article", articleService.findArticleBycode(code))
                .addObject("title", "articles.title")
                .addObject("fragments", "fragments/articles/article");
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
                             @RequestParam("uploadImages") MultipartFile[] uploadImages, RedirectAttributes redirectAttributes,
                             ModelMap model) {
        if (articleResult.hasErrors() || !checkIfMultipartIsEmpty(uploadImages) || articleService.findArticleBycodeBool(article.getCode())) {
            if (!checkIfMultipartIsEmpty(uploadImages)) {
                model.addAttribute("errorFile", "Il faut au moins une image!");
            }
            if (articleService.findArticleBycodeBool(article.getCode())) {
                articleResult.rejectValue("code", "error.code.exist");
            }
            model.addAttribute("title", "articles.title");
            model.addAttribute("fragments", "fragments/articles/form");
            model.addAttribute("categories", categorieService.categorieListSelect());
            return "pages/home";
        }

        articleService.save(article);
        if (createFolder(UPLOAD_FOLDER + article.getCode().toString())) {
            saveImagesInFolderServer(uploadImages, article, article.getCode().toString());
        }

        return "redirect:/articles";
    }

    private Boolean checkIfImage(String file) {
        File f = new File(file);
        String mimetype = new MimetypesFileTypeMap().getContentType(f);
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }

    private Boolean checkIfMultipartIsEmpty(MultipartFile[] images) {
        for (MultipartFile image : images) {
            System.out.println(image.getOriginalFilename());
            if (image.getOriginalFilename().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void saveImagesInFolderServer(MultipartFile[] images, Article article, String nameDir) {
        Integer count = 0;
        for (MultipartFile uploadedFile : images) {
            if (checkIfImage(uploadedFile.getOriginalFilename())) {
                File file = new File(UPLOAD_FOLDER + nameDir + "/" + nameDir + "_" + count + getFileExtension(uploadedFile.getOriginalFilename()));
                try {
                    uploadedFile.transferTo(file);
                    Image img = new Image();
                    img.setArticle(article);
                    img.setLien("/img/" + nameDir + "/" + nameDir + "_" + count + getFileExtension(uploadedFile.getOriginalFilename()));
                    imageService.save(img);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            count++;
        }
    }

    private Boolean createFolder(String dir) {
        return new File(dir).mkdir();
    }

    private static String getFileExtension(String file) {
        return file.substring(file.lastIndexOf(".")); // return ---> ".java"
    }
}
