package spring.ms.com.rest.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

    @RequestMapping("/")
    public String login()
    {
        return "page_login";
    }

    @RequestMapping("/home")
    public String home(Model model)
    {
        model.addAttribute("title", "Home");
        return "home_page";
    }
}
