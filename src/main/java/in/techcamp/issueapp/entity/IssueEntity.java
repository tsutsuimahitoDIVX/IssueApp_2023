package in.techcamp.issueapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "issues")
@Entity
public class IssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "タイトルには空白文字のみの入力は許可されません。")
    private String title;

    @NotBlank(message = "内容には空白文字のみの入力は許可されません。")
    private String content;

    private String period;
    private Character importance;

    @ManyToOne
    private UserEntity user;

    @OneToMany(mappedBy = "issue",cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments;
}
