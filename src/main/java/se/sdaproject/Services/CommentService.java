package se.sdaproject.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.sdaproject.Exceptions.ResourceNotFoundException;
import se.sdaproject.Models.Article;
import se.sdaproject.Models.Comment;
import se.sdaproject.Repositories.ArticleRepository;
import se.sdaproject.Repositories.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    public List<Comment> findAllCommentsForArticle(long articleId) {
        return commentRepository.findAll()
                .stream()
                .filter(comment -> comment.getArticle().getId().equals(articleId))
                .collect(Collectors.toList());
    }

    public List<Comment> findAllCommentsFromAuthor(String authorName) {
        return commentRepository.findAll()
                .stream()
                .filter(comment -> comment.getAuthorName().equals(authorName))
                .collect(Collectors.toList());
    }

    public Comment saveCommentForArticle(long articleId, Comment comment) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(articleId));
        comment.setArticle(article);
        commentRepository.save(comment);
        return comment;
    }

    public Comment updateComment(Comment newComment, long id) {
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

    public void removeComment(long id) {
        commentRepository.deleteById(id);
    }
}
