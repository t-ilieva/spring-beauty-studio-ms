package spring.ms.com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    private void userDetails(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.getByEmail(email);

        model.addAttribute("user", user);
    }

    @GetMapping("home")
    public String home(Model model) {
        model.addAttribute("title", "Home");
        return "home";
    }
}
