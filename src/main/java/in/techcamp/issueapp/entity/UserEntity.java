package in.techcamp.issueapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<IssueEntity> issues;

    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comments;
}
