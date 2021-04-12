package se.sdaproject.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.Models.Comment;
import se.sdaproject.Services.CommentService;

import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/articles/{articleId}/comments")
    public List<Comment> getAllCommentsForArticle(@PathVariable long articleId) {
        return commentService.findAllCommentsForArticle(articleId);
    }

    @RequestMapping(value = "comments", method = RequestMethod.GET)
    public List<Comment> getAllCommentsFromAuthor(@RequestParam("authorName") String authorName) {
        return commentService.findAllCommentsFromAuthor(authorName);
    }

    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<Comment> addCommentForArticle(@PathVariable long articleId, @RequestBody Comment comment) {
        Comment savedComment = commentService.saveCommentForArticle(articleId, comment);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedComment);
    }

    @PutMapping("/comments/{id}")
    public Comment updateComment(@RequestBody Comment newComment, @PathVariable long id) {
        return commentService.updateComment(newComment, id);
    }

    @DeleteMapping("/comments/{id}")
    public void removeComment(@PathVariable Long id) {
        commentService.removeComment(id);
    }
}
