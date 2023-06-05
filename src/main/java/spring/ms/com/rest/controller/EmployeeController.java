package spring.ms.com.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ms.com.rest.request.EmployeeRequest;
import spring.ms.com.rest.request.ServiceRequest;
import spring.ms.com.rest.response.CategoryResponse;
import spring.ms.com.rest.response.EmployeeResponse;
import spring.ms.com.rest.response.LocationResponse;
import spring.ms.com.rest.response.ServiceResponse;
import spring.ms.com.services.CategoryService;
import spring.ms.com.services.EmployeeService;
import spring.ms.com.services.LocationService;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LocationService locationService;


    @GetMapping("/employees")
    public String getAll(Model model){
        model.addAttribute("title", "Employees");
        model.addAttribute("employees", employeeService.getAll());

        return "employees";
    }

    @GetMapping("/addEmployee")
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

    @PostMapping("/addEmployee")
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
        return "redirect:/addEmployee";
    }
}
