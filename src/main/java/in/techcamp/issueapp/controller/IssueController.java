package in.techcamp.issueapp.controller;

import in.techcamp.issueapp.entity.CommentEntity;
import in.techcamp.issueapp.entity.IssueEntity;
import in.techcamp.issueapp.entity.UserEntity;
import in.techcamp.issueapp.repository.CommentRepository;
import in.techcamp.issueapp.repository.IssueRepository;
import in.techcamp.issueapp.repository.UserRepository;
import in.techcamp.issueapp.service.IssueService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IssueController {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IssueService issueService;

//    イシュー一覧表示
    @GetMapping("/")
    public String getIndex(Model model){
        List<IssueEntity> issueList = issueRepository.findAll();
        model.addAttribute("issue", issueList);
        return "index";
    }

//    イシュー投稿機能（フォーム画面遷移
    @GetMapping("/issueForm")
    public String showIssueForm(@ModelAttribute("issueEntity") IssueEntity issueEntity ){
        return "issueForm";
    }

//    イシュー投稿機能（ロジック
    @PostMapping("/issues")
    public String postIssue(@ModelAttribute("issueEntity")IssueEntity issueEntity, BindingResult result, Authentication authentication){
        User authenticatedUser = (User) authentication.getPrincipal();
        String username = authenticatedUser.getUsername();
        UserEntity user = userRepository.findByUsername(username);
        issueEntity.setUser(user);
        issueService.createIssue(issueEntity);
        return "redirect:/";
    }

//    イシュー更新機能（画面遷移）
    @GetMapping("/user/{userId}/issue/{issueId}/edit")
    public String edit(@PathVariable Integer userId, @PathVariable Integer issueId, Model model) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + userId));
        IssueEntity issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Memo not found: " + issueId));

        model.addAttribute("user", user);
        model.addAttribute("issue", issue);

        return "update"; // メモの編集画面へ遷移
    }

//    イシュー更新機能（更新ロジック）
    @PostMapping("/user/{userId}/issue/{issueId}/update")
    public String update(Authentication authentication,
                         @PathVariable("userId") Integer userId,
                         @PathVariable("issueId") Integer issueId,
                         @RequestParam("title") String newTitle,
                         @RequestParam("content") String newContent,
                         @RequestParam("period") String newPeriod,
                         @RequestParam("importance") char newImportance)
    {
    // 現在認証されているユーザー名を取得
    String username = authentication.getName();

    // 該当のイシューとアカウントを取得
    IssueEntity issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + issueId));
    UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + userId));

    // ユーザーチェック
    if (user.getUsername().equals(username)) {
        // イシューを更新
        issue.setTitle(newTitle);
        issue.setContent(newContent);
        issue.setPeriod(newPeriod);
        issue.setImportance(newImportance);
        issueRepository.save(issue);
    }
    else {
        // エラーメッセージを設定したり、エラーページにリダイレクトしたりします。
    }

    // 更新後のページにリダイレクト

    return "redirect:/issue/{issueId}";
}

//    イシュー削除機能
    @PostMapping("/user/{userId}/issue/{issueId}/delete")
    public String delete(Authentication authentication,
                             @PathVariable("userId") Integer userId,
                             @PathVariable("issueId") Integer issueId)
    {
        // 現在認証されているユーザー名を取得
        String username = authentication.getName();

        // 該当のメモとアカウントを取得
        IssueEntity issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + issueId));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + userId));

        // ユーザーチェック
        if (user.getUsername().equals(username)) {
            // イシューを削除

            issueRepository.deleteById(issueId);
        }
        else {
            // エラーメッセージを設定したり、エラーページにリダイレクトしたりします。
        }

        // 更新後のページにリダイレクト

        return "redirect:/";
    }

//    イシュー詳細表示
    @GetMapping("/issue/{issueId}")
    public String showIssueDetail(@PathVariable("issueId") Integer issueId,Model model){
        IssueEntity issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + issueId));
        model.addAttribute("issue",issue);
        return "detail";
    }

//    イシュー投稿ユーザー別一覧表示
    @GetMapping("user/{userId}/issues")
    public String getUserIssues(@PathVariable("userId") Integer userId, Model model){
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + userId));
        List<IssueEntity> issues = issueRepository.findByUser_Id(userId);
        model.addAttribute("user",user);
        model.addAttribute("issue",issues);

        return "userIssues";
    }

//    コメント投稿機能
    @PostMapping("issue/{issueId}/comment")
    public String postComment(Authentication authentication,
                              CommentEntity comment,
                              @PathVariable("issueId")Integer issueId){
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username);

        IssueEntity issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + issueId));

        comment.setUser(user);
        comment.setIssue(issue);

        commentRepository.save(comment);

        return "redirect:/issue/{issueId}";
    }
}