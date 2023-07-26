package in.techcamp.issueapp.controller;

import in.techcamp.issueapp.UserInput;
import in.techcamp.issueapp.entity.UserEntity;
import in.techcamp.issueapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

//    ログインページへ遷移
    @GetMapping("/loginForm")
    public String loginForm(){
        return "login";
    }

//    ログイン機能（ロジック
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", "名前かパスワードが間違っています。");
        }
        return "login";
    }

//   　ユーザー新規登録ページへ遷移
    @GetMapping("/registerForm")
    public String register(@ModelAttribute("user")UserEntity userEntity){
        return "register";
    }

//    ユーザー新規登録（ロジック
    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid UserInput userInput, BindingResult result){
        if (result.hasErrors()){
            return"register";
        }

        userService.registerNewUser(userInput);  // UserServiceのregisterNewUserメソッドを呼び出す
        return "redirect:/";
    }


}

