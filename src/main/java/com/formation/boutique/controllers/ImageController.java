package com.formation.boutique.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;

@Controller
public class ImageController {

    //destination folder to upload the files
    public static String UPLOAD_FOLDER = System.getProperty("user.dir") + "/src/main/resources/static/img/";

    @RequestMapping("/images/create")
    public ModelAndView showUpload() {
        return new ModelAndView("pages/home")
                .addObject("fragments", "fragments/images/form");
    }

    @RequestMapping(value = "/images/create", method = RequestMethod.POST)
    public String uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles, RedirectAttributes redirectAttributes) throws IOException {
        for (MultipartFile uploadedFile : uploadingFiles) {
            if (uploadedFile.getOriginalFilename().isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Il n'y a aucuns fichiers!");
                return "redirect:/images/create";
            }
            if (checkIfImage(uploadedFile.getOriginalFilename())) {
                File file = new File(UPLOAD_FOLDER + uploadedFile.getOriginalFilename());
                uploadedFile.transferTo(file);
            }else{
                redirectAttributes.addFlashAttribute("message", "Les fichiers doivent être des images!!");
                return "redirect:/images/create";
            }
        }

        redirectAttributes.addFlashAttribute("message", "Les images sont bien enregistrés ;-)");
        return "redirect:/images/create";
    }

    public static Boolean checkIfImage(String file) {
        File f = new File(file);
        String mimetype = new MimetypesFileTypeMap().getContentType(f);
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }
}
