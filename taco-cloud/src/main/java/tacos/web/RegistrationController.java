package tacos.web;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.RegistrationForm;
import tacos.data.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	private UserRepository userRepo;
	private PasswordEncoder encoder;
	
	@GetMapping
	private String registerForm() {
		return "registration";
	}
	
	@PostMapping
	private String createUser(RegistrationForm form) {
		userRepo.save(form.toUser(encoder)); 
		return "redirect: /login";
	}
}
