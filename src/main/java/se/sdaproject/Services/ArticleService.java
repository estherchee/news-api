package se.sdaproject.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.sdaproject.Exceptions.ResourceNotFoundException;
import se.sdaproject.Models.Article;
import se.sdaproject.Repositories.ArticleRepository;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    public Article findArticleById(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Article addArticle(Article article) {
        return articleRepository.save(article);
    }

    public Article updateArticle(Article newArticle, long id) {
        Article article = findArticleById(id);
        if (newArticle.getTitle() != null) {
            article.setTitle(newArticle.getTitle());
        }
        if (newArticle.getBody() != null) {
            article.setBody(newArticle.getBody());
        }
        if (newArticle.getAuthorName() != null) {
            article.setAuthorName(newArticle.getAuthorName());
        }
        return articleRepository.save(article);
    }

    public void deleteArticle(long id) {
        articleRepository.deleteById(id);
    }
}
