package com.formation.boutique.services;

import com.formation.boutique.entities.Categorie;
import com.formation.boutique.repositories.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    @Autowired
    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public Collection<Categorie> getAll() {
        return (Collection<Categorie>) categorieRepository.findAll();
    }

    public Categorie save(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Iterable<Categorie> findAllByOrderByArticlesAsc() {
        return categorieRepository.findAllByOrderByArticlesAsc();
    }

    public List<String> categorieListTree() {
        Collection<Categorie> cats = this.getAll();
        List<String> str = new ArrayList<>();
        str.add("<ul>");
        for (Categorie c : cats) {
            if (!ifexist(str, c.getNom())) {
                str.add("<li>" + c.getNom() + "</li>");
                this.subCat(c, "<ul>", str);
            }
        }
        str.add("</ul>");
        return str;
    }

    private void subCat(Categorie categorie, String submark, List<String> categories) {
        Collection<Categorie> cats = categorieRepository.findCategoriesByParentOrderByNom(categorie);
        if (cats.size() > 0) {
            categories.add(submark);
            for (Categorie c : cats) {
                categories.add("<li>" + c.getNom() + "</li>");
                subCat(c, submark, categories);
            }
            categories.add("</ul>");
        }
    }

    public List<String> categorieListSelect() {
        Collection<Categorie> cats = this.getAll();
        List<String> str = new ArrayList<>();
        for (Categorie c : cats) {
            if (!ifexist(str, c.getNom())){
                str.add("<option value='" + c.getId() + "'>" + c.getNom() + "</option>");
                this.subCatSelect(c, "", str);
            }
        }
        return str;
    }

    private Boolean ifexist(List<String> list, String name){
        for (String str:list) {
            if (Pattern.compile(name).matcher(str).find()) {
                return true;
            }
        }
        return false;
    }

    private void subCatSelect(Categorie categorie, String submark, List<String> categories) {
        Collection<Categorie> cats = categorieRepository.findCategoriesByParentOrderByNom(categorie);
        if (cats.size() > 0) {
            submark += "&emsp;";
            for (Categorie c : cats) {
                categories.add("<option value='" + c.getId() + "'>" + submark + c.getNom() + "</option>");
                subCatSelect(c, submark, categories);
            }
        }
    }

}
