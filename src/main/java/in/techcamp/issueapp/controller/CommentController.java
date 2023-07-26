package in.techcamp.issueapp.controller;

import in.techcamp.issueapp.entity.CommentEntity;
import in.techcamp.issueapp.entity.IssueEntity;
import in.techcamp.issueapp.entity.UserEntity;
import in.techcamp.issueapp.repository.CommentRepository;
import in.techcamp.issueapp.repository.IssueRepository;
import in.techcamp.issueapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

        IssueEntity issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Memo not found: " + issueId));

        comment.setUser(user);
        comment.setIssue(issue);

        commentRepository.save(comment);

        return "redirect:/issue/{issueId}";
    }
}
