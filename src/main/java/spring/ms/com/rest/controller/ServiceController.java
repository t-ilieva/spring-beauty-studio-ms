package spring.ms.com.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.ms.com.rest.request.ServiceRequest;
import spring.ms.com.rest.response.CategoryResponse;
import spring.ms.com.rest.response.ServiceResponse;
import spring.ms.com.services.CategoryService;
import spring.ms.com.services.ServiceService;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String getAll(Model model){
        model.addAttribute("title", "Services");
        model.addAttribute("services", serviceService.getAll());

        return "services";
    }

    @GetMapping("/add")
    public String create(Model model){
        ServiceRequest serviceRequest = new ServiceRequest();
        List<CategoryResponse> categories = categoryService.getAll();
        model.addAttribute("title", "Add Service");
        model.addAttribute("serviceRequest", serviceRequest);
        model.addAttribute("categories", categories);
        return "add_service";
    }

    @PostMapping("/add")
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
        return "redirect:/services/add";
    }

    @GetMapping("/edit/{id}")
    public String editServices(@PathVariable int id, Model model){
        List<CategoryResponse> categories = categoryService.getAll();
        model.addAttribute("title", ("Update Service | ID:" + id));
        model.addAttribute("service", serviceService.getById(id).get());
        model.addAttribute("categories", categories);
        return "edit_service";
    }

    @PostMapping("/edit/{id}")
    public String updateService(@PathVariable int id,
                                 @ModelAttribute("service") ServiceResponse serviceResponse,
                                 RedirectAttributes redirectAttributes, Model model){

        int newId = serviceService.editService(id, serviceResponse);
        Optional<ServiceResponse> service = serviceService.getById(id);
        if(service.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating selected service!");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Updated successfully!");
        }
        return "redirect:/services/edit/{id}";
    }

    @GetMapping("/delete/{id}")
    public String deleteLocation(@PathVariable int id, Model model){
        model.addAttribute("title", ("Delete Location | ID:" + id));
        model.addAttribute("text", "service");
        model.addAttribute("type", "services");
        model.addAttribute("item", serviceService.getById(id).get());
        return "delete_page";
    }

    @PostMapping("/delete/{id}")
    public String deleteLocation(@PathVariable int id) {
        serviceService.deleteService(id);
        return "redirect:/services";
    }
}
