package se.sdaproject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sdaproject.Models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
