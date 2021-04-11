package se.sdaproject.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.Exceptions.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PostMapping("/articles")
    public Article addArticle(@RequestBody Article article) {
        return repository.save(article);
    }

    @PutMapping("articles/{id}")
    public Article updateArticle(@RequestBody Article newArticle, @PathVariable Long id) {
        return repository.findById(id)
                .map(article -> {
                    if(newArticle.getTitle() != null) {
                        article.setTitle(newArticle.getTitle());
                    }
                    if(newArticle.getBody() != null) {
                        article.setBody(newArticle.getBody());
                    }
                    if(newArticle.getAuthorName() != null) {
                        article.setAuthorName(newArticle.getAuthorName());
                    }
                    return repository.save(article);
                })
                .orElseGet(() -> {
                    newArticle.setId(id);
                    return repository.save(newArticle);
                });
    }

    @DeleteMapping("/articles/{id}")
    public void deleteArticle(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
