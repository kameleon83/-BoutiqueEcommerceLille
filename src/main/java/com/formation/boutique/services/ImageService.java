package com.formation.boutique.services;

import com.formation.boutique.entities.Image;
import com.formation.boutique.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Iterable<Image> getAll(){
        return imageRepository.findAll();
    }

    public Image save(Image Image){
        return imageRepository.save(Image);
    }

}
