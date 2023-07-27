package in.techcamp.issueapp.controller;

import in.techcamp.issueapp.entity.CommentEntity;
import in.techcamp.issueapp.entity.IssueEntity;
import in.techcamp.issueapp.entity.UserEntity;
import in.techcamp.issueapp.repository.CommentRepository;
import in.techcamp.issueapp.repository.IssueRepository;
import in.techcamp.issueapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private CommentRepository commentRepository;

//    コメント投稿機能
    @PostMapping("issue/{issueId}/comment")
    public String postComment(Authentication authentication,
                              CommentEntity comment,
                              @PathVariable("issueId") Integer issueId) {
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username);

        IssueEntity issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Issue not found: " + issueId));

        comment.setUser(user);
        comment.setIssue(issue);

        commentRepository.save(comment);

        return "redirect:/issue/{issueId}";
    }

//    コメント編集機能（編集フォーム遷移
    @GetMapping("issue/{issueId}/comment/{commentId}/edit")
    public String commentEdit(@PathVariable("issueId")Integer issueId,
                              @PathVariable("commentId")Integer commentId,
                              Model model) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found: " + commentId));
        IssueEntity issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Issue not found: " + issueId));
        model.addAttribute("comment",comment);
        model.addAttribute("issue",issue);

        return "commentEdit";
    }

//    コメント編集機能（ロジック
    @PostMapping("issue/{issueId}/comment/{commentId}/update")
    public String commentUpdate(@PathVariable("issueId")Integer issueId,
                                @PathVariable("commentId")Integer commentId,
                                @RequestParam("message") String newMessage,
                                Authentication authentication,
                                Model model) {
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username);
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment not found: " + commentId));

        if (user.getUsername().equals(username)) {
            // メモを更新
            comment.setMessage(newMessage);
            try {
                commentRepository.save(comment);
            } catch (DataAccessException e) {
                // データベース接続に失敗した場合の処理
                model.addAttribute("errorMessage", "データベースへの接続に失敗しました。");
                return "error";
            }
        } else {
            model.addAttribute("errorMessage", "コメント投稿者と異なります！");
            return "error";
        }

        // 更新後のページにリダイレクト
        return "redirect:/issue/{issueId}";
    }

//    コメント削除機能
    @PostMapping("issue/{issueId}/comment/{commentId}/delete")
    public String commentDelete(@PathVariable("issueId")Integer issueId,
                                @PathVariable("commentId")Integer commentId,
                                Authentication authentication){
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username);

        if (user.getUsername().equals(username)) {

            commentRepository.deleteById(commentId);
        }
        else {
            // エラーメッセージを設定したり、エラーページにリダイレクトしたりします。
        }

        return "redirect:/issue/{issueId}";
    }
}
