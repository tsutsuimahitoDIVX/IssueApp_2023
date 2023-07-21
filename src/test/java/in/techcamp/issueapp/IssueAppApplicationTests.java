package in.techcamp.issueapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class IssueAppApplicationTests {

	@Autowired
	private PasswordEncoder encoder;

	@Test
	void encode() {
		System.out.println(encoder.encode("test"));
	}
}
