package spring.ms.com.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ms.com.rest.request.LocationRequest;
import spring.ms.com.rest.request.ServiceRequest;
import spring.ms.com.rest.response.CategoryResponse;
import spring.ms.com.rest.response.LocationResponse;
import spring.ms.com.rest.response.ServiceResponse;
import spring.ms.com.services.CategoryService;
import spring.ms.com.services.ServiceService;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Controller
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/services")
    public String getAll(Model model){
        model.addAttribute("title", "Services");
        model.addAttribute("services", serviceService.getAll());

        return "services";
    }

    @GetMapping("/addService")
    public String create(Model model){
        ServiceRequest serviceRequest = new ServiceRequest();
        List<CategoryResponse> categories = categoryService.getAll();
        model.addAttribute("title", "Add Service");
        model.addAttribute("serviceRequest", serviceRequest);
        model.addAttribute("categories", categories);
        return "add_service";
    }

    @PostMapping("/addService")
    public String save(@ModelAttribute("serviceRequest") ServiceRequest serviceRequest,
                       RedirectAttributes redirectAttributes) throws ParseException {
        int id = serviceService.createService(serviceRequest);
        Optional<ServiceResponse> service = serviceService.getById(id);
        if(service.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding a service!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Added successfully!");
        }
        return "redirect:/addService";
    }
}
