package spring.ms.com.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ms.com.rest.request.CategoryRequest;
import spring.ms.com.rest.response.CategoryResponse;
import spring.ms.com.services.CategoryService;

import java.util.Optional;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String getAll(Model model){
        model.addAttribute("title", "Categories");
        model.addAttribute("categories", categoryService.getAll());

        return "categories";
    }

    @GetMapping("/addCategory")
    public String create(Model model){
        CategoryRequest categoryRequest = new CategoryRequest();
        model.addAttribute("title", "Add Category");
        model.addAttribute("categoryRequest", categoryRequest);

        return "add_category";
    }

    @PostMapping("/addCategory")
    public String save(@ModelAttribute("categoryRequest") CategoryRequest categoryRequest,
                       RedirectAttributes redirectAttributes){
        int id = categoryService.createCategory(categoryRequest);
        Optional<CategoryResponse> category = categoryService.getById(id);
        if(category.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding a category!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Added successfully!");
        }
        return "redirect:/addCategory";
    }
}

