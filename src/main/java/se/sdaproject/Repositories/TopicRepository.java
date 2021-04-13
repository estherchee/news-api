package se.sdaproject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sdaproject.Models.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
