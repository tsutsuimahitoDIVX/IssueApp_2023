package in.techcamp.issueapp.controller;

import in.techcamp.issueapp.IssueForm;
import in.techcamp.issueapp.repository.IssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class IssueController {
    private final IssueRepository issueRepository;

    @GetMapping("/")
    public String showIndex(Model model){
        var issueList = issueRepository.findAll();
        model.addAttribute("issueList", issueList);
        return "index";
    }

    @GetMapping("/issueForm")
    public String showIssueForm(@ModelAttribute("issueForm") IssueForm form ){
        return "issueForm";
    }

    @PostMapping("/issues")
        public String createIssue(IssueForm issueForm){
            issueRepository.insert(issueForm.getTitle(), issueForm.getContent(), issueForm.getPeriod(), issueForm.getImportance());
            return "redirect:/";
    }

    @GetMapping("/issues/{id}")
    public String issueDetail(@PathVariable long id, Model model){
        var issue = issueRepository.findById(id);
        model.addAttribute("issue", issue);
        return "detail";
    }

    @PostMapping("/issues/{id}/update")
    public String updateIssue(@PathVariable long id, IssueForm issueForm){
        issueRepository.update(id, issueForm.getTitle(), issueForm.getContent(), issueForm.getPeriod(), issueForm.getImportance());
        return "redirect:/";
    }

    @PostMapping("issues/{id}/delete")
    public String deleteIssue(@PathVariable long id) {
        issueRepository.deleteById(id);
        return "redirect:/";
    }
}
