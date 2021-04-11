package se.sdaproject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.sdaproject.Models.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query(value = "SELECT * FROM topic WHERE name= ?1", nativeQuery = true)
    Topic findTopicWithName(String topicName);
}
