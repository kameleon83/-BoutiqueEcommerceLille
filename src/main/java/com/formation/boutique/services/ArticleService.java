package com.formation.boutique.services;

import com.formation.boutique.entities.Article;
import com.formation.boutique.repositories.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Iterable<Article> getAll() {
        return articleRepository.findAll();
    }

    public Article save(Article Article) {
        return articleRepository.save(Article);
    }

    public Boolean findArticleBycodeBool(Long code){
        return articleRepository.findArticleBycode(code) != null;
    }
    public Article findArticleBycode(Long code){
        return articleRepository.findArticleBycode(code);
    }

}
