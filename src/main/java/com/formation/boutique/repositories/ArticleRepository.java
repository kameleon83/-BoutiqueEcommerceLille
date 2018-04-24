package com.formation.boutique.repositories;

import com.formation.boutique.entities.Article;
import com.formation.boutique.entities.Categorie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    Article findArticleBycode(Long code);

    Article findArticleByCategorieAndPromo(Categorie categorie, Integer promo);
}
