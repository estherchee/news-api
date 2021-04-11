package se.sdaproject.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.sdaproject.ArticleNotFoundException;
import se.sdaproject.Models.Article;
import se.sdaproject.Repositories.ArticleRepository;

import java.util.List;

@RestController
public class ArticleController {
    ArticleRepository repository;

    @Autowired
    public ArticleController(ArticleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/articles")
    public List<Article> listAllArticles() {
        return repository.findAll();
    }

    @GetMapping("/articles/{id}")
    public Article getArticle(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }
}
