package in.techcamp.issueapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comment")
@Data
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private IssueEntity issue;
}
