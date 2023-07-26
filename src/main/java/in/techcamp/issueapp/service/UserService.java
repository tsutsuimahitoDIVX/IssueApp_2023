package in.techcamp.issueapp.service;

import in.techcamp.issueapp.UserInput;
import in.techcamp.issueapp.entity.UserEntity;
import in.techcamp.issueapp.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewUser(@Valid UserInput userInput){
        String password = userInput.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity user = new UserEntity();
        user.setUsername(userInput.getUsername());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }
}
