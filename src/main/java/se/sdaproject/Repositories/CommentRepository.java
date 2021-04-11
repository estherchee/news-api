package se.sdaproject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.sdaproject.Models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comment WHERE article_id = ?1",
            nativeQuery = true)
    List<Comment> findAllCommentsForArticle(Long articleId);

    @Query(value = "SELECT * FROM comment WHERE author_name = ?1",
            nativeQuery = true)
    List<Comment> findAllCommentsFromAuthor(String authorName);
}
