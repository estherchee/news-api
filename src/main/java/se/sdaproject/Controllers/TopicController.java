package se.sdaproject.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.Exceptions.ResourceNotFoundException;
import se.sdaproject.Models.Article;
import se.sdaproject.Models.Topic;
import se.sdaproject.Repositories.ArticleRepository;
import se.sdaproject.Repositories.TopicRepository;

import java.util.Arrays;
import java.util.List;

@RestController
public class TopicController {
    private final TopicRepository topicRepository;
    private final ArticleRepository articleRepository;

    public TopicController(TopicRepository topicRepository, ArticleRepository articleRepository) {
        this.topicRepository = topicRepository;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/topics")
    public List<Topic> getAllTopic() {
        return topicRepository.findAll();
    }

    @GetMapping("/articles/{articleId}/topics")
    public List<Topic> getAllTopicByArticleId(@PathVariable long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(articleId));
        return article.getTopics();
    }

    @PostMapping("/articles/{articleId}/topics")
    public ResponseEntity<Topic> linkTopicWithArticle(@PathVariable long articleId, @RequestBody Topic newTopic) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(articleId));
        Topic topic = topicRepository.findTopicWithName(newTopic.getName());
        Topic topicToSave;
        if (topic != null) {
            topicToSave = topic;
        } else {
            topicToSave = newTopic;
        }
        if (topicToSave.getArticles() == null) {
            topicToSave.setArticles(Arrays.asList(article));
        } else {
            topicToSave.getArticles().add(article);
        }
        topicRepository.save(topicToSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(topicToSave);
    }

    @PostMapping("/topics")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic newTopic) {
        topicRepository.save(newTopic);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTopic);
    }

    @PutMapping("/topics/{id}")
    public Topic updateTopic(@RequestBody Topic newTopic, @PathVariable Long id) {
        return topicRepository.findById(id)
                .map(topic -> {
                    if (newTopic.getName() != null) {
                        topic.setName(newTopic.getName());
                    }
                    return topicRepository.save(topic);
                })
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @DeleteMapping("/topics/{id}")
    public void deleteTopic(@PathVariable Long id) {
        topicRepository.deleteById(id);
    }

    @DeleteMapping("/articles/{articleId}/topics/{topicId}")
    public void unlinkArticleAndTopic(@PathVariable long articleId, @PathVariable long topicId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(articleId));
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException(topicId));
        topic.getArticles().remove(article);
        topicRepository.save(topic);
    }

    @GetMapping("/topics/{topicId}/articles")
    public List<Article> getArticlesByTopic(@PathVariable long topicId) {
        return topicRepository.findById(topicId)
                .map(topic -> topic.getArticles())
                .orElseThrow(() -> new ResourceNotFoundException(topicId));
    }
}
