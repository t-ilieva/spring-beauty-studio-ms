package spring.ms.com.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ms.com.rest.request.CategoryRequest;
import spring.ms.com.rest.response.CategoryResponse;
import spring.ms.com.security.User;
import spring.ms.com.security.UserRepository;
import spring.ms.com.security.UserService;
import spring.ms.com.services.CategoryService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @ModelAttribute
    private void userDetails(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.getByEmail(email);

        model.addAttribute("user", user);
    }

    @GetMapping("")
    public String getAll(Model model, RedirectAttributes redirectAttributes){
        model.addAttribute("title", "Categories");
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("category", "");


        return "categories";
    }

    @GetMapping("/add")
    public String create(Model model){
        CategoryRequest categoryRequest = new CategoryRequest();
        model.addAttribute("title", "Add Category");
        model.addAttribute("categoryRequest", categoryRequest);

        return "add_category";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("categoryRequest") CategoryRequest categoryRequest,
                       RedirectAttributes redirectAttributes){
        int id = categoryService.createCategory(categoryRequest);
        Optional<CategoryResponse> category = categoryService.getById(id);
        if(category.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding! Check if category already exists!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Added successfully!");
        }
        return "redirect:/categories/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){
        model.addAttribute("title", ("Update Category | ID:" + id));
        model.addAttribute("category", categoryService.getById(id).get());
        return "edit_category";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable int id,
                                 @ModelAttribute("category") CategoryRequest categoryRequest,
                                 RedirectAttributes redirectAttributes, Model model){

        int newId = categoryService.editCategory(id, categoryRequest);
        Optional<CategoryResponse> category = categoryService.getById(newId);
        if(category.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating! Check if category already exists!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Updated successfully!");
        }
        return "redirect:/categories/edit/{id}";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model){
        model.addAttribute("title", ("Delete Category | ID:" + id));
        model.addAttribute("text", "category");
        model.addAttribute("type", "categories");
        model.addAttribute("item", categoryService.getById(id).get());
        return "delete_page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("category") String category, Model model){

        List<CategoryResponse> categories = categoryService.getAll();
        List<CategoryResponse> categorySearch = new ArrayList<>();

        for (CategoryResponse categoryResponse : categories) {
            if(categoryResponse.getName().toLowerCase().startsWith(category.toLowerCase())){
                categorySearch.add(categoryResponse);
            }
        }

        model.addAttribute("category", "");
        model.addAttribute("categorySearch", categorySearch);

        return "search_categories";
    }

    @PostMapping("/search")
    public String Search(Model model,
                         @ModelAttribute("category") String category,
                         RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("category", category);

        return "redirect:/categories/search";
    }

}

