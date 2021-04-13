package se.sdaproject.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.Models.Article;
import se.sdaproject.Services.ArticleService;

import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public List<Article> listAllArticles() {
        return articleService.findAllArticles();
    }

    @GetMapping("/articles/{id}")
    public Article getArticle(@PathVariable long id) {
        return articleService.findArticleById(id);
    }

    @PostMapping("/articles")
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        Article createdArticle = articleService.addArticle(article);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdArticle);
    }

    @PutMapping("articles/{id}")
    public Article updateArticle(@RequestBody Article newArticle, @PathVariable Long id) {
        return articleService.updateArticle(newArticle, id);
    }

    @DeleteMapping("/articles/{id}")
    public void deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }
}
