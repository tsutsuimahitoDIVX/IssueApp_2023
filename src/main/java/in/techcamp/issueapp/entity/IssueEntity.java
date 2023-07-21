package in.techcamp.issueapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class IssueEntity {
    long id;
    String title;
    String content;
    String period;
    Character importance;
}
