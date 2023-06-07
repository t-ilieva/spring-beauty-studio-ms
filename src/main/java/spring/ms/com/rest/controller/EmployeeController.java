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
import spring.ms.com.services.CategoryService;
import spring.ms.com.services.EmployeeService;
import spring.ms.com.services.LocationService;

import java.text.ParseException;
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


    @GetMapping("")
    public String getAll(Model model){
        model.addAttribute("title", "Employees");
        model.addAttribute("employees", employeeService.getAll());

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
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding an employee!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Added successfully!");
        }
        return "redirect:/employees/add";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable int id, Model model){
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
    public String updateEmployee(@PathVariable int id,
                                 @ModelAttribute("employee") EmployeeResponse employeeResponse,
                                 RedirectAttributes redirectAttributes, Model model){

        int newId = employeeService.editEmployee(id, employeeResponse);
        Optional<EmployeeResponse> employee = employeeService.getById(id);
        if(employee.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating selected employee!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Updated successfully!");
        }
        return "redirect:/employees/edit/{id}";
    }

    @GetMapping("/delete/{id}")
    public String deleteLocation(@PathVariable int id, Model model){
        model.addAttribute("title", ("Delete Employee | ID:" + id));
        model.addAttribute("text", "employee");
        model.addAttribute("type", "employees");
        model.addAttribute("item", employeeService.getById(id).get());
        return "delete_page";
    }

    @PostMapping("/delete/{id}")
    public String deleteLocation(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
}
