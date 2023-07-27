package in.techcamp.issueapp.controller;

import in.techcamp.issueapp.IssueUpdateForm;
import in.techcamp.issueapp.entity.CommentEntity;
import in.techcamp.issueapp.entity.IssueEntity;
import in.techcamp.issueapp.entity.UserEntity;
import in.techcamp.issueapp.repository.CommentRepository;
import in.techcamp.issueapp.repository.IssueRepository;
import in.techcamp.issueapp.repository.UserRepository;
import in.techcamp.issueapp.service.IssueService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
    public String getIndex(Model model) {
        List<IssueEntity> issueList = issueRepository.findAll();
        model.addAttribute("issue", issueList);
        return "index";
    }

    //    イシュー投稿機能（フォーム画面遷移
    @GetMapping("/issueForm")
    public String showIssueForm(@ModelAttribute("issueEntity") IssueEntity issueEntity) {
        return "issueForm";
    }

    //    イシュー投稿機能（ロジック
    @PostMapping("/issues")
    public String postIssue(@Valid  @ModelAttribute("issueEntity") IssueEntity issueEntity,
                            BindingResult result,
                            Authentication authentication,
                            Model model)
    {
        User authenticatedUser = (User) authentication.getPrincipal();
        String username = authenticatedUser.getUsername();
        UserEntity user = userRepository.findByUsername(username);
        issueEntity.setUser(user);

        if (result.hasErrors()){
            return"issueForm";
        }

        try {
            issueService.createIssue(issueEntity);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        return "redirect:/";
    }

    //    イシュー更新機能（画面遷移）
    @GetMapping("/user/{userId}/issue/{issueId}/edit")
    public String edit(@PathVariable Integer issueId, Model model) {

        IssueEntity issue;

        try {
            issue = issueRepository.findById(issueId)
                    .orElseThrow(() -> new EntityNotFoundException("Issue not found: " + issueId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

//       空白文字では更新できないバリデーション用
        IssueUpdateForm issueUpdateForm = new IssueUpdateForm();
        issueUpdateForm.setTitle(issue.getTitle());
        issueUpdateForm.setContent(issue.getContent());
        issueUpdateForm.setPeriod(issue.getPeriod());
        issueUpdateForm.setImportance(issue.getImportance());

        model.addAttribute("issue", issue);
        model.addAttribute("issueUpdateForm",issueUpdateForm);

        return "update";
    }

    //    イシュー更新機能（更新ロジック）
    @PostMapping("/user/{userId}/issue/{issueId}/update")
    public String update(Authentication authentication,
                         @Valid IssueUpdateForm issueUpdateForm,
                         BindingResult result,
                         @PathVariable("userId") Integer userId,
                         @PathVariable("issueId") Integer issueId,
                         Model model
                        ) {

        // 現在認証されているユーザー名を取得
        String username = authentication.getName();

        // 該当のイシューとアカウントを取得
        IssueEntity issue;
        UserEntity user;

        try {
            issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Issue not found: " + issueId));
            user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        if (result.hasErrors()){
            model.addAttribute("issue",issue);
            model.addAttribute("issueUpdateForm",issueUpdateForm);
            return"update";
        }

        // ユーザーチェック
        if (user.getUsername().equals(username)) {
            // イシューを更新
            try {
                issue.setTitle(issueUpdateForm.getTitle());
                issue.setContent(issueUpdateForm.getContent());
                issue.setPeriod(issueUpdateForm.getPeriod());
                issue.setImportance(issueUpdateForm.getImportance());

                issueRepository.save(issue);
            } catch (Exception e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", "イシューの投稿者と一致しません。");
            return "error";
        }
        // 更新後のページにリダイレクト
        return "redirect:/issue/" + issueId;
    }


    //    イシュー削除機能
    @PostMapping("/user/{userId}/issue/{issueId}/delete")
    public String delete(Authentication authentication,
                         @PathVariable("userId") Integer userId,
                         @PathVariable("issueId") Integer issueId,
                         Model model) {
        // 現在認証されているユーザー名を取得
        String username = authentication.getName();

        // 該当のメモとアカウントを取得
        UserEntity user;

        try {
            user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        }  catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
        // ユーザーチェック
        if (user.getUsername().equals(username)) {
            // イシューを削除
            try {
                issueRepository.deleteById(issueId);
            } catch (Exception e){
                model.addAttribute("errorMessage",e.getMessage());
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", "イシューの投稿者と一致しません。");
            return "error";
        }
        return "redirect:/";
    }

    //    イシュー詳細表示
    @GetMapping("/issue/{issueId}")
    public String showIssueDetail(@PathVariable("issueId") Integer issueId, @ModelAttribute("comment") CommentEntity comment, Model model) {
        IssueEntity issue;

        try {
            issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Issue not found: " + issueId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        List<CommentEntity> comments = commentRepository.findByIssue_id(issueId);
        model.addAttribute("issue", issue);
        model.addAttribute("comments",comments);
        return "detail";
    }

    //    イシュー投稿ユーザー別一覧表示
    @GetMapping("user/{userId}/issues")
    public String getUserIssues(@PathVariable("userId") Integer userId, Model model) {
        UserEntity user;

        try {
            user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }

        List<IssueEntity> issues = issueRepository.findByUser_Id(userId);
        model.addAttribute("user", user);
        model.addAttribute("issue", issues);

        return "userIssues";
    }
}