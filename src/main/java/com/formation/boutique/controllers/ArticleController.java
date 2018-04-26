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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

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
                .addAllObjects(addModelForm("fragments/articles/index", "create", "post"));

    }

    @GetMapping("/articles/{code}")
    public ModelAndView getArticle(@PathVariable Long code) {
        return new ModelAndView("pages/home")
                .addObject("article", articleService.findArticleBycode(code))
                .addAllObjects(addModelForm("fragments/articles/article", "create", "post"));
    }

    @GetMapping("/articles/create")
    public ModelAndView getCreate() {
        return new ModelAndView("pages/home")
                .addObject("article", new Article())
                .addAllObjects(addModelForm("fragments/articles/form", "create", "post"));
    }

    @GetMapping("/articles/update/{code}")
    public ModelAndView getUpdate(@PathVariable Long code) {
        return new ModelAndView("pages/home")
                .addObject("article", articleService.findArticleBycode(code))
                .addAllObjects(addModelForm("fragments/articles/form", "update", "put"));
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
            model.addAttribute("categories", categorieService.categorieListSelect());
            model.addAllAttributes(addModelForm("fragments/articles/form", "create", "post"));
            return "pages/home";
        }

        articleService.save(article);
        if (createFolder(UPLOAD_FOLDER + article.getCode().toString())) {
            saveImagesInFolderServer(uploadImages, article, article.getCode().toString());
        }

        return "redirect:/articles";
    }

    @PutMapping("/articles/update")
    public String putCreate(@Valid @ModelAttribute Article article, BindingResult articleResult,
                            @RequestParam("uploadImages") MultipartFile[] uploadImages, RedirectAttributes redirectAttributes,
                            ModelMap model) {
        if (articleResult.hasErrors()) {
            model.addAttribute("categories", String.join("", categorieService.categorieListSelect()));
            model.addAllAttributes(addModelForm("fragments/articles/form", "update", "put"));
            return "pages/home";
        }

        articleService.save(article);
        saveImagesInFolderServer(uploadImages, article, article.getCode().toString());

        return "redirect:/articles";
    }

    @GetMapping("/articles/delete/{code}")
    public String delete(@PathVariable Long code, ModelMap model, RedirectAttributes redirectAttributes) {
        if (deleteDirectory(new File(UPLOAD_FOLDER + code.toString()))) {
            articleService.delete(code);
            redirectAttributes.addFlashAttribute("message", "L'article a bien été supprimé!");
        } else {
            redirectAttributes.addFlashAttribute("message", "L'article n'a pas été supprimé");
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
            String md5 = md5Hash(nameDir+ System.nanoTime());
            if (checkIfImage(uploadedFile.getOriginalFilename())) {
                File file = new File(UPLOAD_FOLDER + nameDir + "/" + md5 + getFileExtension(uploadedFile.getOriginalFilename()));
                try {
                    uploadedFile.transferTo(file);
                    Image img = new Image();
                    img.setArticle(article);
                    img.setLien("/img/" + nameDir + "/" + md5 + getFileExtension(uploadedFile.getOriginalFilename()));
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

    private Map<String, Object> addModelForm(String fragments, String action, String method) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("fragments", fragments);
        attributes.put("title", "articles.title");
        attributes.put("action", action);
        attributes.put("method", method);
        attributes.put("categories", String.join("", categorieService.categorieListSelect()));
        return attributes;
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    private String md5Hash(String str){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(str.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private Boolean checkIfNumberImageExist(String lien, Integer count){
        String[] tab = lien.split("/");
        lien = tab[tab.length-1];
        System.out.println(lien);
        lien = lien.split("_")[1];
        System.out.println(lien);
        lien = lien.split("\\.")[0];
        System.out.println(lien);
        return lien.equals(count.toString());
    }

}
