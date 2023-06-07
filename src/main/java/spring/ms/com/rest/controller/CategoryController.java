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
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String getAll(Model model){
        model.addAttribute("title", "Categories");
        model.addAttribute("categories", categoryService.getAll());

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
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding a category!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Added successfully!");
        }
        return "redirect:/categories/add";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable int id, Model model){
        model.addAttribute("title", ("Update Category | ID:" + id));
        model.addAttribute("category", categoryService.getById(id).get());
        return "edit_category";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable int id,
                                 @ModelAttribute("category") CategoryRequest categoryRequest,
                                 RedirectAttributes redirectAttributes, Model model){

        int newId = categoryService.editCategory(id, categoryRequest);
        Optional<CategoryResponse> category = categoryService.getById(id);
        if(category.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating selected category!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Updated successfully!");
        }
        return "redirect:/categories/edit/{id}";
    }

    @GetMapping("/delete/{id}")
    public String deleteLocation(@PathVariable int id, Model model){
        model.addAttribute("title", ("Delete Location | ID:" + id));
        model.addAttribute("text", "category");
        model.addAttribute("type", "categories");
        model.addAttribute("item", categoryService.getById(id).get());
        return "delete_page";
    }

    @PostMapping("/delete/{id}")
    public String deleteLocation(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}

