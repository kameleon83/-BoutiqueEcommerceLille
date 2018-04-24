package com.formation.boutique.services;

import com.formation.boutique.entities.Categorie;
import com.formation.boutique.repositories.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    @Autowired
    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public Collection<Categorie> getAll() {
        return categorieRepository.findByParentIsNull();
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
        for (Categorie c : cats) {
            str.add("<div class='col-md-6'><ul class='list-group d-block p-1'>");
            str.add("<li class='list-group-item d-flex justify-content-between align-items-center list-group-item-dark'>" +
                    c.getNom() +
                    "<div><a class='cat-img' href='/categories/update/" + c.getId() + "' data-toggle='tooltip' data-placement='top' title='Update'><img src='/img/site/update.png' alt='update'></a>" +
                    "<a href='/categories/delete/" + c.getId() + "' data-toggle='tooltip' data-placement='top' title='Delete'><img src='/img/site/trash.png' alt='delete'></a></div>" +
                    "</li>");
            this.subCat(c, "<ul>", str);
            str.add("</ul></div>");
        }
        return str;
    }

    private void subCat(Categorie categorie, String submark, List<String> categories) {
        Collection<Categorie> cats = categorieRepository.findCategoriesByParentOrderByNom(categorie);
        if (cats.size() > 0) {
            categories.add(submark);
            for (Categorie c : cats) {
                categories.add("<li class='list-group-item d-flex justify-content-between align-items-center'>" +
                        c.getNom() +
                        "<div><a class='cat-img' href='/categories/update/" + c.getId() + "' data-toggle='tooltip' data-placement='top' title='Update'><img src='/img/site/update.png' alt='update'></a>" +
                        "<a href='/categories/delete/" + c.getId() + "' data-toggle='tooltip' data-placement='top' title='Delete'><img src='/img/site/trash.png' alt='delete'></a></div>" +
                        "</li>");
                subCat(c, submark, categories);
            }
            categories.add("</ul>");
        }
    }

    public List<String> categorieListSelect() {
        Collection<Categorie> cats = this.getAll();
        List<String> str = new ArrayList<>();
        for (Categorie c : cats) {
//            if (!ifexist(str, c.getNom())){
            str.add("<option value=''>---</option>");
            str.add("<option value='" + c.getId() + "' data-id='" + c.getId() + "'>" + c.getNom() + "</option>");
            this.subCatSelect(c, "", str);
//            }
        }
        return str;
    }

    private void subCatSelect(Categorie categorie, String submark, List<String> categories) {
        Collection<Categorie> cats = categorieRepository.findCategoriesByParentOrderByNom(categorie);
        if (cats.size() > 0) {
            submark += "&emsp;";
            for (Categorie c : cats) {
                categories.add("<option value='" + c.getId() + "' data-id='" + c.getId() + "'>" + submark + c.getNom() + "</option>");
                subCatSelect(c, submark, categories);
            }
        }
    }

    public Categorie getOne(Long id) {
        return categorieRepository.findCategorieById(id);
    }

    public void delete(Categorie categorie) {
        categorieRepository.delete(categorie);
    }

}
