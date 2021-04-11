package se.sdaproject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sdaproject.Models.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
