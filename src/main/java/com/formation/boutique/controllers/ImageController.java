package com.formation.boutique.controllers;

import com.formation.boutique.entities.Image;
import com.formation.boutique.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

@Controller
public class ImageController {

    private final ImageService imageService;

    //destination folder to upload the files
    public static String UPLOAD_FOLDER = System.getProperty("user.dir") + "/src/main/resources/static";

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

//    @RequestMapping("/images/create")
//    public ModelAndView showUpload() {
//        return new ModelAndView("pages/home")
//                .addObject("fragments", "fragments/images/form");
//    }
//
//    @RequestMapping(value = "/images/create", method = RequestMethod.POST)
//    public String uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles, RedirectAttributes redirectAttributes) throws IOException {
//        for (MultipartFile uploadedFile : uploadingFiles) {
//            if (uploadedFile.getOriginalFilename().isEmpty()) {
//                redirectAttributes.addFlashAttribute("message", "Il n'y a aucun fichier!");
//                return "redirect:/images/create";
//            }
//            if (checkIfImage(uploadedFile.getOriginalFilename())) {
//                File file = new File(UPLOAD_FOLDER + uploadedFile.getOriginalFilename());
//                uploadedFile.transferTo(file);
//            }else{
//                redirectAttributes.addFlashAttribute("message", "Les fichiers doivent être des images!!");
//                return "redirect:/images/create";
//            }
//        }
//
//        redirectAttributes.addFlashAttribute("message", "Les images sont bien enregistrées ;-)");
//        return "redirect:/images/create";
//    }

    public static Boolean checkIfImage(String file) {
        File f = new File(file);
        String mimetype = new MimetypesFileTypeMap().getContentType(f);
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }

    @GetMapping("/images/delete/{code}/{codeArticle}")
    public String delete(@PathVariable Long code, @PathVariable Long codeArticle, ModelMap model, RedirectAttributes redirectAttributes) {
        Image image = imageService.getOne(code);
        System.out.println(UPLOAD_FOLDER + image.getLien());
        File img = new File(UPLOAD_FOLDER + image.getLien());
        if (img.delete()){
            imageService.delete(code);
            redirectAttributes.addFlashAttribute("message", "L'image a bien été supprimé!");
        }else{
            redirectAttributes.addFlashAttribute("message", "L'image n'a pas été supprimé");
        }

        System.out.println("Deletion successful.");
        return "redirect:/articles/update/" + codeArticle;
    }
}
