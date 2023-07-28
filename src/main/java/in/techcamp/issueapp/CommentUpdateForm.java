package in.techcamp.issueapp;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentUpdateForm {
    @NotBlank(message = "内容には空白文字のみの入力は許可されません。")
    private String message;
}
