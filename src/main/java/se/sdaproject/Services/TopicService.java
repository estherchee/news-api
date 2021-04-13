package se.sdaproject.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import se.sdaproject.Exceptions.ResourceNotFoundException;
import se.sdaproject.Models.Article;
import se.sdaproject.Models.Topic;
import se.sdaproject.Repositories.ArticleRepository;
import se.sdaproject.Repositories.TopicRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository, ArticleRepository articleRepository) {
        this.topicRepository = topicRepository;
        this.articleRepository = articleRepository;
    }

    public List<Topic> findAllTopic() {
        return topicRepository.findAll();
    }

    public List<Topic> findAllTopicByArticleId(long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(articleId));
        return article.getTopics();
    }

    public Topic linkTopicWithArticle(long articleId, Topic newTopic) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(articleId));
        Optional<Topic> result = topicRepository.findAll()
                .stream()
                .filter(topic -> topic.getName().equals(newTopic.getName()))
                .findFirst();

        Topic topicToSave = result.orElse(newTopic);

        if (topicToSave.getArticles() == null) {
            topicToSave.setArticles(Arrays.asList(article));
        } else {
            topicToSave.getArticles().add(article);
        }
        topicRepository.save(topicToSave);
        return topicToSave;
    }

    public Topic createTopic(Topic newTopic) {
        return topicRepository.save(newTopic);
    }

    public Topic updateTopic(Topic newTopic, long id) {
        return topicRepository.findById(id)
                .map(topic -> {
                    if (newTopic.getName() != null) {
                        topic.setName(newTopic.getName());
                    }
                    return topicRepository.save(topic);
                })
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public void deleteTopic(long id) {
        topicRepository.deleteById(id);
    }

    public void unlinkArticleAndTopic(long articleId, long topicId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(articleId));
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException(topicId));
        topic.getArticles().remove(article);
        topicRepository.save(topic);
    }

    public List<Article> getArticlesByTopic(long topicId) {
        return topicRepository.findById(topicId)
                .map(Topic::getArticles)
                .orElseThrow(() -> new ResourceNotFoundException(topicId));
    }
}
