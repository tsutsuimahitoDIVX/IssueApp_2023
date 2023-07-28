package in.techcamp.issueapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "comment")
@Data
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "コメントには空白文字のみの入力は許可されません。")
    private String message;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private IssueEntity issue;
}
