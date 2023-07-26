package in.techcamp.issueapp.service;

import in.techcamp.issueapp.entity.IssueEntity;
import in.techcamp.issueapp.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public void createIssue(IssueEntity issueEntity){
        issueRepository.save(issueEntity);
    }
}
