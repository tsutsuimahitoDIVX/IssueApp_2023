package in.techcamp.issueapp.repository;


import in.techcamp.issueapp.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findByIssue_id(Integer issueId);
}

