package spring.ms.com.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.ms.com.security.User;
import spring.ms.com.security.UserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    private void userDetails(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        model.addAttribute("user", user);
    }

    @GetMapping("home")
    public String home(Model model) {
        model.addAttribute("title", "Home");
        return "home";
    }
}