package in.techcamp.issueapp.controller;

import in.techcamp.issueapp.IssueForm;
import in.techcamp.issueapp.entity.IssueEntity;
import in.techcamp.issueapp.entity.UserEntity;
import in.techcamp.issueapp.repository.IssueRepository;
import in.techcamp.issueapp.repository.UserRepository;
import in.techcamp.issueapp.service.IssueService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class IssueController {
    private final IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueService issueService;

    @GetMapping("/")
    public String showIndex(Model model){
        var issueList = issueRepository.findAll();
        model.addAttribute("issueList", issueList);
        return "index";
    }

    @GetMapping("/issueForm")
    public String showIssueForm(@ModelAttribute("issueEntity") IssueEntity issueEntity ){
        return "issueForm";
    }

//    イシュー投稿機能
    @PostMapping("/issues")
    public String postIssue(@ModelAttribute("issueEntity")IssueEntity issueEntity, BindingResult result, Authentication authentication){
        User authenticatedUser = (User) authentication.getPrincipal();
        String username = authenticatedUser.getUsername();
        UserEntity user = userRepository.findByUsername(username);
        issueEntity.setUser(user);
        issueService.createIssue(issueEntity);
        return "redirect:/";
    }

//    @GetMapping("/issues/{id}")
//    public String issueDetail(@PathVariable long id, Model model){
//        var issue = issueRepository.findById(id);
//        model.addAttribute("issue", issue);
//        return "detail";
//    }

//    @PostMapping("/issues/{id}/update")
//    public String updateIssue(@PathVariable long id, IssueForm issueForm){
//        issueRepository.update(id, issueForm.getTitle(), issueForm.getContent(), issueForm.getPeriod(), issueForm.getImportance());
//        return "redirect:/";
//    }
//
//    @PostMapping("issues/{id}/delete")
//    public String deleteIssue(@PathVariable long id) {
//        issueRepository.deleteById(id);
//        return "redirect:/";
//    }
}
