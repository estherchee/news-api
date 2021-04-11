package se.sdaproject.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.Exceptions.ResourceNotFoundException;
import se.sdaproject.Models.Article;
import se.sdaproject.Models.Comment;
import se.sdaproject.Repositories.ArticleRepository;
import se.sdaproject.Repositories.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentController(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articles/{articleId}/comments")
    public List<Comment> getAllCommentsForArticle(@PathVariable Long articleId) {
        return commentRepository.findAllCommentsForArticle(articleId);
    }

    @RequestMapping(value = "comments", method = RequestMethod.GET)
    public List<Comment> getAllCommentsFromAuthor(@RequestParam("authorName") String authorName) {
        return commentRepository.findAllCommentsFromAuthor(authorName);
    }

    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<Comment> addCommentForArticle(@PathVariable Long articleId, @RequestBody Comment comment) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(articleId));
        comment.setArticle(article);
        commentRepository.save(comment);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(comment);
    }

    @PutMapping("/comments/{id}")
    public Comment updateComment(@RequestBody Comment newComment, @PathVariable Long id) {
        return commentRepository.findById(id)
                .map(comment -> {
                    if (newComment.getBody() != null) {
                        comment.setBody(newComment.getBody());
                    }
                    if (newComment.getAuthorName() != null) {
                        comment.setAuthorName(newComment.getAuthorName());
                    }
                    return commentRepository.save(comment);
                })
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @DeleteMapping("/comments/{id}")
    public void removeComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}
