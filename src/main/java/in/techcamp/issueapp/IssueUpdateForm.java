package in.techcamp.issueapp;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IssueUpdateForm{
        @NotBlank(message = "タイトルには空白文字のみの入力は許可されません。")
        private String title;

        @NotBlank(message = "内容には空白文字のみの入力は許可されません。")
        private String content;

        private String period;
        private Character importance;
}
