package se.sdaproject.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.Exceptions.ResourceNotFoundException;
import se.sdaproject.Models.Article;
import se.sdaproject.Models.Topic;
import se.sdaproject.Repositories.ArticleRepository;
import se.sdaproject.Repositories.TopicRepository;
import se.sdaproject.Services.TopicService;

import java.util.Arrays;
import java.util.List;

@RestController
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topics")
    public List<Topic> getAllTopic() {
        return topicService.findAllTopic();
    }

    @GetMapping("/articles/{articleId}/topics")
    public List<Topic> getAllTopicByArticleId(@PathVariable long articleId) {
        return topicService.findAllTopicByArticleId(articleId);
    }

    @PostMapping("/articles/{articleId}/topics")
    public ResponseEntity<Topic> linkTopicWithArticle(@PathVariable long articleId, @RequestBody Topic newTopic) {
        Topic savedTopic = topicService.linkTopicWithArticle(articleId, newTopic);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTopic);
    }

    @PostMapping("/topics")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic newTopic) {
        Topic savedTopic = topicService.createTopic(newTopic);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTopic);
    }

    @PutMapping("/topics/{id}")
    public Topic updateTopic(@RequestBody Topic newTopic, @PathVariable long id) {
        return topicService.updateTopic(newTopic, id);
    }

    @DeleteMapping("/topics/{id}")
    public void deleteTopic(@PathVariable long id) {
        topicService.deleteTopic(id);
    }

    @DeleteMapping("/articles/{articleId}/topics/{topicId}")
    public void unlinkArticleAndTopic(@PathVariable long articleId, @PathVariable long topicId) {
        topicService.unlinkArticleAndTopic(articleId, topicId);
    }

    @GetMapping("/topics/{topicId}/articles")
    public List<Article> getArticlesByTopic(@PathVariable long topicId) {
        return topicService.getArticlesByTopic(topicId);
    }
}
