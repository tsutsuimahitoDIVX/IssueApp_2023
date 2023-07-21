package in.techcamp.issueapp;

import lombok.Data;

@Data
public class IssueForm {
    String title;
    String content;
    String period;
    Character importance;
}
