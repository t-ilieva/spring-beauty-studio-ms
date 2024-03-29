package spring.ms.com.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ms.com.rest.request.EmployeeRequest;
import spring.ms.com.rest.response.CategoryResponse;
import spring.ms.com.rest.response.EmployeeResponse;
import spring.ms.com.rest.response.LocationResponse;
import spring.ms.com.security.User;
import spring.ms.com.security.UserRepository;
import spring.ms.com.security.UserService;
import spring.ms.com.services.CategoryService;
import spring.ms.com.services.EmployeeService;
import spring.ms.com.services.LocationService;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;

    @ModelAttribute
    private void userDetails(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.getByEmail(email);

        model.addAttribute("user", user);
    }

    @GetMapping("")
    public String getAll(Model model){
        model.addAttribute("title", "Employees");
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("employee", "");

        return "employees";
    }

    @GetMapping("/add")
    public String create(Model model){
        EmployeeRequest employeeRequest = new EmployeeRequest();
        List<LocationResponse> locations = locationService.getAll();
        List<CategoryResponse> categories = categoryService.getAll();
        model.addAttribute("title", "Add Employee");
        model.addAttribute("employeeRequest", employeeRequest);
        model.addAttribute("categories", categories);
        model.addAttribute("locations", locations);
        return "add_employee";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("employeeRequest") EmployeeRequest employeeRequest,
                       RedirectAttributes redirectAttributes) throws ParseException {
        int id = employeeService.createEmployee(employeeRequest);
        Optional<EmployeeResponse> employee = employeeService.getById(id);
        if(employee.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding! Check if employee with the same employee number already exists!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Added successfully!");
        }
        return "redirect:/employees/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){
        EmployeeResponse employeeResponse = employeeService.getById(id).get();
        List<LocationResponse> locations = locationService.getAll();
        List<CategoryResponse> categories = categoryService.getAll();
        model.addAttribute("title", ("Update Employee | ID:" + id));
        model.addAttribute("employee", employeeResponse);
        model.addAttribute("categories", categories);
        model.addAttribute("locations", locations);
        return "edit_employee";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable int id,
                                 @ModelAttribute("employee") EmployeeResponse employeeResponse,
                                 RedirectAttributes redirectAttributes, Model model){

        int newId = employeeService.editEmployee(id, employeeResponse);
        Optional<EmployeeResponse> employee = employeeService.getById(newId);
        if(employee.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating! Check if employee with the same employee number already exists!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Updated successfully!");
        }
        return "redirect:/employees/edit/{id}";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model){
        model.addAttribute("title", ("Delete Employee | ID:" + id));
        model.addAttribute("text", "employee");
        model.addAttribute("type", "employees");
        model.addAttribute("item", employeeService.getById(id).get());
        return "delete_page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("employee") String employee, Model model){

        List<EmployeeResponse> employees = employeeService.getAll();
        List<EmployeeResponse> employeeSearch = new ArrayList<>();

        for (EmployeeResponse employeeResponse : employees) {
            String name = employeeResponse.getFirstName() + " " + employeeResponse.getLastName();
            if(name.toLowerCase().contains(employee.toLowerCase())){
                employeeSearch.add(employeeResponse);
            }
        }

        model.addAttribute("employee", "");
        model.addAttribute("employeeSearch", employeeSearch);

        return "search_employees";
    }

    @PostMapping("/search")
    public String Search(Model model,
                         @ModelAttribute("employee") String employee,
                         RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("employee", employee);

        return "redirect:/employees/search";
    }
}
