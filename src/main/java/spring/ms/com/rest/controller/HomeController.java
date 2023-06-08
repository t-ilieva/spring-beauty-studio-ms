package spring.ms.com.rest.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ms.com.rest.response.AppointmentResponse;
import spring.ms.com.security.User;
import spring.ms.com.security.UserDTO;
import spring.ms.com.security.UserService;

import java.util.Optional;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String index()
    {
        return "redirect:/login";
    }

    @GetMapping("login")
    public String login()
    {
        return "page_login";
    }

    @GetMapping("logout")
    public String logout()
    {
        return "redirect:/login";
    }

    @GetMapping("home")
    public String home(Model model)
    {
        model.addAttribute("title", "Home");
        return "home_page";
    }

    @GetMapping ("signUp")
    public String signUp(Model model)
    {
        model.addAttribute("userDTO", new UserDTO());
        return "sign_up_page";
    }

    @PostMapping("signUp")
    public String createUser(@ModelAttribute("userDTO") UserDTO userDTO,
                             RedirectAttributes redirectAttributes){

        boolean check = userService.checkEmail(userDTO.getEmail());

        if(check) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email already exists!");
        }else {
            int id = userService.create(userDTO);
            Optional<UserDTO> userDTO1 = userService.getById(id);
            if(userDTO1.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Error creating user!");
            }
            else{
                redirectAttributes.addFlashAttribute("successMessage", "Created successfully!");
            }
        }
        return "redirect:/signUp";
    }
}
