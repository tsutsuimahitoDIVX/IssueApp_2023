package in.techcamp.issueapp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserInput {

    @NotBlank(message = "ユーザー名は必須です。")
    @UniqueUsername(message = "ユーザー名が既に登録されています。")
    private String username;

    @Size(min = 4, max = 10, message = "パスワードは4文字以上、10文字以下で設定してください。")
    @NotBlank(message = "パスワードには空白文字のみの入力は許可されません。")
    private String password;
}
